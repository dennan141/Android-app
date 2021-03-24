package se.ju.student.kade1796.studyassist.ui.create_thread

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import se.ju.student.kade1796.studyassist.*

class CreateThreadFragment : Fragment() {
    private lateinit var createThreadViewModel: CreateThreadViewModel
    private val db = DatabaseFirestore.instance
    private var categoryText = ""

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
                getCategoryFromSpinner(parent.getItemAtPosition(pos).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        createButton.setOnClickListener {
            if (Authentication.instance.getCurrentUser() != null) {
                if (!validateTitleText(title)) {
                    title.error = getString(R.string.titleTextInvalid)
                } else if (!validateContentText(content)) {
                    content.error = getString(R.string.contentTextInvalid)
                } else {
                    println(categoryText)
                    val threadTitle = title.text.toString()
                    val threadContent = content.text.toString()
                    val threadCategory = categoryText

                    addThreadToDb(threadTitle, threadContent, threadCategory)

                    val loadingDialog = LoadingDialog(this)
                    loadingDialog.startLoadingDialog()

                    Handler(Looper.getMainLooper()).postDelayed({
                        loadingDialog.dismissDialog()
                        val intent = Intent(this.context, ThreadDetailActivity::class.java)
                        intent.putExtra(getString(R.string.id_intent), DatabaseFirestore.threadid)
                        intent.putExtra(getString(R.string.threadTitle_intent), threadTitle)
                        intent.putExtra(getString(R.string.threadContent_intent), threadContent)
                        intent.putExtra(getString(R.string.threadCategory_intent), threadCategory)

                        val args = Bundle()
                        val posts = ArrayList<Comment>()
                        args.putSerializable(getString(R.string.bundlePosts_key), posts)
                        intent.putExtra(getString(R.string.bundleArgs_intent), args)
                        intent.putExtra(
                            getString(R.string.userId_intent),
                            DatabaseFirestore.instance.auth.currentUser!!.uid
                        )
                        startActivity(intent)
                        activity?.onBackPressed()
                    }, 1000)

                }
            } else {
                val intent = Intent(this.context, LogInActivity::class.java)
                intent.putExtra(
                    getString(R.string.errorMessage_intent),
                    getString(R.string.text_you_must_be_logged_in_to_do_this)
                )
            }


        }
    }

    private fun getCategoryFromSpinner(category: String) {
        categoryText = category
    }

    private fun validateTitleText(editText: EditText): Boolean {
        return (editText.text.length in 6..49)
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
                threadTitle,
                threadContent,
                threadCategory,
                DatabaseFirestore.instance.auth.currentUser?.uid
            )
        )
        println(DatabaseFirestore.threadid + "coooooooewo")

    }

}