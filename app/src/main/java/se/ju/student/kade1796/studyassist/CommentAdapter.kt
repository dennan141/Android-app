package se.ju.student.kade1796.studyassist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.comment_item.view.*
import kotlinx.android.synthetic.main.comment_item.view.likeButton
import kotlinx.android.synthetic.main.comment_item.view.likes_textView

class CommentAdapter(
    private var commentList: MutableList<Comment>,
    private val listener: OnItemClickListener
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

        println("onBindViewHolder position $position ${commentList[position]}")
        holder.commentTextView.text = commentList[position].content.toString()
        holder.likeButtonImageButton.setOnClickListener {

            listener.addLikes(position)
        }
        holder.likesTextView.text = commentList[position].likes.toString()
    }


    fun addPosts(listOfComments: MutableList<Comment>) {
        println("addPosts")
        this.commentList.addAll(listOfComments)
        this.notifyDataSetChanged()

    }

    override fun getItemCount() = commentList.size

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentTextView: TextView = itemView.comment_textView
        var likeButtonImageButton: ImageButton = itemView.likeButton
        var likesTextView: TextView = itemView.likes_textView

    }

    interface OnItemClickListener {
        fun addLikes(position: Int)
    }
}