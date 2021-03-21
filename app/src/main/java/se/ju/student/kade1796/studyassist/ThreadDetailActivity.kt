package se.ju.student.kade1796.studyassist

import androidx.appcompat.app.AppCompatActivity
//import kotlinx.android.synthetic.main.activity_thread_detail.*
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_thread_detail.*

class ThreadDetailActivity : AppCompatActivity(), CommentAdapter.OnItemClickListener {
    private val commentList:MutableList<Posts> = ArrayList()
    private val db = DatabaseFirestore.instance
    private var thread = Threads()
    private lateinit var recyclerView: RecyclerView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread_detail)

        val currentUser = DatabaseFirestore.instance.auth.currentUser;

        recyclerView = findViewById(R.id.recyclerView)
        val editButton = findViewById<Button>(R.id.editButton)
        val deleteButton = findViewById<Button>(R.id.deleteButton)

        if(currentUser != null && thread.userId == currentUser.uid) {
            editButton.visibility = View.VISIBLE;
            deleteButton.visibility = View.VISIBLE;
        }

        deleteButton.setOnClickListener {
            DatabaseFirestore.instance.deleteThread(thread)
            finish();
        }

        //TODO: Fix edit thread

        recyclerView.adapter = CommentAdapter(commentList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadThread()
    }

    override fun addLikes(position: Int) {
        commentList[position].likes = commentList[position].likes?.plus(1)

        /*if (thread != null)
            DatabaseFirestore.instance.updateCommentLikes(thread, likes!!)
        recyclerView.adapter!!.notifyItemChanged(position)*/
    }

    private fun loadThread() {
        var titleText = findViewById<TextView>(R.id.titleText)
        var contentText = findViewById<TextView>(R.id.contentText)
        var likesText = findViewById<TextView>(R.id.likesText)

        val id = intent.getStringExtra("id")
        val category = intent.getStringExtra("category")
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val args = intent.getBundleExtra("bundleArgs")
        val posts = args!!.getSerializable("bundlePosts") as MutableList<Posts>;
        var likes = intent.getIntExtra("likes", 0)
        val userId = intent.getStringExtra("userId");

        titleText.text = title
        contentText.text = content
        likesText.text = likes.toString()
        likeButton.setOnClickListener{
            likes+=1
            likesText.text = likes.toString()
            db.updateLikes(thread, likes)
        }
        (recyclerView.adapter as CommentAdapter).addPosts(posts)
        thread = Threads(title, content, category, userId, likes!!.toInt(), posts, id);
    }
}