package se.ju.student.kade1796.studyassist


import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ThreadsActivity : AppCompatActivity(), ThreadAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView;
    private var threadList:MutableList<Threads> = arrayListOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_threads)

        val category = intent.getStringExtra("category").toString()
        recyclerView = findViewById(R.id.recyclerView)
        val categoryText = findViewById<TextView>(R.id.categoryText)
        categoryText.text = category

        println("recyclerview: " + this::recyclerView.isInitialized )
        println("getAllThreadsInCategory $DatabaseFirestore.instance.getAllThreadsInCategory(category)")

        recyclerView.adapter = ThreadAdapter(threadList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        DatabaseFirestore.instance.getAllThreadsInCategory("Campus",
            recyclerView.adapter as ThreadAdapter
        )
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()
        val thread = threadList[position]
        recyclerView.adapter!!.notifyItemChanged(position)
        val intent = Intent(this, ThreadDetailActivity::class.java)

        thread.posts as ArrayList<Posts>

        intent.putExtra("id", thread.id)
        intent.putExtra("category", thread.category)
        intent.putExtra("title", thread.title)
        intent.putExtra("content", thread.content)
        val args = Bundle()
        args.putSerializable("bundlePosts", thread.posts)
        intent.putExtra("bundleArgs", args)
        intent.putExtra("likes", thread.likes)
        intent.putExtra("userId", thread.userId)
        startActivity(intent)
    }

    override fun addLikes(position: Int) {
        threadList[position].likes = threadList[position].likes?.plus(1)
        recyclerView.adapter!!.notifyItemChanged(position)
        val likes = threadList[position].likes
        val thread = threadList[position]

        if (thread != null)
            DatabaseFirestore.instance.updateLikes(thread, likes!!)
    }
}