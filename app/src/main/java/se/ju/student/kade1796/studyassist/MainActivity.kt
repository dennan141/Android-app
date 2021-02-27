package se.ju.student.kade1796.studyassist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var testButton = findViewById<Button>(R.id.button2)


        testButton.setOnClickListener {
            var intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }
    }
}