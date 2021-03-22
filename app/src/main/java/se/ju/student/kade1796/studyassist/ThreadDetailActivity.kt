package se.ju.student.kade1796.studyassist

import androidx.appcompat.app.AppCompatActivity
//import kotlinx.android.synthetic.main.activity_thread_detail.*
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_thread_detail.*

class ThreadDetailActivity : AppCompatActivity(), CommentAdapter.OnItemClickListener {
    private val commentList: MutableList<Comment> = ArrayList()
    private val db = DatabaseFirestore.instance
    private var thread = Threads()
    private var threadList:MutableList<Threads> = arrayListOf()
    private lateinit var recyclerView: RecyclerView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread_detail)

        val currentUser = DatabaseFirestore.instance.auth.currentUser

        recyclerView = findViewById(R.id.recyclerView)
        val editButton = findViewById<Button>(R.id.editButton)
        val deleteButton = findViewById<Button>(R.id.deleteButton)
        val createCommentButton = findViewById<Button>(R.id.createCommentButton)
        val commentText = findViewById<EditText>(R.id.comment_edittext)

        if (currentUser != null && thread.userId == currentUser.uid) {
            editButton.visibility = View.VISIBLE;
            deleteButton.visibility = View.VISIBLE;
        }

        //TODO: Fix edit thread
        recyclerView.adapter = CommentAdapter(commentList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)

        createCommentButton.setOnClickListener {

            if (commentText.editableText.isEmpty() || commentText.editableText.length > 100) {
                commentText.error = getString(R.string.commentTextInvalid)
            } else {
                val commentThreadId = intent.getStringExtra("id")
                val commentContent = commentText.text.toString()
                val category = intent.getStringExtra("category")
                // println(Comment(commentThreadId, commentContent, category))
                val comment = Comment(category, commentContent, commentThreadId)
                db.addComment(comment)

                val commentList = mutableListOf<Comment>(comment)
                println("commentList $commentList")
                (recyclerView.adapter as CommentAdapter).addPosts(commentList)
            }
        }

        deleteButton.setOnClickListener {
            DatabaseFirestore.instance.deleteThread(thread)
            finish()
        }

        loadThread()
    }


    override fun addLikes(position: Int) {
        commentList[position].likes = commentList[position].likes?.plus(1)
        var comment = Comment(commentList[position].category, commentList[position].content, commentList[position].threadId, commentList[position].likes)
        if (thread != null)
            DatabaseFirestore.instance.updateCommentLikes(comment)
        
    }

    private fun loadThread() {
        val titleText = findViewById<TextView>(R.id.titleText)
        val contentText = findViewById<TextView>(R.id.contentText)
        val likesText = findViewById<TextView>(R.id.likesText)

        val id = intent.getStringExtra("id")
        val category = intent.getStringExtra("category")
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val args = intent.getBundleExtra("bundleArgs")
        val posts = args!!.getSerializable("bundlePosts") as MutableList<Comment>;
        var likes = intent.getIntExtra("likes", 0)
        val userId = intent.getStringExtra("userId");

        titleText.text = title
        contentText.text = content
        likesText.text = likes.toString()
        likeButton.setOnClickListener {
            likes += 1
            likesText.text = likes.toString()
            db.updateLikes(thread, likes)
        }
        (recyclerView.adapter as CommentAdapter).addPosts(posts)
        thread = Threads(title, content, category, userId, likes, posts, id);
    }
}