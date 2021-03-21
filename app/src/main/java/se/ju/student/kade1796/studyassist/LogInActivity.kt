package se.ju.student.kade1796.studyassist

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LogInActivity : AppCompatActivity() {
    //access a firebase auth from your Activity
    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //initialize firebase auth here
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()

        val enteredEmail = findViewById<EditText>(R.id.loginEmail).editableText
        val enteredPassword = findViewById<EditText>(R.id.loginPassword).editableText
        val loginButton = findViewById<Button>(R.id.loginButton)

        var currentUser = Authentication.instance.getCurrentUser()

        //FOR TESTING!!!
        //currentUser = null
        //FOR TESTING!!!

        //If user is logged in, open categories instead
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            if (isEmpty(enteredEmail)) {
                Toast.makeText(
                    baseContext, "Enter your email",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (isEmpty(enteredPassword)) {
                Toast.makeText(
                    baseContext, "Enter your password",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Log.d("login Empty", "$enteredEmail $enteredPassword")
                signIn(enteredEmail.toString(), enteredPassword.toString())
            }
        }
    }


    //*********** PRIVATE FUNCTION; MOVE TO OTHER FILE LATER

    private fun isEmpty(inputText: Editable): Boolean {
        return inputText.toString().isEmpty()
    }

    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("successTag", "signInWithEmail:success")
                    val user = auth.currentUser
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("FailTag", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
        // [END sign_in_with_email]
    }



    private fun updateUI(user: FirebaseUser?) {
        //Update the UI with this
        //Suited bettet in accountHandler as a function, can update UI whenever necessary?
    }

}