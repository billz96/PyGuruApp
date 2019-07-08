package gr.example.zografos.vasileios.pythonguru

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class NewQuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_question)

        val dbHelper = PyGuruHelper(this)
        val sharedPref: SharedPreferences = this.getSharedPreferences("PyGuruStudent", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPref.edit()

        // setup right and wrong answers
        for (i in 1..3) {
            editor.putString("PyGuruRightAnswer$i", "n") // every answer is wrong
            editor.commit()
        }

        val quizBtns : List<Button> = listOf(
            findViewById(R.id.quiz_1_btn),
            findViewById(R.id.quiz_2_btn),
            findViewById(R.id.quiz_3_btn),
            findViewById(R.id.quiz_4_btn),
            findViewById(R.id.quiz_5_btn),
            findViewById(R.id.quiz_6_btn),
            findViewById(R.id.quiz_7_btn)
        )

        val ansBtns : List<Button> = listOf(
            findViewById(R.id.ans_1_btn),
            findViewById(R.id.ans_2_btn),
            findViewById(R.id.ans_3_btn)
        )

        // setup listeners
        quizBtns.forEachIndexed { i, btn ->
            btn.setOnClickListener {
                editor.putInt("PyGuruAtQuiz", i+1)
                editor.commit()
            }
        }

        ansBtns.forEachIndexed { i, btn ->
            btn.setOnClickListener {
                editor.putString("PyGuruRightAnswer${i + 1}", "y")
                editor.commit()
            }
        }


        val addBtn : Button = findViewById(R.id.add_question_btn)
        addBtn.setOnClickListener {
            val questionBody = findViewById<TextView>(R.id.question_body).text.toString()
            val ansTxts : List<String> = listOf(
                findViewById<TextView>(R.id.ans1_txt).text.toString(),
                findViewById<TextView>(R.id.ans2_txt).text.toString(),
                findViewById<TextView>(R.id.ans3_txt).text.toString()
            )

            val quizNo = sharedPref.getInt("PyGuruAtQuiz", -1)

            // get right and wrong answers
            val rightAnswers : List<String> = listOf(
                sharedPref.getString("PyGuruRightAnswer1", ""),
                sharedPref.getString("PyGuruRightAnswer2", ""),
                sharedPref.getString("PyGuruRightAnswer3", "")
            )

            // are all answer txt filled?
            val b1 = ansTxts.all { txt -> txt.isNotEmpty() }

            // is there any right answer?
            val b2 = rightAnswers.any { a -> a.equals("y") }

            // check if everything is filled
            if (questionBody.isNotEmpty() && b1 && quizNo != -1 && b2) {
                // get answers
                val realAns : List<String> = ansTxts.mapIndexed { i, ans ->
                    ans + "," + sharedPref.getString("PyGuruRightAnswer${i + 1}", "")
                }

                // create answers text
                var realAnsTxt = ""
                realAns.forEachIndexed { i, a ->
                    if (i < 2) {
                        realAnsTxt = realAnsTxt + a + ";"
                    } else {
                        realAnsTxt = realAnsTxt + a
                    }
                }

                // Toast.makeText(this, "$realAnsTxt", Toast.LENGTH_LONG).show()

                // add question
                val res = dbHelper.insertQuestion(questionBody, quizNo, realAnsTxt)

                // check insertion's result
                if (res) {
                    Toast.makeText(this, "Your question has been added.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Something gone wrong, please try again.", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "You must fill the form.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
