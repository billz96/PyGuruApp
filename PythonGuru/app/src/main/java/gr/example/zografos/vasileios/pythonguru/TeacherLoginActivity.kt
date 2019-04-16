package gr.example.zografos.vasileios.pythonguru

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class TeacherLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_login)

        val dbHelper = PyGuruHelper(this)

        val registerTxt : TextView = findViewById(R.id.teacherRegister)
        registerTxt.setOnClickListener {
            // go to teacher's register activity
            val intent = Intent(this, NewTeacherActivity::class.java)
            startActivity(intent) // start new teacher activity
        }

        val loginBtn : Button = findViewById(R.id.teacherLoginBtn)
        loginBtn.setOnClickListener {
            val username : TextView = findViewById(R.id.teacherUname)
            val password : TextView = findViewById(R.id.teacherPwd)

            if (username.text.isNotEmpty() && password.text.isNotEmpty()) {
                val uname = username.text.toString()
                val pwd = password.text.toString()

                val res = dbHelper.findTeacher(uname, pwd)
                // teacher exists
                if (res) {
                    // save teacher's username
                    val sharedPref: SharedPreferences = this.getSharedPreferences("PyGuruStudent", Context.MODE_PRIVATE)
                    val editor : SharedPreferences.Editor = sharedPref.edit()
                    editor.putString("PyGuruTeacher", uname)
                    editor.commit()

                    // go to teacher's main menu
                    val intent = Intent(this, TeacherMenuActivity::class.java)
                    startActivity(intent) // start teacher's main menu activity
                } else {
                    // teacher doesn't exist
                    Toast.makeText(this, "Username/Password is wrong.", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "You must fill the form.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
