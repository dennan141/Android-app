package se.ju.student.kade1796.studyassist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onStart() {
        super.onStart()

        val enteredEmail = findViewById<EditText>(R.id.loginEmail).editableText
        val enteredPassword = findViewById<EditText>(R.id.loginPassword).editableText
        val loginButton = findViewById<Button>(R.id.loginButton)

        //If user is logged in, open categories instead
        if (DatabaseFirestore.instance.auth.currentUser != null) {
            val intent = Intent(this, ThreadsActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            if (enteredEmail == null) {
                Toast.makeText(
                    baseContext, "Enter EMAIL for fan",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                signIn(enteredEmail.toString(), enteredPassword.toString())
                Log.d("login", "Logged in as ${DatabaseFirestore.instance.auth.currentUser?.email}")
            }
        }
    }


    //*********** PRIVATE FUNCTION; MOVE TO OTHER FILE LATER **************
    private fun signIn(email: String, password: String) {
        DatabaseFirestore.instance.loginWithEmail(email, password, this)
    }

    private fun sendEmailVerification() {
        // [START send_email_verification]
        val user = DatabaseFirestore.instance.auth.currentUser

        if (user != null) {
            user.sendEmailVerification()
                .addOnCompleteListener(this) { task ->
                    // Email Verification sent
                }
        } else {
            Log.d("Email Verification", "User is not logged in!")
        }
        // [END send_email_verification]
    }


    fun updateUI(user: FirebaseUser?) {
        //Update the UI with this
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            println(R.string.text_could_not_login)
            //Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
        }
    }
}