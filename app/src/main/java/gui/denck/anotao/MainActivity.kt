package gui.denck.anotao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import gui.denck.anotao.extensions.toast
import gui.denck.anotao.model.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var mAuth: FirebaseAuth? = null
    private var mAuthStateListener: FirebaseAuth.AuthStateListener? = null
    private var mUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        FirebaseApp.initializeApp(this)
        mUser = mAuth?.currentUser //Buscando usuário logado

        //Verificar se está logado
        mAuthStateListener = FirebaseAuth.AuthStateListener {
            if (mUser != null) {
                //ja esta logado!
                val intent = Intent(this@MainActivity, MainActivity2::class.java)
                startActivity(intent)
                finish()
            }
        }

        login.setOnClickListener(this)
        esqueceuSenha.setOnClickListener(this)
        registro.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        mAuth?.addAuthStateListener(mAuthStateListener!!)
    }

    override fun onStop() {
        super.onStop()
        if (mAuthStateListener != null) {
            mAuth?.removeAuthStateListener(mAuthStateListener!!)
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.login -> {
//                val intent = Intent(this@MainActivity, MainActivity2::class.java)
//                startActivity(intent)
//                finish()
                login()
            }
            R.id.esqueceuSenha -> {
                forgotPassword()
            }
            R.id.registro -> {
                register()
            }
        }
    }

    fun forgotPassword() {
        if (email.text.toString().isEmpty()) {
            toast(R.string.emailOnly)
            return
        }

        mAuth?.sendPasswordResetEmail(
                email.text.toString())
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        toast(R.string.passwordReset)
                    }
                }

    }

    fun login() {
        if (email.text.toString().isEmpty() || senha.text.toString().isEmpty()) {
            toast(R.string.enterUsername)
            return
        }
        var user = User()
        user.email = email.text.toString()
        user.password = senha.text.toString()

        mAuth?.signInWithEmailAndPassword(user.email.toString(), user.password.toString())
                ?.addOnCompleteListener(object : OnCompleteListener<AuthResult> {
            //task
            override fun onComplete(p0: Task<AuthResult>) {


                if (!p0.isSuccessful) {
                    toast(R.string.invalidUser)
                    return
                }
                val intent = Intent(this@MainActivity, MainActivity2::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    fun register() {
        if (email.text.toString().isEmpty() || senha.text.toString().isEmpty()) {
            toast(R.string.registerUser)
            return
        }
        var user = User()
        user.email = email.text.toString()
        user.password = senha.text.toString()
        mAuth?.createUserWithEmailAndPassword(user.email.toString(), user.password.toString())
                ?.addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                    //task
                    override fun onComplete(p0: Task<AuthResult>) {
                        if (!p0.isSuccessful) {
                            toast(R.string.notCreate)
                            return
                        }
                        toast(R.string.createdSuccessfully)
                        val intent = Intent(this@MainActivity, MainActivity2::class.java)
                        startActivity(intent)
                        finish()
                    }
                })
    }
}