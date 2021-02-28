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

    fun addThread(title: String, content: String ){

        val newThread = Threads(title, content)

        Log.d("Adding thread", "Firestore.kt line 24 adding thread: ")
        print(newThread)

        //THIS IS FOR TESTING: THIS ADDS THE THREAD TO : categories/campus/threads TODO: SHOULD NOT BE HARDCODED!!!
        db.collection("categories")
            .document("campus")
            .collection("threads")


    }



}