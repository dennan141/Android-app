package se.ju.student.kade1796.studyassist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }


    override fun onStart() {
        super.onStart()


        //listener = FirebaseFirestore.getIstance()
    }
}