package se.ju.student.kade1796.studyassist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_thread.*

class CreateThreadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_thread)



        //Populate with dummy data
        DatabaseFirestore.instance.dummyData()
        DatabaseFirestore.instance.getAllCategories()
        //populate with dummy data


        val spinner = findViewById<Spinner>(R.id.spinner)
        val title = findViewById<EditText>(R.id.title_edit_text).editableText
        val content = findViewById<EditText>(R.id.content_edit_text).editableText
        var category = findViewById<TextView>(R.id.categoryText)

        ArrayAdapter.createFromResource(
            this,
            R.array.categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long){
                category.text = parent.getItemAtPosition(pos).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        Toast.makeText(this, category.text, Toast.LENGTH_SHORT)


        val createButton = findViewById<Button>(R.id.create_button)

        createButton.setOnClickListener{
            val intent = Intent(this, ThreadsActivity::class.java)
            intent.putExtra("category", category.text)
            intent.putExtra("title", title.toString())
            intent.putExtra("content", content.toString())
            startActivity(intent)
        }
    }


}