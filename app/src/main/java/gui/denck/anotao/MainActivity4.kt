package gui.denck.anotao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import gui.denck.anotao.adapters.reminderAdapter
import gui.denck.anotao.extensions.toast
import gui.denck.anotao.model.Reminder
import kotlinx.android.synthetic.main.activity_main4.*

class MainActivity4 : AppCompatActivity() {

    val database = FirebaseFirestore.getInstance()
    private lateinit var adapter: reminderAdapter
    private var reminders = arrayListOf<Reminder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        adapter = reminderAdapter(reminders, this@MainActivity4, {reminder: Reminder -> itemClicked(reminder)  })
        listReminder.adapter = adapter
        listReminder.layoutManager = LinearLayoutManager(this@MainActivity4)
        database.collection("reminder").get().addOnSuccessListener { result ->
            if (result.isEmpty){
                toast(R.string.alertaNoRegister)
                return@addOnSuccessListener
            }

            for (document in result ) {
                val reminderr = document.toObject(Reminder::class.java)
                reminderr.key = document.id
                this.reminders.add(reminderr)
            }
            adapter.updateList()
        }.addOnFailureListener { error ->
            toast("${R.string.errorConsultClient} ${error.toString()}")
        }
    }

    private fun itemClicked(reminder: Reminder){
        toast(R.string.clicked)
    }
}