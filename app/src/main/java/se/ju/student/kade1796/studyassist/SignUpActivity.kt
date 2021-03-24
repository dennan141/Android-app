package se.ju.student.kade1796.studyassist

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils.isEmpty
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignUpActivity : AppCompatActivity() {

    //access a firebase auth from your Activity
    var auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //initialize firebase auth here
        auth = Firebase.auth

    }

    override fun onStart() {
        super.onStart()

        val enteredEmail = findViewById<EditText>(R.id.signUpEmail).editableText
        val enteredPassword = findViewById<EditText>(R.id.signUpPassword).editableText
        val repeatPassword = findViewById<EditText>(R.id.signUpPassword_2).editableText
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        //val errorMessage = intent.getStringExtra("errorMessage")
        //val currentUser = Authentication.instance.getCurrentUser()


        signUpButton.setOnClickListener {
            Log.d("passwords", "$enteredPassword and $repeatPassword")
            if (enteredPassword.toString() == repeatPassword.toString()) {
                signUp(enteredEmail.toString(), enteredPassword.toString())
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    baseContext, getString(R.string.text_passwords_dont_match),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }

    //*********** PRIVATE FUNCTION; MOVE TO OTHER FILE LATER


    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("successTag", "createUserWithEmail:success")
                    val user = auth.currentUser
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("failTag", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, getString(R.string.text_authentication_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                    //updateUI(null)
                }
            }
    }


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
                        baseContext, getString(R.string.text_authentication_failed),
                        Toast.LENGTH_SHORT
                    ).show()


                }
            }
    }

    //*********** PRIVATE FUNCTION; MOVE TO OTHER FILE LATER **************


}




