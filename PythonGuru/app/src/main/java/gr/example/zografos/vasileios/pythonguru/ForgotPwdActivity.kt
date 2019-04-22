package gr.example.zografos.vasileios.pythonguru

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ForgotPwdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pwd)

        val dbHelper = PyGuruHelper(this)

        val fpwdBtn : Button = findViewById(R.id.fpwd_btn)
        fpwdBtn.setOnClickListener {
            val uname : String = findViewById<TextView>(R.id.fpwd_uname).text.toString()
            val pwd : String = findViewById<TextView>(R.id.fpwd_password).text.toString()

            if (uname.isNotEmpty() && pwd.isNotEmpty()) {
                val res = dbHelper.findUsername(uname)
                if (res) {
                    // update password
                    val res = dbHelper.changePassword(uname, pwd)

                    // show failure message
                    if (!res) {
                        Toast.makeText(this, "Something gone wrong try again.", Toast.LENGTH_LONG).show()
                    }

                    // show success message
                    Toast.makeText(this, "Your password was successfully changed.", Toast.LENGTH_LONG).show()

                    // go to login activity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent) // start login activity
                } else {
                    Toast.makeText(this, "Invalid username.", Toast.LENGTH_LONG).show()
                }
            } else {
                // show error
                Toast.makeText(this, "You must fill the form.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
