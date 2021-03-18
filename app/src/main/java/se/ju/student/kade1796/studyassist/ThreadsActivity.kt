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
import com.google.firebase.firestore.FirebaseFirestore

class ThreadsActivity : AppCompatActivity(), ThreadAdapter.OnItemClickListener {
    private val threadList = mutableListOf<Threads>()
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
        val newItem = Threads(title, content)
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
                it.title!!
            }

            //Initialize array adapter
            var arrayAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_expandable_list_item_1,
                threadList)

            //Set adapter
            listView.adapter = arrayAdapter

            editText.addTextChangedListener(object: TextWatcher {
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

    override fun add(position: Int) {
        TODO("Not yet implemented")
    }


}
