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


        //************************* DUMMY DATA *******************************************
        //DatabaseFirestore.instance.dummyData()
        DatabaseFirestore.instance.getAllCategories()
        val emptyPostList = mutableListOf<Posts>()

        DatabaseFirestore.instance.getThreadById("Aq3816gTNPaiwDrolCwL", "Campus") {
            //DatabaseFirestore.instance.deleteThread(it)
            val newThread = Threads("Title", "String content", 3, emptyPostList, "Campus")


            //FINNS TVÅ OLIKA FUNKTIONER I DATABASEN SOM TOG IN SAMMA OCH HETTE SAMMA!
            //JAG DÖPTE OM DEM TILL FÖLJANDE:
            //***********************************************************************
            DatabaseFirestore.instance.loadAllThreadsInCategory("Campus")
            DatabaseFirestore.instance.getAllThreadsInCategory("Campus")
            //***********************************************************************


            DatabaseFirestore.instance.getThreadsByTitle("Dennis title_testing", "Campus") {
                Log.d("getThreadsByTitle", "Threads grabbed by title are: $it")
            }
            //************************* DUMMY DATA *******************************************

            val spinner = findViewById<Spinner>(R.id.spinner)
            val title = findViewById<EditText>(R.id.title_editText)
            val content = findViewById<EditText>(R.id.content_editText)
            var category = findViewById<TextView>(R.id.categoryText)
            val createButton = findViewById<Button>(R.id.create_button)

            //***************** TO BE USED OR WHAT?? *******************************
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
            //***************** TO BE USED OR WHAT?? *******************************

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

            //********************* ON CLICK CREATE THREAD ************************
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
            //********************* ON CLICK CREATE THREAD ************************
        }
    }
}


//*********************** PRIVATE FUNCTIONS ************************

private fun validateTitleText(editText: EditText): Boolean {
    return (editText.text.length in 6..29)
}

private fun validateContentText(editText: EditText): Boolean {
    return (editText.text.length in 10..300)
}

//*********************** PRIVATE FUNCTIONS ************************