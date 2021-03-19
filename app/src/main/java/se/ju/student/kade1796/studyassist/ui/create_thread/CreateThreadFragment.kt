package se.ju.student.kade1796.studyassist.ui.create_thread

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import se.ju.student.kade1796.studyassist.*

class CreateThreadFragment : Fragment() {

    private lateinit var createThreadViewModel: CreateThreadViewModel
    private val db = DatabaseFirestore.instance

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createThreadViewModel =
            ViewModelProvider(this).get(CreateThreadViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_create, container, false)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Populate with dummy data
        db.dummyData()
        db.getAllCategories()
        val emptyPostList = mutableListOf<Posts>()
        val newThread = Threads("Title", "String content", 4, emptyPostList, "Campus")
        db.getAllThreadsInCategory("Campus")
        db.getThreadById("9LeCNW7J5xid7jzxJpYa", "Campus") {
            Log.d("testingCallback", "it is: $it")
        }
        db.getThreadsByTitle("Dennis title_testing","Campus"){
            Log.d("getThreadsByTitle", "Threads grabbed by title are: $it")
        }

        val spinner = view.findViewById<Spinner>(R.id.spinner)
        val title = view.findViewById<EditText>(R.id.title_editText)
        val content = view.findViewById<EditText>(R.id.content_editText)
        var category = view.findViewById<TextView>(R.id.categoryText)
        val createButton = view.findViewById<Button>(R.id.create_button)

        activity?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.categories,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
                category.text = parent.getItemAtPosition(pos).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        createButton.setOnClickListener{
            if(!validateTitleText(title)){
                title.error = getString(R.string.titleTextInvalid)
            }else if(!validateContentText(content)){
                content.error = getString(R.string.contentTextInvalid)
            }else{
                val threadTitle = title.text.toString()
                val threadContent = content.text.toString()
                val threadCategory = category.text
                addThreadToDb(threadTitle, threadContent, emptyPostList, threadCategory)

                val intent = Intent(this.context, ThreadDetailActivity::class.java)
                intent.putExtra("id", threadTitle)
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

    private fun addThreadToDb(
        threadTitle: String,
        threadContent: String,
        emptyPostList: MutableList<Posts>,
        threadCategory: CharSequence
    ) {
        db.addThread(Threads(threadTitle, threadContent, 0, emptyPostList, threadCategory.toString()))

    }

}