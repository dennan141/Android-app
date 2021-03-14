package se.ju.student.kade1796.studyassist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_threads.*

class ThreadsActivity : AppCompatActivity(), ThreadAdapter.OnItemClickListener {
    private val threadList = list()
    private val adapter = ThreadAdapter(threadList, this)
    private var likesCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_threads)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val categoryText = findViewById<TextView>(R.id.categoryText)
        categoryText.text = intent.getStringExtra("category").toString()

        val title  = intent.getStringExtra("title").toString()
        val content = intent.getStringExtra("content").toString()
        val category = intent.getStringExtra("category").toString()


        /*val startIndex = 0
        val newItem = Thread(title, content)
        threadList.add(startIndex, newItem)
        adapter.notifyItemInserted(startIndex)*/





        //THIS IS ONLY FOR TESTING AND CAN SAFELY BE REMOVED
        //*****************************************************************************



    }


    override fun onItemClick(position: Int) {
        Toast.makeText(this,"Item $position clicked", Toast.LENGTH_SHORT).show()
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
    }

    private fun list(): MutableList<Thread>{
        val list = mutableListOf(
                Thread("thread rere", "dsadsa", 0),
                Thread("thread 2", "rrrr", 2),
                Thread("thread 3", "rrrr", 1),
                Thread("thread 4", "rrrr", 1),
                Thread("thread 5", "rrrr", 1),
                Thread("thread 6", "rrrr", 1),
                Thread("thread 7", "rrrr", 2),
                Thread("thread 8", "rrrr", 5),
                Thread("thread 9", "rrrr",11),
                Thread("thread 465", "rrrr", 44),
                Thread("thread 7", "rrrr",33),
                Thread("thread 8", "rrrr",1),
                Thread("thread 9", "rrrr",0),
                Thread("thread 465", "rrrr",1),
                Thread("thread 56", "rrrr",1)
        )
        return list
    }
}
