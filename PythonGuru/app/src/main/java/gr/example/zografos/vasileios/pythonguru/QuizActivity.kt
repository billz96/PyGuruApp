package gr.example.zografos.vasileios.pythonguru

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class QuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // get quiz's id
        val sharedPref: SharedPreferences = this.getSharedPreferences("PyGuruStudent", Context.MODE_PRIVATE)
        val quiz = sharedPref.getInt("PyGuruQuiz", -1)

        // set title
        val quizView : TextView = findViewById(R.id.quizView)
        quizView.text = "Quiz " + quiz

        // get quiz's questions
        val dbHelper = PyGuruHelper(this)
        val questions = dbHelper.getQuestions(quiz)

        // display questions
        val questionsBtns : List<Button> = listOf(
            findViewById(R.id.q_1), // q_x -> question x
            findViewById(R.id.q_2),
            findViewById(R.id.q_3),
            findViewById(R.id.q_4),
            findViewById(R.id.q_5),
            findViewById(R.id.q_6),
            findViewById(R.id.q_7)
        )

        questions.forEachIndexed { index, question ->
            questionsBtns[index].text = question.get("body") // show question

            // find answers -> ["answer-1,y", "answer-2,n", "answer-3,n"]
            val answers = question.get("answers")!!.split(";")

            // setup listeners that show possible answers
            questionsBtns[index].setOnClickListener {
                // save all choices(available answers)
                val editor : SharedPreferences.Editor = sharedPref.edit()
                editor.putString("PyGuruChoice1", answers[0])
                editor.commit()
                editor.putString("PyGuruChoice2", answers[1])
                editor.commit()
                editor.putString("PyGuruChoice3", answers[2])
                editor.commit()

                // save question's index
                editor.putString("PyGuruQuestion", (index+1).toString())
                editor.commit()

                // go pick an answer
                val intent = Intent(this, AnswerActivity::class.java)
                startActivity(intent) // start answer activity
            }

        }

        val doneBtn : Button = findViewById(R.id.finishedQuiz)
        doneBtn.setOnClickListener {
            val ans  = listOf(
                sharedPref.getString("PyGuruAnswer1", ""),
                sharedPref.getString("PyGuruAnswer2", ""),
                sharedPref.getString("PyGuruAnswer3", ""),
                sharedPref.getString("PyGuruAnswer4", ""),
                sharedPref.getString("PyGuruAnswer5", ""),
                sharedPref.getString("PyGuruAnswer6", ""),
                sharedPref.getString("PyGuruAnswer7", "")
            )

            // check if every question is answered
            if (ans.all { el -> !el.equals("") }) {
                Toast.makeText(this, "Go see your mark in progress page.", Toast.LENGTH_LONG).show()

                // find correct answers
                val correctAns = ans.filter { el ->
                    val content = el.split(",")
                    content[1].equals("y")
                }

                // calculate mark and update the old one
                val mark = dbHelper.calculateMark(correctAns.size, quiz)
                val username = sharedPref.getString("PyGuruUser", "")
                val res = dbHelper.updateMark(username, mark, quiz)
                if (!res) {
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "You must answer every question!", Toast.LENGTH_LONG).show()
            }
        }
    }
}
