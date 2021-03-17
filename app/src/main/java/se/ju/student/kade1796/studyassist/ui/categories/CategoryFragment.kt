package se.ju.student.kade1796.studyassist.ui.categories

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import se.ju.student.kade1796.studyassist.*

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

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //Start ThreadsActivity at clicked item
        Toast.makeText(parent!!.context,"Item $position clicked", Toast.LENGTH_SHORT).show()
        val clickedItem = arrayList!![position]
        val intent = Intent(parent!!.context, ThreadsActivity::class.java)
        intent.putExtra("title", clickedItem.id)
        startActivity(intent)
    }
}

