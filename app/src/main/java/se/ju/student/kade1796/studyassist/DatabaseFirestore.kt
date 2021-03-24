package se.ju.student.kade1796.studyassist

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import org.json.JSONArray

class DatabaseFirestore {
    companion object {
        val instance = DatabaseFirestore()
        var threadid = String()
    }

    // Loads in the instance of the database and Firestore authenticator
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()


    //****************************************THREADS FUNC*************************************************
    //Returns a list of threads to local Repository
    fun getAllThreadsInCategory(categoryName: String, adapter: ThreadAdapter) {
        println(categoryName)
        db.collection("categories")
            .document(categoryName)
            .collection("threads")
            .get()
            .addOnSuccessListener { result ->
                var listOfThreads = mutableListOf<Threads>()

                for (document in result) {
                    listOfThreads.add(document.toObject(Threads::class.java))
                }
                adapter.update(listOfThreads)
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }


    fun getUsersThreads(user: FirebaseUser, adapter: ThreadAdapter) {
        adapter.update(Repository.instance.userThreads)
    }


    //Adds a new thread from the Threads-data class, recommended!
    //Give it the data class Threads and a category to be added to
    fun addThread(newThread: Threads) {
        db.collection("categories")
            .document(newThread.category.toString())
            .collection("threads")
            .add(newThread)
            .addOnSuccessListener {
                Log.d("SuccessAddThread", "Thread added: $newThread")
                val id = it.id
                threadid = it.id
                val documentReference = db.collection("categories")
                    .document(newThread.category.toString())
                    .collection("threads")
                    .document(id)
                documentReference.update("id", id)
                Repository.instance.userThreads.add(newThread)
            }
    }

    fun getThreadId(categoryName: String, title: String) {
        val docref = db.collection("categories")
            .document(categoryName.toString())
            .collection("threads")
            .get()

    }


    //On success deletes a the thread
    fun deleteThread(threadToDelete: Threads) {

        db.collection("categories")
            .document(threadToDelete.category.toString())
            .collection("threads")
            .document(threadToDelete.id.toString())
            .delete()
            .addOnSuccessListener { Log.d("SuccessDeletingThread", "Thread successfully deleted!") }
            .addOnFailureListener { e -> Log.w("FailDeletingThread", "Error deleting thread", e) }
    }


    fun updateLikes(thread: Threads, likes: Int) {

        db.collection("categories")
            .document(thread.category.toString())
            .collection("threads")
            .document(thread.id.toString())
            .update("likes", likes)

    }


    //******************************************POSTS FUNC*************************************************
    //TODO IMPLEMENT POSTS FUNCS


    fun addComment(comment: Comment) {
        Log.d("posts", "threadId: ${comment.threadId} cateoryId: ${comment.category}");

        //val categoryId = categoryTitleToId(comment.category.toString())
        db.collection("categories")
            .document(comment.category.toString())
            .collection("threads")
            .document(comment.threadId.toString())
            .update("posts", FieldValue.arrayUnion(comment))
    }

    fun updateCommentLikes(comment: Comment) {
        db.collection("categories")
            .document(comment.category.toString())
            .collection("threads")
            .document(comment.threadId.toString())
        //.update("likes", mapOf("likes" to comment.likes))
    }

    //******************************************LOGIN FUNC ************************************************


}