package se.ju.student.kade1796.studyassist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.thread_item.view.*

class ThreadAdapter(
    var threadList: List<Thread>,
    val listener: OnItemClickListener
) : RecyclerView.Adapter<ThreadAdapter.ThreadViewHolder>() {

    override fun onCreateViewHolder(ThreadsActivty: ViewGroup, viewType: Int): ThreadViewHolder {
        val itemView = LayoutInflater.from(ThreadsActivty.context).inflate(R.layout.thread_item, ThreadsActivty, false)
        return ThreadViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ThreadViewHolder, position: Int) {
        holder.titleTextView.text = threadList[position].title
        holder.descriptionTextView.text = threadList[position].content
        holder.likeButtonImageButton.setOnClickListener{
            listener.add(position)
        }
        holder.likesTextView.text = threadList[position].likes.toString()
    }

    override fun getItemCount() = threadList.size

    inner class ThreadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        val titleTextView: TextView = itemView.title_textView
        val descriptionTextView: TextView = itemView.description_textView
        var likeButtonImageButton: ImageButton = itemView.likeButton
        var likesTextView: TextView = itemView.likes_textView


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
        fun add(position: Int)
    }

}
