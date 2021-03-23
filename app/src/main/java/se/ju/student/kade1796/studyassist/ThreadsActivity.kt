package se.ju.student.kade1796.studyassist


import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class ThreadsActivity : AppCompatActivity(), ThreadAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView;
    private var threadList: MutableList<Threads> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_threads)

        val category = intent.getStringExtra("categoryTitle").toString()
        recyclerView = findViewById(R.id.recyclerView)
        val categoryText = findViewById<TextView>(R.id.categoryText)
        categoryText.text = category


        recyclerView.adapter = ThreadAdapter(threadList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        DatabaseFirestore.instance.getAllThreadsInCategory(
            category,
            recyclerView.adapter as ThreadAdapter
        )

        //Search function, assign variable
        val searchTextView = findViewById<TextView>(R.id.search_textView)

        searchTextView.setOnClickListener {
            //Initialize dialog
            val dialog = Dialog(this)
            //Set custom dialog
            dialog.setContentView(R.layout.dialog_search_layout)
            //Set custom height and width, set transparent background
            dialog.window?.setLayout(1000, 1200)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            //Show dialog
            dialog.show()

            //Initialize and assign variable
            var editText = dialog.findViewById<EditText>(R.id.edit_text)
            var listView = dialog.findViewById<ListView>(R.id.list_view)

            //Create array of thread titles
            var threadTitles: List<String?> = threadList.map {
                it.title
            }

            //Initialize array adapter
            var arrayAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_expandable_list_item_1,
                threadTitles
            )

            //Set adapter
            listView.adapter = arrayAdapter

            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    //Filter array list
                    arrayAdapter.filter.filter(s)
                }
            })

            listView.setOnItemClickListener { parent, view, position, id ->
                //Code based of code from: "override fun onItemClick(position: Int)"

                //val thread = parent.getItemAtPosition(position) as Threads
                //Måste hitta rätt position när arrayAdaptern filteras. Kanske en funktion som väljer rätt thread-position baserat på titel?
                val thread = threadList[position]
                recyclerView.adapter!!.notifyItemChanged(position)
                val intent = Intent(this, ThreadDetailActivity::class.java)

                thread.posts as ArrayList<Comment>

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

                dialog.dismiss()
            }
        }
    }

    override fun onItemClick(position: Int) {
        val thread = threadList[position]
        recyclerView.adapter!!.notifyItemChanged(position)
        val intent = Intent(this, ThreadDetailActivity::class.java)

        thread.posts as ArrayList<Comment>

        intent.putExtra("id", thread.id)
        intent.putExtra("category", thread.category)
        intent.putExtra("title", thread.title)
        intent.putExtra("content", thread.content)
        val args = Bundle()
        args.putSerializable("bundlePosts", thread.posts)
        intent.putExtra("bundleArgs", args)
        intent.putExtra("likes", thread.likes)
        intent.putExtra("userId", thread.userId)
        finish()
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