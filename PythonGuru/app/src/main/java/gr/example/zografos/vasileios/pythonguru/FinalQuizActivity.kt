package gr.example.zografos.vasileios.pythonguru

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.Toast

class FinalQuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_quiz)

        // get quiz's id
        val sharedPref: SharedPreferences = this.getSharedPreferences("PyGuruStudent", Context.MODE_PRIVATE)
        val quiz = sharedPref.getInt("PyGuruQuiz", -1)

        // get quiz's questions
        val dbHelper = PyGuruHelper(this)
        val questions = dbHelper.getQuestions(quiz) // 14 questions!

        // display questions
        val questionsBtns : List<Button> = listOf(
            findViewById(R.id.fq1), // fqX -> final quiz's question X
            findViewById(R.id.fq2),
            findViewById(R.id.fq3),
            findViewById(R.id.fq4),
            findViewById(R.id.fq5),
            findViewById(R.id.fq6),
            findViewById(R.id.fq7),
            findViewById(R.id.fq8),
            findViewById(R.id.fq9),
            findViewById(R.id.fq10),
            findViewById(R.id.fq11),
            findViewById(R.id.fq12),
            findViewById(R.id.fq13),
            findViewById(R.id.fq14)
        )

        questions.forEachIndexed { index, question ->
            questionsBtns[index].text = "${index+1}. ${question.get("body")}" // show question

            // find answers -> ["answer-1,y", "answer-2,n", "answer-3,n"]
            val answers = question.get("answers")!!.split(";")

            // setup listeners that show possible answers
            questionsBtns[index].setAllCaps(false)
            questionsBtns[index].setOnClickListener {
                val quiz = sharedPref.getInt("PyGuruQuiz", -1)

                if (quiz > 0) {
                    // save all choices(available answers)
                    val editor: SharedPreferences.Editor = sharedPref.edit()
                    editor.putString("PyGuruChoice1", answers[0])
                    editor.commit()
                    editor.putString("PyGuruChoice2", answers[1])
                    editor.commit()
                    editor.putString("PyGuruChoice3", answers[2])
                    editor.commit()

                    // save question's index
                    editor.putString("PyGuruQuestion", (index + 1).toString())
                    editor.commit()

                    // go pick an answer
                    val intent = Intent(this, AnswerActivity::class.java)
                    startActivity(intent) // start answer activity
                } else {
                    Toast.makeText(this, "You have already completed the test.", Toast.LENGTH_LONG).show()
                }
            }

        }

        val doneBtn : Button = findViewById(R.id.finishedFinalQuiz)
        doneBtn.setOnClickListener {
            val ans  = listOf(
                sharedPref.getString("PyGuruAnswer1", ""),
                sharedPref.getString("PyGuruAnswer2", ""),
                sharedPref.getString("PyGuruAnswer3", ""),
                sharedPref.getString("PyGuruAnswer4", ""),
                sharedPref.getString("PyGuruAnswer5", ""),
                sharedPref.getString("PyGuruAnswer6", ""),
                sharedPref.getString("PyGuruAnswer7", ""),
                sharedPref.getString("PyGuruAnswer8", ""),
                sharedPref.getString("PyGuruAnswer9", ""),
                sharedPref.getString("PyGuruAnswer10", ""),
                sharedPref.getString("PyGuruAnswer11", ""),
                sharedPref.getString("PyGuruAnswer12", ""),
                sharedPref.getString("PyGuruAnswer13", ""),
                sharedPref.getString("PyGuruAnswer14", "")
            )

            // check if every question is answered and if the user already answered the test
            if (ans.all { el -> !el.equals("") } && quiz > 0) {

                // find correct answers
                val correctAns = ans.filter { el ->
                    val content = el.split(",")
                    content[1].equals("y")
                }

                // calculate mark and update the old one
                val mark = Math.round((correctAns.size / 14.toDouble()) * 100).toDouble()
                val username = sharedPref.getString("PyGuruUser", "")
                val res = dbHelper.updateMark(username, mark, quiz)
                if (!res) {
                    Toast.makeText(this, "Something went wrong! Try again.", Toast.LENGTH_LONG).show()
                }

                // make test invalid by removing the quiz's index
                val editor : SharedPreferences.Editor = sharedPref.edit()
                editor.putInt("PyGuruQuiz", -1)
                editor.commit()

                // remove stored answers
                for (i in 1..14) {
                    editor.putString("PyGuruAnswer$i", "")
                    editor.commit()
                }

                // find which answers are wright and which are wrong
                var ansMsgs = ans.mapIndexed { i, a ->
                    val content = a.split(",")
                    var res = "Answer-" + (i+1) +": "+ content[0] + " -> "
                    if (content[1].equals("y")) {
                        res = res + "Correct"
                    } else {
                        res = res + "Wrong"
                    }
                    res
                }

                ansMsgs = listOf("Your answers are:") + ansMsgs

                // show user's mark and answers
                // create the builder
                val dialogBuilder = AlertDialog.Builder(this)

                // prepare the items
                val msgs = ansMsgs.plus("Your mark is: ${mark.toString()}% (=${correctAns.size}/14)")
                val items = msgs.toTypedArray() // convert string list to string array

                // show the items
                dialogBuilder.setItems(items, null)

                // set click listener
                dialogBuilder.setPositiveButton("OK", DialogInterface.OnClickListener {
                        dialog, id -> finish()
                })

                // create the alert box
                val alertBox = dialogBuilder.create()

                // set title
                alertBox.setTitle("Your answers and mark")

                // show alert box
                alertBox.show()
            } else {
                Toast.makeText(this, "Either there are some missing questions or you have already completed the test.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
