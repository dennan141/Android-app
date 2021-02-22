package se.ju.student.kade1796.studyassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class ThreadsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_threads)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
    }
}
