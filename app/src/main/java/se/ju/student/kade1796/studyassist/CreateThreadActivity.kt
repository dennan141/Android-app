package se.ju.student.kade1796.studyassist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CreateThreadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_thread)

        val spinner = findViewById<Spinner>(R.id.spinner)
        val title = findViewById<EditText>(R.id.title_editText)
        val content = findViewById<EditText>(R.id.content_editText)
        var category = findViewById<TextView>(R.id.categoryText)
        val createButton = findViewById<Button>(R.id.create_button)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                pos: Int,
                id: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        //********************* ON CLICK CREATE THREAD ************************
        createButton.setOnClickListener {
            if (!validateTitleText(title)) {
                title.error = getString(R.string.title_text_invalid)
            } else if (!validateContentText(content)) {
                content.error = getString(R.string.content_text_invalid)
            } else {
                val intent = Intent(this, ThreadsActivity::class.java)
                intent.putExtra(getString(R.string.threadCategory_intent), category.text)
                intent.putExtra(getString(R.string.threadTitle_intent), title.toString())
                intent.putExtra(getString(R.string.threadContent_intent), content.toString())
                startActivity(intent)
            }
        }
        //********************* ON CLICK CREATE THREAD ************************
    }
}

//*********************** PRIVATE FUNCTIONS ************************
private fun validateTitleText(editText: EditText): Boolean {
    val titleMinLimit = 6
    val titleMaxLimit = 29
    return (editText.text.length in titleMinLimit..titleMaxLimit)
}

private fun validateContentText(editText: EditText): Boolean {
    val contentMinLimit = 10
    val contentMaxLimit = 300
    return (editText.text.length in contentMinLimit..contentMaxLimit)
}
//*********************** PRIVATE FUNCTIONS ************************