package se.ju.student.kade1796.studyassist

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ThreadsActivity : AppCompatActivity() {
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







}