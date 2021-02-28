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


        //THIS IS ONLY FOR TESTING AND CAN SAFELY BE REMOVED
        //*****************************************************************************

        //---------------------User can now be added using this -----------------------
        auth.createUserWithEmailAndPassword("kade1796@student.ju.se","12345678")
                .addOnSuccessListener { Log.d("SuccessTag", "Users successfully added!") }
                .addOnFailureListener { e -> Log.e("FailTag", "Error writing user", e) }
        //---------------------User can now be added using this -----------------------


        //---------------------Thread can now be added using this -----------------------
        val newThread = Threads("Dennis Testing the title", "Dennis testing hardcoded content")

        database.collection("testing")
            .add(newThread)
            .addOnSuccessListener { Log.d("SuccessTag", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.e("FailTag", "Error writing document", e) }
        //---------------------Thread can now be added using this -----------------------


        
        //***************************************************************************
        //THIS IS ONLY FOR TESTING AND CAN SAFELY BE REMOVED
            
    }


    override fun onStart() {
        super.onStart()





    }
}