package se.ju.student.kade1796.studyassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val category1_button = findViewById<ImageButton>(R.id.category1)

        category1_button.setOnClickListener {
            //Start next activity
        }
    }


}