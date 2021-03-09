package se.ju.student.kade1796.studyassist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
//import kotlinx.android.synthetic.main.activity_thread_detail.*
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ThreadDetailActivity : AppCompatActivity() {
    private val commentList = dummyList()
    private val adapter = CommentAdapter(commentList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_thread_detail)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        var title = findViewById<TextView>(R.id.titleText)
        var content = findViewById<TextView>(R.id.contentText)
        title.text  = intent.getStringExtra("title").toString()
        content.text = intent.getStringExtra("content").toString()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun dummyList() : ArrayList<Posts>{
        val list = mutableListOf(
            Posts("comment rere", "dsadsa"),
            Posts("comment 2", "rrrr"),
            Posts("Posts 3", "rrrr"),
            Posts("Posts 4", "rrrr"),
            Posts("Posts 5", "rrrr"),
            Posts("Posts 6", "rrrr"),
            Posts("Posts 7", "rrrr"),
            Posts("Posts 8", "rrrr"),
            Posts("Posts 9", "rrrr"),
            Posts("Posts 465", "rrrr"),
            Posts("Posts 7", "rrrr"),
            Posts("Posts 8", "rrrr"),
            Posts("Posts 9", "rrrr"),
            Posts("comment 465", "rrrr"),
            Posts("thread 56", "rrrr")
        )
        return list as ArrayList<Posts>
    }

}