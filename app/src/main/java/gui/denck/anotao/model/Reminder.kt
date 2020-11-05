package gui.denck.anotao.model

class Reminder {

    var key: String? = null
    var title: String? = null
    var description: String? = null
    var category: String? = null
    var date: String? = null


    override fun toString(): String {
        return "Reminder(key=$key, title=$title, description=$description, category=$category, date=$date)"
    }

}