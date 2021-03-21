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

        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner = view.findViewById<Spinner>(R.id.spinner)
        val title = view.findViewById<EditText>(R.id.title_editText)
        val content = view.findViewById<EditText>(R.id.content_editText)
        val category = view.findViewById<TextView>(R.id.categoryText)
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
                val threadCategory = category.text.toString()

                addThreadToDb(threadTitle, threadContent, threadCategory)

                val intent = Intent(this.context, ThreadDetailActivity::class.java)
                intent.putExtra("title", threadTitle)
                intent.putExtra("content", threadContent)
                intent.putExtra("category", threadCategory)

                val args = Bundle()
                val posts = ArrayList<Posts>()
                args.putSerializable("bundlePosts", posts)
                intent.putExtra("bundleArgs", args)

                intent.putExtra("userId", DatabaseFirestore.instance.auth.currentUser!!.uid)
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
        threadCategory: String
    ) {
        db.addThread(
            Threads(
                threadTitle, threadContent, threadCategory, DatabaseFirestore.instance.auth.currentUser?.uid
            )
        )
    }

}