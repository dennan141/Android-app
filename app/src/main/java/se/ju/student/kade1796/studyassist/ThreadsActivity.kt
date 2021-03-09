package se.ju.student.kade1796.studyassist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import android.provider.ContactsContract
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class ThreadsActivity : AppCompatActivity(), ThreadAdapter.OnItemClickListener {
    private val threadList = dummyList()
    private val adapter = ThreadAdapter(threadList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_threads)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val categoryText = findViewById<TextView>(R.id.categoryText)
        categoryText.text = intent.getStringExtra("category").toString()


        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        val title  = intent.getStringExtra("title").toString()
        val content = intent.getStringExtra("content").toString()
        println(title)
        val startIndex = 0
        val newItem = Thread(title, content)
        threadList.add(startIndex, newItem)
        adapter.notifyItemInserted(startIndex)





        //THIS IS ONLY FOR TESTING AND CAN SAFELY BE REMOVED
        //*****************************************************************************


        //---------------------Thread can now be added using this -----------------------
        //-----------------------DUMMY DATA---------------------------
        //Creates lists for dummy data
        var mutableListOfPosts = mutableListOf<Posts>()
        var mutableListOfThreads = mutableListOf<Threads>()
        //Creates object
        val newPost1 = Posts("CONTENT IN NEW POST 1")
        val newPost2 = Posts("CONTENT IN NEW POST 2")

        mutableListOfPosts.add(newPost1)
        mutableListOfPosts.add(newPost2)
        val newThread = Threads("TestingTitle", "TestingContent", mutableListOfPosts, "Other")
        //mutableListOfThreads.add(newThread)
        val newCategory = Categories("TESTING_TITLE_5")
        //-----------------------DUMMY DATA---------------------------


        DatabaseFirestore.instance.addCategory(newCategory)
        DatabaseFirestore.instance.addThread(newThread)
        val listOfThreads = DatabaseFirestore.instance.getAllThreadsInCategory("Campus")


        //---------------------Thread can now be added using this -----------------------


        //***************************************************************************
        //THIS IS ONLY FOR TESTING AND CAN SAFELY BE REMOVED








    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this,"Item $position clicked", Toast.LENGTH_SHORT).show()
        val clickedItem = threadList[position]
        adapter.notifyItemChanged(position)
        val intent = Intent(this, ThreadDetailActivity::class.java)
        intent.putExtra("title", clickedItem.title)
        intent.putExtra("content", clickedItem.content)
        startActivity(intent)
    }

    private fun dummyList() : ArrayList<Thread>{
        val list = mutableListOf(
            Thread("thread rere", "dsadsa"),
            Thread("thread 2", "rrrr"),
            Thread("thread 3", "rrrr"),
            Thread("thread 4", "rrrr"),
            Thread("thread 5", "rrrr"),
            Thread("thread 6", "rrrr"),
            Thread("thread 7", "rrrr"),
            Thread("thread 8", "rrrr"),
            Thread("thread 9", "rrrr"),
            Thread("thread 465", "rrrr"),
            Thread("thread 7", "rrrr"),
            Thread("thread 8", "rrrr"),
            Thread("thread 9", "rrrr"),
            Thread("thread 465", "rrrr"),
            Thread("thread 56", "rrrr")
        )
        return list as ArrayList<Thread>



    }
}
