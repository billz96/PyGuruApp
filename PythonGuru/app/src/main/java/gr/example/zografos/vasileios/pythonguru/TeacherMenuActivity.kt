package gr.example.zografos.vasileios.pythonguru

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class TeacherMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_menu)

        val welcomeTxt : TextView = findViewById<TextView>(R.id.teacher_welcome)
        var txt : String = welcomeTxt.text.toString()

        // get teacher's name
        val sharedPref: SharedPreferences = this.getSharedPreferences("PyGuruStudent", Context.MODE_PRIVATE)
        txt = txt + " " + sharedPref.getString("PyGuruTeacher", "") + " !"

        // update text
        welcomeTxt.text = txt

        // setup listeners
        val logoutBtn : Button = findViewById(R.id.teacher_logout)
        logoutBtn.setOnClickListener {
            // reset teacher's name
            val sharedPref: SharedPreferences = this.getSharedPreferences("PyGuruStudent", Context.MODE_PRIVATE)
            val editor : SharedPreferences.Editor = sharedPref.edit()
            editor.putString("PyGuruTeacher", "")
            editor.commit()

            // go to login screen
            val intent = Intent(this, TeacherLoginActivity::class.java)
            startActivity(intent) // start login activity
        }

        val resetPwdBtn : Button = findViewById(R.id.reset_teacher_pwd)
        resetPwdBtn.setOnClickListener {
            // start reset password activity
            val intent = Intent(this, ResetTeacherPwdActivity::class.java)
            startActivity(intent) // start profile activity
        }

        val addQuestionBtn : Button = findViewById(R.id.add_question_btn)
        addQuestionBtn.setOnClickListener {
            // go to new question activity
            val intent = Intent(this, NewQuestionActivity::class.java)
            startActivity(intent) // start new question activity
        }
    }
}
