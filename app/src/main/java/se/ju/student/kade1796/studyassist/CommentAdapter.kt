package se.ju.student.kade1796.studyassist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.comment_item.view.*
import kotlinx.android.synthetic.main.comment_item.view.likeButton
import kotlinx.android.synthetic.main.comment_item.view.likes_textView
import kotlinx.android.synthetic.main.thread_item.view.*

class CommentAdapter(
        var commentList: MutableList<Posts>,
        private val listener: CommentAdapter.OnItemClickListener
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(ThreadDetailActivty: ViewGroup, viewType: Int): CommentViewHolder {
        val itemView = LayoutInflater.from(ThreadDetailActivty.context).inflate(R.layout.comment_item, ThreadDetailActivty, false)
        return CommentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.commentTextView.text = commentList[position].content
        holder.likeButtonImageButton.setOnClickListener{
            listener.addLikes(position)
        }
        holder.likesTextView.text = commentList[position].likes.toString()
    }

    fun addPosts(posts:MutableList<Posts>) {
        this.commentList.addAll(posts);
    }

    override fun getItemCount() = commentList.size

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentTextView: TextView = itemView.comment_textView
        var likeButtonImageButton: ImageButton = itemView.likeButton
        var likesTextView: TextView = itemView.likes_textView

    }

    interface OnItemClickListener{
        fun addLikes(position: Int)
    }
}