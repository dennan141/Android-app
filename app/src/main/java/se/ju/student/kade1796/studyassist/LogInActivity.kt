package se.ju.student.kade1796.studyassist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase



class LogInActivity : AppCompatActivity() {
    var auth = FirebaseAuth.getInstance()
    // Access a Cloud Firestore instance from your Activity
    var database = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //THIS IS ONLY FOR TESTING AND CAN SAFELY BE REMOVED / DENNIS
        //*****************************************************************************
        auth.createUserWithEmailAndPassword("kade1796@student.ju.se","123")


        val newThread = Threads("testTitle", "TestContent")

        database.collection("categories")
            .add(newThread)
            .addOnSuccessListener { Log.d("SuccessTag", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.e("FailTag", "Error writing document", e) }
        //***************************************************************************
        //THIS IS ONLY FOR TESTING AND CAN SAFELY BE REMOVED / DENNIS
            
    }


    override fun onStart() {
        super.onStart()





    }
}