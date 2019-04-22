package gr.example.zografos.vasileios.pythonguru

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ProgressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        val dbHelper : PyGuruHelper = PyGuruHelper(this)
        val marksViews : List<TextView> = listOf(
            findViewById(R.id.test1),
            findViewById(R.id.test2),
            findViewById(R.id.test3),
            findViewById(R.id.test4),
            findViewById(R.id.test5),
            findViewById(R.id.test6),
            findViewById(R.id.test7) // final test!
        )
        val commentsViews : List<TextView> = listOf(
            findViewById(R.id.comment1),
            findViewById(R.id.comment2),
            findViewById(R.id.comment3),
            findViewById(R.id.comment4),
            findViewById(R.id.comment5),
            findViewById(R.id.comment6),
            findViewById(R.id.comment7) // final test!
        )

        // get user's name
        val sharedPref: SharedPreferences = this.getSharedPreferences("PyGuruStudent", Context.MODE_PRIVATE)
        val username : String = sharedPref.getString("PyGuruUser", "")

        val marksColView : TextView = findViewById(R.id.marksColumn)
        val txt = marksColView.text.toString()
        marksColView.text = username + "'s " + txt

        // find user's marks
        val marksStr : String = dbHelper.getMarks(username)

        if (!marksStr.isEmpty()) {
            val marksStrs = marksStr.split(",")
            val realMarks = marksStrs.map { markStr -> markStr.toDouble() }

            // display marks and comments
            marksViews.forEachIndexed { index, markView ->
                // check if user passed the current quiz
                val mark = marksStrs[index].toDouble()

                var testRes : String = "FAIL"
                if (mark >= 0.63) {
                    testRes = "PASS"
                }

                // show mark
                markView.text = markView.text.toString() + " " + marksStrs[index] + " " + testRes
                commentsViews[index].text = dbHelper.commentMark(index, realMarks[index]) // show comment
            }

        }
    }
}
