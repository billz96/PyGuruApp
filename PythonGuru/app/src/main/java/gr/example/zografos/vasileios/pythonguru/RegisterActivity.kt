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
                var res : Boolean = dbHelper.findUser(username.text.toString(), password.text.toString())

                // student exists
                if (res) {
                    Toast.makeText(this, "Student exists.", Toast.LENGTH_LONG).show()
                } else {
                    // new student
                    res = dbHelper.insertUser(username.text.toString(), password.text.toString())
                    if (res) {
                        // save user's username
                        val sharedPref: SharedPreferences = this.getSharedPreferences("PyGuruStudent", Context.MODE_PRIVATE)
                        val editor : SharedPreferences.Editor = sharedPref.edit()
                        editor.putString("PyGuruStudent", username.text.toString())
                        editor.apply()

                        // go to main menu
                        val intent = Intent(this, MainMenuActivity::class.java)
                        startActivity(intent) // start main menu activity
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
