package gr.example.zografos.vasileios.pythonguru

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val welcomeTxt : TextView = findViewById(R.id.welcomeText)
        var txt : String = welcomeTxt.text.toString()

        // get user's name
        val sharedPref: SharedPreferences = this.getSharedPreferences("PyGuruStudent", Context.MODE_PRIVATE)
        txt = txt + " " + sharedPref.getString("PyGuruUser", "") + " !"

        // update text
        welcomeTxt.text = txt


        // setup listeners
        val progressBtn : Button = findViewById(R.id.progressButton)
        progressBtn.setOnClickListener {
            // go to progress
            val intent = Intent(this, ProgressActivity::class.java)
            startActivity(intent) // start progress activity
        }

        val profileBtn : Button = findViewById(R.id.profileButton)
        profileBtn.setOnClickListener {
            // go to profile
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent) // start profile activity
        }

        val testsBtn : Button = findViewById(R.id.testsButton)
        testsBtn.setOnClickListener {
            // go to quizzes
            val intent = Intent(this, QuizzesActivity::class.java)
            startActivity(intent) // start quizzes activity
        }

        val lessonsBtn : Button = findViewById(R.id.lessonsButton)
        lessonsBtn.setOnClickListener {
            // go to lessons
            val intent = Intent(this, LessonsActivity::class.java)
            startActivity(intent) // start lessons activity
        }
    }
}
