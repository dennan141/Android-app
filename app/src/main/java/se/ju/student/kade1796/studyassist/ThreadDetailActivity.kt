package se.ju.student.kade1796.studyassist

import androidx.appcompat.app.AppCompatActivity
//import kotlinx.android.synthetic.main.activity_thread_detail.*
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_thread_detail.*

class ThreadDetailActivity : AppCompatActivity(), CommentAdapter.OnItemClickListener {
    private val commentList = dummyList()
    private val adapter = CommentAdapter(commentList, this)
    private val db = DatabaseFirestore.instance
    private var likesCounter = 0
    private var thread = Threads()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_thread_detail)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val threadId = intent.getStringExtra("id").toString()
        load(threadId)


        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }
    override fun add(position: Int) {
        commentList[position].likes = commentList[position].likes?.plus(1)
        adapter.notifyItemChanged(position)
    }



    // ***************** PRIVATE FUNCTIONS **************************

    private fun load(threadId: String) {
        var title = findViewById<TextView>(R.id.titleText)
        var content = findViewById<TextView>(R.id.contentText)
        var likes = findViewById<TextView>(R.id.likesText)

        val loadingDialog = LoadingDialog(this)
        loadingDialog.startLoadingDialogActivity()
        Handler(Looper.getMainLooper()).postDelayed({
            loadingDialog.dismissDialog()
            DatabaseFirestore.instance.getThreadById(threadId, "Campus") {
                Log.d("testingCallback", "it is: $it")
                title.text  = it.title
                content.text = it.content
                likes.text = it.likes.toString()
                likesCounter = it.likes
                thread = it
            }

        }, 500)
        likeButton.setOnClickListener{
            db.updateLikes(thread, likesCounter)
            likesCounter+=1
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
    // ***************** PRIVATE FUNCTIONS **************************

}