package se.ju.student.kade1796.studyassist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_thread_detail.*
import android.os.Bundle
import android.widget.TextView
import android.widget.Button

class ThreadDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_thread_detail)

        val titleTextView = findViewById<TextView>(R.id.titleText)
        val contentTextView = findViewById<TextView>(R.id.contentText)
        val title  = intent.getStringExtra("title").toString()
        val content  = intent.getStringExtra("content").toString()
        titleTextView.text = title
        contentTextView.text = content
    }
}