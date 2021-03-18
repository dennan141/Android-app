package se.ju.student.kade1796.studyassist

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
//import kotlinx.android.synthetic.main.activity_thread_detail.*
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ThreadDetailActivity : AppCompatActivity(), CommentAdapter.OnItemClickListener {
    private val commentList = dummyList()
    private val adapter = CommentAdapter(commentList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_thread_detail)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        var title = findViewById<TextView>(R.id.titleText)
        var content = findViewById<TextView>(R.id.contentText)
        var likes = findViewById<TextView>(R.id.likesText)

        title.text  = intent.getStringExtra("title")
        content.text = intent.getStringExtra("content")
        println(intent.getStringExtra("title"))
        likes.text = intent.getIntExtra("likes",0).toString()
        var likesCounter = intent.getIntExtra("likes", 0)


        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val likeButton = findViewById<ImageButton>(R.id.likeButton)

        likeButton.setOnClickListener {
            likesCounter +=1
            likes.text = likesCounter.toString()
        }



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

    override fun add(position: Int) {
        commentList[position].likes = commentList[position].likes?.plus(1)
        adapter.notifyItemChanged(position)
    }

}