package se.ju.student.kade1796.studyassist

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import org.json.JSONArray

class DatabaseFirestore {
    companion object {
        val instance = DatabaseFirestore()
    }

    // Loads in the instance of the database and Firestore authenticator
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()



    //************************************PRIVATE FUNCTIONS AND VARIABLES***************************


    //*************************************CATEGORIES FUNCTIONS*************************************************

    //*****************************************************************************************************
    //                                  !!! IMPORTANT !!!
    //     - NOT BE ACCESSED FROM THE APP IN FUTURE, ONLY FOR CREATING CATEGORIES BEFORE LAUNCH -
    //                                  !!! IMPORTANT !!!
    //****************************************************************************************************




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

                for (document in result)
                    listOfThreads.add(document.toObject(Threads::class.java));

                adapter.update(listOfThreads);
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }


    //Adds a new thread from the Threads-data class, recommended!
    //Give it the data class Threads and a category to be added to
    fun addThread(newThread: Threads) {
        //val categoryId = categoryTitleToId(newThread.category.toString())
       Log.d("weird",newThread.category.toString())
        db.collection("categories")
            .document(newThread.category.toString())
            .collection("threads")
            .add(newThread)
            .addOnSuccessListener {
                Log.d("SuccessAddThread", "Thread added: $newThread")
                val id = it.id
                val documentReference = db.collection("categories")
                    .document(newThread.category.toString())
                    .collection("threads")
                    .document(id)
                documentReference.update("id", id)
            }
    }


    //On success deletes a the thread
    fun deleteThread(threadToDelete: Threads) {
        //val categoryId = categoryTitleToId(threadToDelete.category.toString())

        db.collection("categories")
            .document(threadToDelete.category.toString())
            .collection("threads")
            .document(threadToDelete.id.toString())
            .delete()
            .addOnSuccessListener { Log.d("SuccessDeletingThread", "Thread successfully deleted!") }
            .addOnFailureListener { e -> Log.w("FailDeletingThread", "Error deleting thread", e) }
    }



    fun updateLikes(thread: Threads, likes: Int) {
        //val categoryId = categoryTitleToId(thread.category.toString())
        db.collection("categories")
            .document(thread.toString())
            .collection("threads")
            .document(thread.id.toString())
            .update("likes", likes)

    }

    //******************************************POSTS FUNC*************************************************
    //TODO IMPLEMENT POSTS FUNCS

    fun addComment(comment: Comment){
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
    fun loginWithEmail(email: String, password: String, activity: LogInActivity) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener  { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("successTag", "signInWithEmail:success")
                    activity.updateUI(auth.currentUser)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("FailTag", "signInWithEmail:failure", task.exception)
                    activity.updateUI(null)
                }
            }
    }

}