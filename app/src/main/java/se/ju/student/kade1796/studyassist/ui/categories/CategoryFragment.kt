

// *********************** !!! IMPORTANT !!! ****************************
// *																	*
// *					Old CategoryFragmet.kt - file					*
// *		This is what came from the merge but doesnt seem to work?	*
// *																	*
// *********************** !!! IMPORTANT !!! ****************************

package se.ju.student.kade1796.studyassist.ui.categories



import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import se.ju.student.kade1796.studyassist.R
import android.widget.*
import se.ju.student.kade1796.studyassist.*
import se.ju.student.kade1796.studyassist.DatabaseFirestore
import se.ju.student.kade1796.studyassist.LoadingDialog
import se.ju.student.kade1796.studyassist.ThreadsActivity


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

        gridView = root!!.findViewById(R.id.category_gridView)
        arrayList = ArrayList()
        arrayList = categoryViewModel.getCategoryList()
        categoryAdapter = CategoryAdapter(requireContext(), arrayList!!)
        gridView!!.adapter = categoryAdapter
        gridView!!.onItemClickListener = this

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onItemClick(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        //Start ThreadsActivity at clicked item
        Toast.makeText(parent!!.context, "Item $position clicked", Toast.LENGTH_SHORT)
            .show()
        val clickedItem = arrayList!![position]
        val intent = Intent(parent!!.context, ThreadsActivity::class.java)
        intent.putExtra("title", clickedItem.categoryTitle)
        startActivity(intent)
    }
}
