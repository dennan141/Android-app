package se.ju.student.kade1796.studyassist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner


//This is Hawkars project
class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val createButton = findViewById<Button>(R.id.create_button)

        createButton.setOnClickListener{
            startActivity(
                    Intent(this, CreateThreadActivity::class.java)
            )
        }
    }
}