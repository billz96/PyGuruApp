package gr.example.zografos.vasileios.pythonguru

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class FinalQuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_quiz)

        // get quiz's id
        val sharedPref: SharedPreferences = this.getSharedPreferences("PyGuruStudent", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPref.edit()
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

        val backBtn = findViewById<Button>(R.id.backBtn2)
        backBtn.setOnClickListener {
            // remove quiz
            editor.putInt("PyGuruQuiz", -1)
            editor.commit()

            // remove stored answers
            for (i in 1..14) {
                editor.putString("PyGuruAnswer$i", "")
                editor.commit()
            }

            // go to quizzes list
            val intent = Intent(this, QuizzesActivity::class.java)
            startActivity(intent)
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
                editor.putInt("PyGuruQuiz", -1)
                editor.commit()

                // remove stored answers
                for (i in 1..14) {
                    editor.putString("PyGuruAnswer$i", "")
                    editor.commit()
                }

                // find and show which answers are right and which are wrong
                var ansMsgs = ans.mapIndexed { i, a ->
                    val content = a.split(",")
                    var res = "Answer-" + (i+1) +": \""+ content[0] + "\" -> "
                    if (content[1].equals("y")) {
                        res = res + "Correct!\n"
                    } else {
                        res = res + "Wrong!\n"

                        // find and show the correct answer
                        val _answers = questions[i].get("answers")!!.split(";")
                        _answers.forEach { _ans ->
                            val _content = _ans.split(",")
                            if (_content[1].equals("y")) {
                                val correctAns = _content[0]
                                res = res + "Correct answer: \"${correctAns}\"\n"
                            }
                        }

                    }
                    res
                }

                ansMsgs = listOf("Your answers are:") + ansMsgs

                // show user's mark and answers
                // create the builder
                val dialogBuilder = AlertDialog.Builder(this)

                // prepare the items
                val msgs = ansMsgs.plus("\nYour mark is: ${mark.toString()}% (=${correctAns.size}/14)")
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

                // find and show not answered questions
                val currAnsTxt = findViewById<TextView>(R.id.currAns2)
                val currAns = listOf(
                    listOf(sharedPref.getString("PyGuruAnswer1", ""), 1),
                    listOf(sharedPref.getString("PyGuruAnswer2", ""), 2),
                    listOf(sharedPref.getString("PyGuruAnswer3", ""), 3),
                    listOf(sharedPref.getString("PyGuruAnswer4", ""), 4),
                    listOf(sharedPref.getString("PyGuruAnswer5", ""), 5),
                    listOf(sharedPref.getString("PyGuruAnswer6", ""), 6),
                    listOf(sharedPref.getString("PyGuruAnswer7", ""), 7),
                    listOf(sharedPref.getString("PyGuruAnswer8", ""), 8),
                    listOf(sharedPref.getString("PyGuruAnswer9", ""), 9),
                    listOf(sharedPref.getString("PyGuruAnswer10", ""), 10),
                    listOf(sharedPref.getString("PyGuruAnswer11", ""), 11),
                    listOf(sharedPref.getString("PyGuruAnswer12", ""), 12),
                    listOf(sharedPref.getString("PyGuruAnswer13", ""), 13),
                    listOf(sharedPref.getString("PyGuruAnswer14", ""), 14)
                )
                val currAnsFiltered = currAns.filter { currA -> currA[0] == "" }
                val currAnsIdx = currAnsFiltered.map { a -> a[1]}
                currAnsIdx.forEachIndexed { i, a ->
                    if (i == 0) {
                        currAnsTxt.text = " You haven't answered the questions: ${a},"
                    } else if (i < currAnsFiltered.size - 1){
                        currAnsTxt.text = "${currAnsTxt.text} ${a},"
                    } else {
                        currAnsTxt.text = "${currAnsTxt.text} ${a}."
                    }
                }

                Toast.makeText(this, "There are some missing questions.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
