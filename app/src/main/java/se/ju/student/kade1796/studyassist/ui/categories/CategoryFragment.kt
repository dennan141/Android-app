package se.ju.student.kade1796.studyassist.ui.categories

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import se.ju.student.kade1796.studyassist.*
import android.widget.TextView
import androidx.lifecycle.Observer
import android.widget.ImageButton
import se.ju.student.kade1796.studyassist.R
import android.widget.*
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import se.ju.student.kade1796.studyassist.*

class CategoryFragment : Fragment(), AdapterView.OnItemClickListener {
    private lateinit var categoryViewModel: CategoryViewModel
    private var arrayList: ArrayList<Categories>? = null
    private var gridView: GridView? = null
    private var categoryAdapter: CategoryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        categoryViewModel =
            ViewModelProvider(this).get(CategoryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_category, container, false)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        gridView = root!!.findViewById(R.id.category_gridView)
        arrayList = ArrayList()
        arrayList = categoryViewModel.getCategoryList()
        categoryAdapter = CategoryAdapter(requireContext(), arrayList!!)
        gridView!!.adapter = categoryAdapter
        gridView!!.onItemClickListener = this

        return root
    }

    override fun onItemClick(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        //Start ThreadsActivity at clicked item
        val clickedItem = arrayList!![position]
        val intent = Intent(parent!!.context, ThreadsActivity::class.java)
        intent.putExtra("categoryTitle", clickedItem.categoryTitle)
        startActivity(intent)
    }


}
