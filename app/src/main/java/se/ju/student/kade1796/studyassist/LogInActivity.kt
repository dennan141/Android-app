package se.ju.student.kade1796.studyassist

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils.isEmpty
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
        val errorMessage = intent.getStringExtra("errorMessage")
        val currentUser = Authentication.instance.getCurrentUser()



        //If user is logged in, open categories instead
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            loginButton.setOnClickListener {
                Log.d("hejhej", "onclick")
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

                    Log.d(
                        "login",
                        "Logged in as ${DatabaseFirestore.instance.auth.currentUser?.email}"
                    )
                }
            }
        }
    }

    //*********** PRIVATE FUNCTION; MOVE TO OTHER FILE LATER

    private fun isEmpty(inputText: Editable): Boolean {
        return inputText.toString().isEmpty()
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("tryLogin", "logged in")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("tryLogin", "logged in not")
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()


                }
            }
    }

    //*********** PRIVATE FUNCTION; MOVE TO OTHER FILE LATER **************


}




