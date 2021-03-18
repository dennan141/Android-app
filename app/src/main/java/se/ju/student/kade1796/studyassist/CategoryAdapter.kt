package se.ju.student.kade1796.studyassist

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CategoryAdapter(
    private val context: Context,
    private val arrayList: ArrayList<Categories>,
    ) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        Log.d("adapter", "In adapter")

        var view:View = View.inflate(context, R.layout.categories_item, null)
        var imageView:ImageView = view.findViewById(R.id.category_imageView)
        var title:TextView = view.findViewById(R.id.category_title_textView)

        var listItem:Categories = arrayList[position]

        imageView.setImageResource(listItem.icons!!)
        title.text = listItem.categoryTitle

        return view
    }

    override fun getItem(position: Int): Any {
        return arrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return arrayList.size
    }
}