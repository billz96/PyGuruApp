package gr.example.zografos.vasileios.pythonguru

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast

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

        val question = sharedPref.getString("PyGuruQuestion","")
        val quiz = sharedPref.getInt("PyGuruQuiz", -1)

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
                if (quiz > 0) {
                    val editor: SharedPreferences.Editor = sharedPref.edit()
                    editor.putString("PyGuruAnswer" + question, choices[index])
                    editor.commit()
                } else {
                    Toast.makeText(this, "You have already completed the test.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
