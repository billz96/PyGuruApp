package gr.example.zografos.vasileios.pythonguru

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class NewTeacherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_teacher)

        val dbHelper = PyGuruHelper(this)
        val registerBtn : Button = findViewById(R.id.teacher_register)
        registerBtn.setOnClickListener {
            val uname : TextView = findViewById(R.id.new_teacher_name)
            val unameTxt = uname.text.toString()

            val pwd : TextView = findViewById(R.id.new_teacher_pwd)
            val pwdTxt = pwd.text.toString()

            if (unameTxt.isNotEmpty() && pwdTxt.isNotEmpty()) {
                // check id teacher exists
                var res = dbHelper.findTeacher(unameTxt, pwdTxt)
                if (!res) {
                    // new teacher
                    res = dbHelper.insertTeacher(unameTxt, pwdTxt)
                    if (res) {
                        // go to login screen
                        val intent = Intent(this, TeacherLoginActivity::class.java)
                        startActivity(intent) // start login activity
                    } else {
                        // something gone wrong try again
                        Toast.makeText(this, "Something gone wrong, please try again.", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "Teacher exists.", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "You must fill the form.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
