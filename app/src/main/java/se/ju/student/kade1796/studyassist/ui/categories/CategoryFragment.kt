package se.ju.student.kade1796.studyassist.ui.categories


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import se.ju.student.kade1796.studyassist.*
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import se.ju.student.kade1796.studyassist.DatabaseFirestore
import se.ju.student.kade1796.studyassist.LoadingDialog
import se.ju.student.kade1796.studyassist.R
import se.ju.student.kade1796.studyassist.ThreadsActivity


class CategoryFragment : Fragment(), AdapterView.OnItemClickListener {

    private lateinit var categoryViewModel: CategoryViewModel

    private var arrayList:ArrayList<Category> ?= null
    private var gridView:GridView ?= null
    private var categoryAdapter:CategoryAdapter ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        categoryViewModel =
            ViewModelProvider(this).get(CategoryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_category, container, false)

        gridView = root!!.findViewById(R.id.category_gridView)
        arrayList = ArrayList()
        arrayList = categoryViewModel.tempCategoryList()
        categoryAdapter = CategoryAdapter(requireContext(), arrayList!!)
        gridView!!.adapter = categoryAdapter
        gridView!!.onItemClickListener = this

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
        val categoryCampus          = view.findViewById<ImageButton>(R.id.category_campus)
        val categorySocialLife      = view.findViewById<ImageButton>(R.id.category_social_life)
        val categoryStudyHelp       = view.findViewById<ImageButton>(R.id.category_study_help)
        val categoryMath            = view.findViewById<ImageButton>(R.id.category_math)
        val categoryCourseMaterial  = view.findViewById<ImageButton>(R.id.category_course_material)
        val categoryITHelp          = view.findViewById<ImageButton>(R.id.category_it_help)
        val categoryExchangeStudies = view.findViewById<ImageButton>(R.id.category_exchange_studies)
        val categoryOther           = view.findViewById<ImageButton>(R.id.category_other)

        categoryCampus.setOnClickListener {

            val intent = Intent(this.context, ThreadsActivity::class.java)
            DatabaseFirestore.instance.getAllThreadsInCategory("Campus")
            val loadingDialog = LoadingDialog(this)
            loadingDialog.startLoadingDialog()
            Handler(Looper.getMainLooper()).postDelayed({
                loadingDialog.dismissDialog()
                intent.putExtra("category", "campus")
                startActivity(intent)
            }, 1000)

        }

        categorySocialLife.setOnClickListener {
            val intent = Intent(this.context, ThreadsActivity::class.java)
            intent.putExtra("category", "social life")
            startActivity(intent)
        }

        categoryStudyHelp.setOnClickListener {
            val intent = Intent(this.context, ThreadsActivity::class.java)
            intent.putExtra("category", "study help")
            startActivity(intent)
        }

        categoryMath.setOnClickListener {
            val intent = Intent(this.context, ThreadsActivity::class.java)
            intent.putExtra("category", "math")
            startActivity(intent)
        }

        categoryCourseMaterial.setOnClickListener {

        }

        categoryITHelp.setOnClickListener {

        }

        categoryExchangeStudies.setOnClickListener {

        }

        categoryOther.setOnClickListener {

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //Start ThreadsActivity at clicked item
        Toast.makeText(parent!!.context,"Item $position clicked", Toast.LENGTH_SHORT).show()
        val clickedItem = arrayList!![position]
        val intent = Intent(parent!!.context, ThreadsActivity::class.java)
        intent.putExtra("title", clickedItem.id)
        startActivity(intent)
    }
}

