package gr.example.zografos.vasileios.pythonguru

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val dbHelper = PyGuruHelper(this)
        // get user's name
        val sharedPref: SharedPreferences = this.getSharedPreferences("PyGuruStudent", Context.MODE_PRIVATE)
        val username = sharedPref.getString("PyGuruUser", "")

        val newPassword : TextView = findViewById(R.id.newPasswordField)
        val repeatPassword : TextView = findViewById(R.id.repeatPasswordField)
        val changePassword : Button = findViewById(R.id.changePassword)

        changePassword.setOnClickListener {
            val password1 = newPassword.text.toString()
            val password2 = repeatPassword.text.toString()

            if (!password1.isEmpty() && !password2.isEmpty()) {
                if (!password1.equals(password2, false)) {
                    Toast.makeText(this, "Passwords are not equal.", Toast.LENGTH_LONG).show()
                } else {
                    val res = dbHelper.changePassword(username, password1)
                    if (!res) {
                        Toast.makeText(this, "Something gone wrong try again.", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "You must fill the form.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
