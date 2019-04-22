package gr.example.zografos.vasileios.pythonguru

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = PyGuruHelper(this)

        var loginBtn : Button = findViewById(R.id.loginButton)

        loginBtn.setOnClickListener {
            // get username and password
            var username : TextView = findViewById(R.id.editText3)
            var password : TextView = findViewById(R.id.editText4)

            if (!username.text.isEmpty() && !password.text.isEmpty()) {
                // user validation here with sqlite
                val res : Boolean = dbHelper.findUser(username.text.toString(), password.text.toString())
                if (res) {
                    // save user's username
                    val sharedPref: SharedPreferences = this.getSharedPreferences("PyGuruStudent", Context.MODE_PRIVATE)
                    val editor : SharedPreferences.Editor = sharedPref.edit()
                    editor.putString("PyGuruUser", username.text.toString())
                    editor.commit()

                    // go to main menu
                    val intent = Intent(this, MainMenuActivity::class.java)
                    startActivity(intent) // start main menu activity
                } else {
                    // some field is wrong
                    Toast.makeText(this, "Username/Password is wrong.", Toast.LENGTH_LONG).show()
                }

            } else {
                // form's fields are required
                Toast.makeText(this, "You must fill the form.", Toast.LENGTH_LONG).show()
            }
        }

        val registerText : TextView = findViewById(R.id.textView2)
        registerText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent) // start register activity
        }

        val teacherText : TextView = findViewById(R.id.teacherLogin)
        teacherText.setOnClickListener {
            val intent = Intent(this, TeacherLoginActivity::class.java)
            startActivity(intent) // start teacher login activity
        }

        val forgotPwdText : TextView = findViewById(R.id.forgotPwd)
        forgotPwdText.setOnClickListener {
            val intent = Intent(this, ForgotPwdActivity::class.java)
            startActivity(intent) // start forgot password login activity
        }
    }
}
