package gui.denck.anotao.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gui.denck.anotao.R
import gui.denck.anotao.model.Reminder
import kotlinx.android.synthetic.main.row_reminder.view.*

class reminderAdapter(
        private val reminders: List<Reminder>,
        private val context: Context,
        private val listener: (Reminder) -> Unit

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_reminder, parent, false))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val reminder = reminders [position]


        if (holder is ViewHolder) {
            holder?.let {
                it.bind(reminder, position,  context, listener)
            }
        }

    }

    override fun getItemCount(): Int {
        return reminders.size
    }

    public fun updateList(){
        this.notifyDataSetChanged()
    }


}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(reminder: Reminder?, postition: Int?, context: Context, listener: (Reminder) -> Unit) {
        val rowReminderTextViewTitle = itemView.rowReminderTextViewTitle
        val rowReminderTextViewDescription = itemView.rowReminderTextViewDescription
        val rowReminderTextViewCategory = itemView.rowReminderTextViewCategory
        val rowReminderTextViewDate = itemView.rowReminderTextViewDate

        reminder?.let {
            rowReminderTextViewTitle.text = it.title.toString()
            rowReminderTextViewDescription.text = it.description.toString()
            rowReminderTextViewCategory.text = it.category.toString()
            rowReminderTextViewDate.text = it.date.toString()

            itemView.setOnClickListener { listener (reminder) }
        }


}

}





