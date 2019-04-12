package gr.example.zografos.vasileios.pythonguru

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class LessonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)

        val dbHelper = PyGuruHelper(this)

        // get lesson's id
        val sharedPref: SharedPreferences = this.getSharedPreferences("PyGuruStudent", Context.MODE_PRIVATE)
        val lessonId = sharedPref.getInt("PyGuruLesson", -1)

        val lessonView: TextView = findViewById(R.id.currentLesson)
        val lessonVisits: TextView = findViewById(R.id.visitsText)
        val lessonBody: TextView = findViewById(R.id.lessonBody)

        if (lessonId > 0) {
            // get lesson's data -> array of 4 elements
            val lessonData: List<String> = dbHelper.getLesson(lessonId).asList()

            if (!lessonData.isEmpty()) {
                // show lesson's data if data exist
                lessonView.text = lessonData[0] // title
                lessonBody.text = lessonData[1] // content
                lessonVisits.text = "visits: " + lessonData[2] // visits + 1
            } else {
                // no data for that lesson id
                lessonView.text = "<NONE>"
                lessonBody.text = "<NONE>"
                lessonVisits.text = "<NONE>"
            }
        } else {
            // invalid lesson id
            lessonView.text = "<NONE>"
            lessonBody.text = "<NONE>"
            lessonVisits.text = "<NONE>"
        }
    }
}
