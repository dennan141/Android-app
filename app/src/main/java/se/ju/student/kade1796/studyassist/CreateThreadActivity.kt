package se.ju.student.kade1796.studyassist

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_create_thread.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateThreadActivity : AppCompatActivity() {
    val database = DatabaseFirestore.instance
    var GlobalData = mutableListOf<Threads>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_thread)


        val spinner = findViewById<Spinner>(R.id.spinner)
        val title = findViewById<EditText>(R.id.title_editText)
        val content = findViewById<EditText>(R.id.content_editText)
        var category = findViewById<TextView>(R.id.categoryText)

//*
//   ArrayAdapter.createFromResource(
//            this,
//            R.array.categories,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            spinner.adapter = adapter
//        }
//
// */

        val createButton = findViewById<Button>(R.id.create_button)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                pos: Int,
                id: Long
            ) {
                // An item was selected. You can retrieve the selected item using
                Toast.makeText(
                    applicationContext,
                    parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT
                ).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        createButton.setOnClickListener {


            if (!validateTitleText(title)) {
                title.error = getString(R.string.titleTextInvalid)
            } else if (!validateContentText(content)) {
                content.error = getString(R.string.contentTextInvalid)
            } else {
                val intent = Intent(this, ThreadsActivity::class.java)
                intent.putExtra("category", category.text)
                intent.putExtra("title", title.toString())
                intent.putExtra("content", content.toString())
                startActivity(intent)
            }
        }
    }


    private fun validateTitleText(editText: EditText): Boolean {
        return (editText.text.length in 6..29)
    }

    private fun validateContentText(editText: EditText): Boolean {
        return (editText.text.length in 10..300)
    }
}

