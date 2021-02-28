package se.ju.student.kade1796.studyassist

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import android.util.Log

class DatabaseFirestore {


    companion object{
        val instance = DatabaseFirestore()
    }



    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()



    //Adds a new thread by creating the new thread here, not recommended but does exist if programmer wants to use.
    fun addThread(title: String, content: String, listOfPosts: MutableList<Posts>, category: String){
        val newThread = Threads(title, content, listOfPosts, category)
        db.collection(category)
                .add(newThread)
                .addOnSuccessListener { Log.d("SuccessTag", "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.e("FailTag", "Error writing document", e) }
    }

    //Adds a new thread from the Threads-data class, recommended!
    fun addThread(newThread: Threads){
        db.collection(newThread.category.toString())
                .add(newThread)
                .addOnSuccessListener { Log.d("SuccessTag", "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.e("FailTag", "Error writing document", e) }
    }




    fun getThreadId(thread: Threads){


    }


}