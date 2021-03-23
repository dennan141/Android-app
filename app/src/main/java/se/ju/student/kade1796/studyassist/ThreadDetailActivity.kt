package se.ju.student.kade1796.studyassist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
//import kotlinx.android.synthetic.main.activity_thread_detail.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_thread_detail.*

class ThreadDetailActivity : AppCompatActivity() {
    private val commentList: MutableList<Comment> = ArrayList()
    private val db = DatabaseFirestore.instance
    private var thread = Threads()
    private lateinit var recyclerView: RecyclerView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread_detail)

        val currentUser = Authentication.instance.getCurrentUser()

        recyclerView = findViewById(R.id.recyclerView)
        val editButton = findViewById<Button>(R.id.editButton)
        val deleteButton = findViewById<Button>(R.id.deleteButton)
        val createCommentButton = findViewById<Button>(R.id.createCommentButton)
        val commentText = findViewById<EditText>(R.id.comment_edittext)

        if (currentUser != null && thread.userId == currentUser.uid) {
            Log.d("ifCheck", "${currentUser.uid} and  ${thread.userId}")
            editButton.visibility = View.VISIBLE;
            deleteButton.visibility = View.VISIBLE;
        } else {
            editButton.visibility = View.INVISIBLE;
            deleteButton.visibility = View.INVISIBLE;
        }

        //TODO: Fix edit thread
        recyclerView.adapter = CommentAdapter(commentList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        createCommentButton.setOnClickListener {
            if (Authentication.instance.getCurrentUser() != null) {
                if (commentText.editableText.isEmpty() || commentText.editableText.length > 100) {
                    commentText.error = getString(R.string.comment_text_invalid)
                } else {
                    val commentThreadId = intent.getStringExtra("id")
                    val commentContent = commentText.text.toString()
                    val category = intent.getStringExtra("category")
                    val comment = Comment(category, commentContent, commentThreadId)
                    db.addComment(comment)
                    val commentList = mutableListOf<Comment>(comment)
                    (recyclerView.adapter as CommentAdapter).addPosts(commentList)

                    val builder = AlertDialog.Builder(this)
                    builder.setTitle(R.string.comment_created)
                    builder.setMessage(R.string.succesfully_created_comment)
                    builder.setNeutralButton(R.string.ok) { dialog, which ->
                    }
                    builder.show()
                }
            }else{
                val builder = AlertDialog.Builder(this)
                builder.setTitle(R.string.not_logged_in)
                builder.setMessage(R.string.not_authorized_comment)
                builder.setNeutralButton(R.string.ok) { dialog, which ->
                }
                builder.show()
            }
        }

        deleteButton.setOnClickListener {
            val categoryName = intent.getStringExtra("category")
            val intent = Intent(this, ThreadsActivity::class.java)
            intent.putExtra("categoryTitle", categoryName.toString())
            DatabaseFirestore.instance.deleteThread(thread)
            finish()
            startActivity(intent)
        }

        loadThread()
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