package se.ju.student.kade1796.studyassist

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_threads.*

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

        //Search function, assign variable
        val searchTextView = findViewById<TextView>(R.id.search_textView)

        searchTextView.setOnClickListener{
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
            var threadTitles : List<String> = threadList.map{
                it.title
            }

            //Initialize array adapter
            var arrayAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_expandable_list_item_1,
                threadList)

            //Set adapter
            listView.adapter = arrayAdapter

            editText.addTextChangedListener(object: TextWatcher{
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

            listView.setOnItemClickListener{ parent, view, position, id ->  
                //Code based of code from: "override fun onItemClick(position: Int)"
                var clickedItem = parent.getItemAtPosition(position) as Thread

                adapter.notifyItemChanged(position)
                val intent = Intent(this, ThreadDetailActivity::class.java)
                intent.putExtra("title", clickedItem.title)
                intent.putExtra("content", clickedItem.content)
                startActivity(intent)

                dialog.dismiss()
            }
        }
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
            Thread("Thread 1", "dsadsa"),
            Thread("Thread 2", "rrrr"),
            Thread("Thread 3", "adsffdsvs"),
            Thread("Thread 4", "vxbxcbx"),
            Thread("Thread 5", "rrteryhbfd"),
            Thread("Thread 6", "rdddd"),
            Thread("Thread 7", "gesdzgvdfn"),
            Thread("Thread 8", "yurtyhryt"),
            Thread("Thread 9", "dfgdg"),
            Thread("If the universe is so big, why won't it fight me?", "sdgdfvr"),
            Thread("I really hope this works!", "tjyjg"),
            Thread("I sure do!", "dgfdgd"),
            Thread("Did you know that male seahorses get pregnant?", "rrrr"),
        )
        return list as ArrayList<Thread>



    }
}
