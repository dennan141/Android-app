package se.ju.student.kade1796.studyassist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView

class SearchAdapter(
    private val ctx: Context,
    private var resources: Int,
    private var threadList: ArrayList<Threads>
) : ArrayAdapter<Threads>(ctx, resources, threadList) {
    val originalThreadList = threadList

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(ctx)
        val view: View = layoutInflater.inflate(resources, null)

        val searchTextView: TextView = view.findViewById(R.id.search_item_textView)
        searchTextView.text = threadList[position].title

        return view
    }

    override fun getCount(): Int {
        return threadList.size
    }

    override fun getItem(position: Int): Threads? {
        return threadList[position]
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                threadList = results!!.values as ArrayList<Threads>
                if (results.count > 0) {
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }

            override fun performFiltering(constraint: CharSequence?): FilterResults {

                var filterResults = FilterResults()

                if (constraint == null || constraint.isEmpty()) {
                    filterResults.values = originalThreadList
                    filterResults.count = originalThreadList.size
                } else {
                    var filterList = ArrayList<Threads>()
                    for (i in threadList) {
                        var threadTitle: String? = i.title
                        if (threadTitle!!.toLowerCase()
                                .contains(constraint.toString().toLowerCase())
                        ) {
                            filterList.add(i)
                        }
                    }

                    filterResults.values = filterList
                    filterResults.count = filterList.size
                }

                return filterResults
            }
        }
    }
}