package se.ju.student.kade1796.studyassist

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class LogInActivity : AppCompatActivity() {
    //access a firebase auth from your Activity
    var auth = FirebaseAuth.getInstance()

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
        val newPost1 = Posts("CONTENT IN NEW POST 1")
        val newPost2 = Posts("CONTENT IN NEW POST 2")
        var mutableListOfPosts = mutableListOf<Posts>()
        val category = "Campus"
        val title = "test_title in newThread"
        val likes = 23
        val content = "test_content in newThread"


        DatabaseFirestore.instance.addThread(title, content, likes, mutableListOfPosts ,category )
        DatabaseFirestore.instance.getAllThreadsInCategory(category)



        //---------------------Thread can now be added using this -----------------------


        
        //***************************************************************************
        //THIS IS ONLY FOR TESTING AND CAN SAFELY BE REMOVED
            
    }


    override fun onStart() {
        super.onStart()





    }
}