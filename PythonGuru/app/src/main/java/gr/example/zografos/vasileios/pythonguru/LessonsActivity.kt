package gr.example.zografos.vasileios.pythonguru

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class LessonsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lessons)

        val dbHelper : PyGuruHelper = PyGuruHelper(this)

        val lessonsViews : List<TextView> = listOf(
            findViewById(R.id.lesson1),
            findViewById(R.id.lesson2),
            findViewById(R.id.lesson3),
            findViewById(R.id.lesson4),
            findViewById(R.id.lesson5),
            findViewById(R.id.lesson6)
        )

        // setup click listeners which load the correct lesson
        lessonsViews.forEachIndexed { index, lessonView ->
            lessonView.setOnClickListener {
                // store current lesson id
                val sharedPref: SharedPreferences = this.getSharedPreferences("PyGuruStudent", Context.MODE_PRIVATE)
                val editor : SharedPreferences.Editor = sharedPref.edit()
                editor.putInt("PyGuruLesson", index+1)
                editor.commit()

                val intent = Intent(this, LessonActivity::class.java)
                startActivity(intent) // start lesson activity
            }
        }

        // get lesson titles from db
        val lessonsTitles = dbHelper.getLessonsTitles()

        if (lessonsTitles.isNotEmpty()) {
            // update each textview's text
            lessonsTitles.forEachIndexed { index, lessonsTitle ->
                val title = lessonsTitle.get("title")
                val txt = lessonsViews[index].text.toString()
                lessonsViews[index].text = txt + " " + title
            }
        }
    }
}
