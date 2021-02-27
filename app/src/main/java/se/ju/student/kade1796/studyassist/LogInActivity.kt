package se.ju.student.kade1796.studyassist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase


class LogInActivity : AppCompatActivity() {
    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth.createUserWithEmailAndPassword("kade1796@student.ju.se","123")
            
    }


    override fun onStart() {
        super.onStart()

    }
}