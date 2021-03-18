package se.ju.student.kade1796.studyassist

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ThreadsActivity : AppCompatActivity(), ThreadAdapter.OnItemClickListener {
    private val db = DatabaseFirestore.instance
    private var threadList = DatabaseFirestore.listthreads
    private val adapter = ThreadAdapter(threadList, this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_threads)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val categoryText = findViewById<TextView>(R.id.categoryText)
        categoryText.text = intent.getStringExtra("category").toString()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.hasFixedSize()
    /*val startIndex = 0
        val newItem = Thread(title, content)
        threadList.add(startIndex, newItem)
        adapter.notifyItemInserted(startIndex)*/





        //THIS IS ONLY FOR TESTING AND CAN SAFELY BE REMOVED
        //*****************************************************************************



    }


    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()
        val clickedItem = threadList[position]
        adapter.notifyItemChanged(position)
        val intent = Intent(this, ThreadDetailActivity::class.java)
        intent.putExtra("title", clickedItem.title)
        intent.putExtra("content", clickedItem.content)
        intent.putExtra("likes", clickedItem.likes)
        startActivity(intent)
    }

    override fun add(position: Int) {
        threadList[position].likes += 1
        adapter.notifyItemChanged(position)
        val likes = threadList[position].likes
        val id = threadList[position].id
        println(id)
        println(likes)
        if (id != null) {
            db.updateLikes(id, likes)
        }
    }



}
