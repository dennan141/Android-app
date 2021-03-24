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

class ThreadsActivity : AppCompatActivity(), ThreadAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView;
    private var threadList: MutableList<Threads> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_threads)

        val category = intent.getStringExtra(getString(R.string.categoryTitle_intent)).toString()
        recyclerView = findViewById(R.id.recyclerView)
        val categoryText = findViewById<TextView>(R.id.categoryText)
        categoryText.text = category

        println("recyclerview: " + this::recyclerView.isInitialized)
        println("getAllThreadsInCategory $DatabaseFirestore.instance.getAllThreadsInCategory(category)")


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
            var searchAdapter = SearchAdapter(
                this,
                R.layout.dialog_search_item,
                threadList as ArrayList<Threads>
            )

            //Set adapter
            listView.adapter = searchAdapter

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
                    searchAdapter.filter.filter(s)
                }
            })

            listView.setOnItemClickListener { parent, view, position, id ->
                //Code based of code from: "override fun onItemClick(position: Int)"

                val thread = parent.getItemAtPosition(position) as Threads
                recyclerView.adapter!!.notifyItemChanged(position)
                val intent = Intent(this, ThreadDetailActivity::class.java)

                thread.posts as ArrayList<Comment>

                intent.putExtra(getString(R.string.id_intent), thread.id)
                intent.putExtra(getString(R.string.threadCategory_intent), thread.category)
                intent.putExtra(getString(R.string.threadTitle_intent), thread.title)
                intent.putExtra(getString(R.string.threadContent_intent), thread.content)
                val args = Bundle()
                args.putSerializable(getString(R.string.bundlePosts_key), thread.posts)
                intent.putExtra(getString(R.string.bundleArgs_intent), args)
                intent.putExtra(getString(R.string.likes_intent), thread.likes)
                intent.putExtra(getString(R.string.userId_intent), thread.userId)
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

        intent.putExtra(getString(R.string.id_intent), thread.id)
        intent.putExtra(getString(R.string.threadCategory_intent), thread.category)
        intent.putExtra(getString(R.string.threadTitle_intent), thread.title)
        intent.putExtra(getString(R.string.threadContent_intent), thread.content)
        val args = Bundle()
        args.putSerializable(getString(R.string.bundlePosts_key), thread.posts)
        intent.putExtra(getString(R.string.bundleArgs_intent), args)
        intent.putExtra(getString(R.string.likes_intent), thread.likes)
        intent.putExtra(getString(R.string.userId_intent), thread.userId)
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