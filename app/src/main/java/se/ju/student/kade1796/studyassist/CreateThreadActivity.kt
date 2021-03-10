package se.ju.student.kade1796.studyassist

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class CreateThreadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_thread)


        //Populate with dummy data
        DatabaseFirestore.instance.dummyData()
        DatabaseFirestore.instance.getAllCategories()
        val emptyPostList = mutableListOf<Posts>()
        val newThread = Threads("Title", "String content", emptyPostList, "Campus")
        DatabaseFirestore.instance.getAllThreadsInCategory("Campus") { allThreads ->
            Log.d("CallbackHell", "List of Threads is: $allThreads")
        }
        DatabaseFirestore.instance.getThreadById("9LeCNW7J5xid7jzxJpYa", "Campus") {
            Log.d("testingCallback", "it is: $it")
        }
        DatabaseFirestore.instance.getThreadsByTitle("Dennis title_testing","Campus"){
            Log.d("getThreadsByTitle", "Threads grabbed by title are: $it")
        }


        //populate with dummy data


        val spinner = findViewById<Spinner>(R.id.spinner)
        val title = findViewById<EditText>(R.id.title_edit_text).editableText
        val content = findViewById<EditText>(R.id.content_edit_text).editableText
        println(title.toString())

        ArrayAdapter.createFromResource(
            this,
            R.array.categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
                Toast.makeText(
                    applicationContext,
                    parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        val createButton = findViewById<Button>(R.id.create_button)

        createButton.setOnClickListener {
            val intent = Intent(this, ThreadsActivity::class.java)
            intent.putExtra("title", title.toString())
            intent.putExtra("content", content.toString())
            startActivity(intent)
        }


    }
}