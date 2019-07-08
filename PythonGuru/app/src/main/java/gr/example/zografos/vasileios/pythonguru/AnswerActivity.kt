package gr.example.zografos.vasileios.pythonguru

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.TextView

class AnswerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        val sharedPref: SharedPreferences = this.getSharedPreferences("PyGuruStudent", Context.MODE_PRIVATE)

        val choices = listOf<String>(
            sharedPref.getString("PyGuruChoice1",""),
            sharedPref.getString("PyGuruChoice2",""),
            sharedPref.getString("PyGuruChoice3","")
        )

        val currQuestion = findViewById<TextView>(R.id.currQuestion)

        val question = sharedPref.getString("PyGuruQuestion","")

        currQuestion.text = "Choose your answer for question-No.${question} :"

        val choicesBtns : List<RadioButton> = listOf(
            findViewById(R.id.choice1),
            findViewById(R.id.choice2),
            findViewById(R.id.choice3)
        )

        choicesBtns.forEachIndexed {index , btn ->
            // show answer
            var choice = choices[index].split(",")
            btn.text = choice[0] // choice[0] -> answer's text

            // store selected answer
            btn.setOnClickListener {
                val editor: SharedPreferences.Editor = sharedPref.edit()
                editor.putString("PyGuruAnswer" + question, choices[index])
                editor.commit()
            }

            // go to current test
            //val intent = Intent(this, QuizActivity::class.java)
            //startActivity(intent)
        }
    }
}
