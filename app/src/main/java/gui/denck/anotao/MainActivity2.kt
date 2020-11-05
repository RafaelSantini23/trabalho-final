package gui.denck.anotao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import gui.denck.anotao.extensions.toast
import gui.denck.anotao.model.Reminder

import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity2 : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val fragmentManager: FragmentManager = supportFragmentManager
    lateinit var fragmentTransaction: FragmentTransaction
    private var mAuth: FirebaseAuth? = null


    val database = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        layoutCadastro.visibility = View.VISIBLE


        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()



        buttonReminder.setOnClickListener {
            if (nomeAnnotation.text.toString().isEmpty() || spinnerReminder.selectedItem.toString().isEmpty() || editTextTextMultiLine.text.toString().isEmpty() || dateReminder.text.toString().isEmpty()  ) {
                toast(R.string.Fill)

                return@setOnClickListener
            }

            var reminder = Reminder()
            reminder.title = nomeAnnotation.text.toString()
            reminder.description = editTextTextMultiLine.text.toString()
            reminder.category = spinnerReminder.selectedItem.toString()
            reminder.date = dateReminder.text.toString()
            database.collection("reminder")
                    .add(reminder)
                    .addOnSuccessListener { documentReference ->
                        toast(R.string.reminderAddSucessfull)
                    }
                    .addOnFailureListener { error ->
                        toast(R.string.errorAddReminder)
                    }

        }




        nav_view.setNavigationItemSelectedListener(this)


        mAuth = FirebaseAuth.getInstance()

    }


        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.listOfNotes -> {
                    startActivity(Intent(this@MainActivity2, MainActivity4::class.java))
                }

                R.id.sobre -> {
                    val intent = Intent(this@MainActivity2, MainActivity3::class.java)
                    startActivity(intent)
                }
                R.id.exit -> {
                    mAuth?.signOut()
                    val intent = Intent(this@MainActivity2, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                else -> super.onOptionsItemSelected(item)
            }
            drawer_layout.closeDrawer(GravityCompat.START)
            return true
        }


        override fun onBackPressed() {
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.isDrawerOpen(GravityCompat.START)
            } else {
                super.onBackPressed()
            }
        }


    }


