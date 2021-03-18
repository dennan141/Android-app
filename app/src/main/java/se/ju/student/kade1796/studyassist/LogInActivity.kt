package se.ju.student.kade1796.studyassist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.delay
import se.ju.student.kade1796.studyassist.ui.categories.CategoryFragment
import kotlin.math.sign


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


        var currentUser = auth.currentUser

        //USED FOR TESTING
        currentUser = null
        //USED FOR TESTING

        //If user is logged in, open categories instead
        if (currentUser != null) {
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

                Log.d("login", "Logged in as ${auth.currentUser?.email}")
            }

        }
    }


    //*********** PRIVATE FUNCTION; MOVE TO OTHER FILE LATER



    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("successTag", "signInWithEmail:success")
                    val user = auth.currentUser
                    val intent = Intent(this, CategoryFragment::class.java)
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

    private fun sendEmailVerification() {
        // [START send_email_verification]
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                // Email Verification sent
            }
        // [END send_email_verification]
    }


    private fun updateUI(user: FirebaseUser?) {
        //Update the UI with this
    }

}