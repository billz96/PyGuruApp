package gr.example.zografos.vasileios.pythonguru

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class QuizzesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizzes)

        // find all quizzes
        val quizzes : List<TextView> = listOf(
            findViewById(R.id.quiz1),
            findViewById(R.id.quiz2),
            findViewById(R.id.quiz3),
            findViewById(R.id.quiz4),
            findViewById(R.id.quiz5),
            findViewById(R.id.quiz6)
        )

        val finalQuiz : TextView = findViewById(R.id.quiz7)

        // setup listeners
        quizzes.forEachIndexed { index, quiz ->
            quiz.setOnClickListener{
                // store current quiz id
                val sharedPref: SharedPreferences = this.getSharedPreferences("PyGuruStudent", Context.MODE_PRIVATE)
                val editor : SharedPreferences.Editor = sharedPref.edit()
                editor.putInt("PyGuruQuiz", index+1)
                editor.commit()

                // go to that quiz
                val intent = Intent(this, QuizActivity::class.java)
                startActivity(intent) // start quiz activity
            }
        }

        finalQuiz.setOnClickListener {
            // store current quiz id
            val sharedPref: SharedPreferences = this.getSharedPreferences("PyGuruStudent", Context.MODE_PRIVATE)
            val editor : SharedPreferences.Editor = sharedPref.edit()
            editor.putInt("PyGuruQuiz", 7)
            editor.commit()

            // go to that quiz
            val intent = Intent(this, FinalQuizActivity::class.java)
            startActivity(intent) // start final quiz activity
        }
    }
}
