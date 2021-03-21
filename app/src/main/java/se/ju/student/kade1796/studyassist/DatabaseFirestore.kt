package se.ju.student.kade1796.studyassist

import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import android.widget.Toast

class DatabaseFirestore {
    companion object {
        val instance = DatabaseFirestore()
        var listthreads = mutableListOf<Threads>()
    }

    // Loads in the instance of the database and Firestore authenticator
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    //************************************PRIVATE FUNCTIONS AND VARIABLES***************************
    private fun categoryTitleToId(categoryTitle: String): String {
        val categoryId = titleToIdMap[categoryTitle]
        return categoryId.toString()
    }

    private val titleToIdMap = mapOf(
        "Campus" to "u6XsDE4PJ32OPmg3xPKl",
        "Other" to "BTxSqt5S6ByK6EQaZMpp",
        "IT" to "NRq7MPQ7135cyEDTvWt8",
        "Math" to "YpPDZBqsWcedKFKOfwTa"
    )

    //************************************PRIVATE FUNCTIONS AND VARIABLES***************************


    //*************************************CATEGORIES FUNCTIONS*************************************************

    //*****************************************************************************************************
    //                                  !!! IMPORTANT !!!
    //     - NOT BE ACCESSED FROM THE APP IN FUTURE, ONLY FOR CREATING CATEGORIES BEFORE LAUNCH -
    //                                  !!! IMPORTANT !!!
    //****************************************************************************************************

    //On Success: Adds the auto-generated id into the field "id" in the newly created category.
    //sub collection "threads" is added when a thread is added for the first time


    fun addCategory(newCategory: Categories) {
        val categoriesRef = db.collection("categories")
            .add(newCategory)
        categoriesRef.addOnSuccessListener {
            Log.d("SuccessTag", "DocumentSnapshot successfully written!")
            val id = it.id
            val documentReference = db.collection("categories").document(id)
            documentReference.update("id", id)
        }
    }
    //********* - ABOVE ^ NOT BE ACCESSED IN FUTURE, ONLY FOR CREATING CATEGORIES BEFORE LAUNCH ^ ABOVE - ******


    //****************************************THREADS FUNC*************************************************
    //Returns a list of threads to local Repository
    fun getAllThreadsInCategory(categoryName: String, adapter: ThreadAdapter) {
        val categoryId = categoryTitleToId(categoryName)

        db.collection("categories")
            .document(categoryId)
            .collection("threads")
            .get()
            .addOnSuccessListener { result ->
                var listOfThreads = mutableListOf<Threads>()

                for (document in result)
                    listOfThreads.add(document.toObject(Threads::class.java));

                adapter.update(listOfThreads);
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception");
            }
    }

    //Adds a new thread from the Threads-data class, recommended!
    //Give it the data class Threads and a category to be added to
    fun addThread(newThread: Threads) {
        val categoryId = categoryTitleToId(newThread.category.toString())
        db.collection("categories")
            .document(categoryId)
            .collection("threads")
            .add(newThread)
            .addOnSuccessListener {
                Log.d("SuccessAddThread", "Thread added: $newThread")
                val id = it.id
                val documentReference = db.collection("categories")
                    .document(categoryId)
                    .collection("threads")
                    .document(id)
                documentReference.update("id", id)
            }
    }

    //Adds a new thread by creating the new thread here, not recommended but does exist if programmer wants to use.
    fun addThread(
        title: String,
        content: String,
        likes: Int,
        listOfPosts: MutableList<Posts>,
        category: String
    ) {
        val newThread = Threads(title, content, category, DatabaseFirestore.instance.auth.uid, likes, listOfPosts)
        addThread(newThread)
    }


    //Gets a thread object from thread id and category.
    fun getThreadById(threadId: String, categoryName: String, callback: (Threads) -> Unit) {
        val categoryId = categoryTitleToId(categoryName)
        val docRef = db.collection("categories")
            .document(categoryId)
            .collection("threads")
            .document(threadId)
            .get()
        docRef.addOnSuccessListener { result ->
            Log.d("SuccessTag", "Thread is: ${result.toObject(Threads::class.java)}")
            result.toObject(Threads::class.java)?.let { callback(it) }
        }
        docRef.addOnFailureListener { e ->
            Log.e("FailTag", "Couldn't find a thread by that id", e)
        }
    }

    //On success deletes a the thread
    fun deleteThreadById(threadId: String, categoryName: String) {
        val categoryId = categoryTitleToId(categoryName)
        db.collection("categories")
            .document(categoryId)
            .collection("threads")
            .document(threadId)
            .delete()
            .addOnSuccessListener { Log.d("SuccessDeletingThread", "Thread successfully deleted!") }
            .addOnFailureListener { e -> Log.w("FailDeletingThread", "Error deleting thread", e) }
    }

    //On success deletes a the thread
    fun deleteThread(threadToDelete: Threads) {
        val categoryId = categoryTitleToId(threadToDelete.category.toString())

        db.collection("categories")
            .document(categoryId)
            .collection("threads")
            .document(threadToDelete.id.toString())
            .delete()
            .addOnSuccessListener { Log.d("SuccessDeletingThread", "Thread successfully deleted!") }
            .addOnFailureListener { e -> Log.w("FailDeletingThread", "Error deleting thread", e) }
    }

    //INPUT: threadTitle searching for title, categoryName as String
    //ON SUCCESS: creates a callback containing a list of att threads containing that title
    //On most cases, it will probably only find one, but is list in case more exists
    fun getThreadsByTitle(
        threadTitle: String,
        categoryName: String,
        callback: (MutableList<Threads>) -> Unit
    ) {
        val listOfThreads = mutableListOf<Threads>()
        val categoryId = categoryTitleToId(categoryName)

        val docRef = db.collection("categories")
            .document(categoryId)
            .collection("threads")
            .whereEqualTo("title", threadTitle)
            .get()
        docRef.addOnSuccessListener { result ->
            for (threads in result) {
                listOfThreads.add(threads.toObject(Threads::class.java))
            }
            Log.d("SuccessTagThreadsTitle", "Threads are: $listOfThreads")
            callback(listOfThreads)
        }
        docRef.addOnFailureListener { e ->
            Log.e("FailTagThreadsTitle", "Couldn't find a thread by that id", e)
        }

    }

    fun updateLikes(thread: Threads, likes: Int) {
        val categoryId = categoryTitleToId(thread.category.toString())
        db.collection("categories")
            .document(categoryId)
            .collection("threads")
            .document(thread.id.toString())
            .update("likes", likes)
    }

    //******************************************POSTS FUNC*************************************************
    //TODO IMPLEMENT POSTS FUNCS
    /*fun updateCommentLikes(thread: Threads, likes: Int) {
        val categoryId = categoryTitleToId(thread.category.toString())
        db.collection("categories")
            .document(categoryId)
            .collection("threads")
            .document(thread.id.toString())
            .update("posts.likes", likes)
    }*/

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