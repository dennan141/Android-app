package se.ju.student.kade1796.studyassist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.comment_item.view.*

class CommentAdapter(
    private var commentList: MutableList<Comment>
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(
        ThreadDetailActivty: ViewGroup,
        viewType: Int
    ): CommentViewHolder {
        val itemView = LayoutInflater.from(ThreadDetailActivty.context)
            .inflate(R.layout.comment_item, ThreadDetailActivty, false)
        return CommentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.commentTextView.text = commentList[position].content.toString()
    }


    fun addPosts(listOfComments: MutableList<Comment>) {
        this.commentList.addAll(listOfComments)
        this.notifyDataSetChanged()
    }

    override fun getItemCount() = commentList.size

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentTextView: TextView = itemView.comment_textView
    }

}