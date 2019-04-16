package gr.example.zografos.vasileios.pythonguru

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ResetTeacherPwdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_teacher_pwd)

        val dbHelper = PyGuruHelper(this)
        // get user's name
        val sharedPref: SharedPreferences = this.getSharedPreferences("PyGuruStudent", Context.MODE_PRIVATE)
        val teacher = sharedPref.getString("PyGuruTeacher", "")


        val resetPwdBtn : Button = findViewById(R.id.reset_teachers_pwd)
        resetPwdBtn.setOnClickListener {
            val password1 = findViewById<TextView>(R.id.new_teachers_pwd).text.toString()
            val password2 = findViewById<TextView>(R.id.repeat_teachers_pwd).text.toString()

            if (!password1.isEmpty() && !password2.isEmpty()) {
                if (!password1.equals(password2, false)) {
                    Toast.makeText(this, "Passwords are not equal.", Toast.LENGTH_LONG).show()
                } else {
                    val res = dbHelper.changeTeachersPassword(teacher, password1)
                    if (!res) {
                        Toast.makeText(this, "Something gone wrong try again.", Toast.LENGTH_LONG).show()
                    }
                    Toast.makeText(this, "Your password was successfully changed.", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "You must fill the form.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
