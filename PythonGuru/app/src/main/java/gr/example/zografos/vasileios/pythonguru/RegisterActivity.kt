package gr.example.zografos.vasileios.pythonguru

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.content.Context
import android.content.SharedPreferences


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val dbHelper = PyGuruHelper(this)

        val registerButton : Button = findViewById(R.id.registerButton)
        registerButton.setOnClickListener {
            // get user's input
            val username : TextView = findViewById(R.id.newUsername)
            val password : TextView = findViewById(R.id.newPassword)

            if (!username.text.isEmpty() && !password.text.isEmpty()) {
                var res : Boolean = dbHelper.userExists()

                // student already registered
                if (res) {
                    Toast.makeText(this, "You can have only 1 account per device.", Toast.LENGTH_LONG).show()
                } else {
                    // new student
                    res = dbHelper.insertUser(username.text.toString(), password.text.toString())
                    if (res) {
                        // go to login screen
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent) // start login activity
                    } else {
                        // something gone wrong try again
                        Toast.makeText(this, "Something gone wrong, please try again.", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "You must fill the form.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
