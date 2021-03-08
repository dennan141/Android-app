package se.ju.student.kade1796.studyassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class ThreadsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_threads)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)


        //THIS IS ONLY FOR TESTING AND CAN SAFELY BE REMOVED
        //*****************************************************************************


        //---------------------Thread can now be added using this -----------------------
        //-----------------------DUMMY DATA---------------------------
        //Creates lists for dummy data
        var mutableListOfPosts = mutableListOf<Posts>()
        var mutableListOfThreads = mutableListOf<Threads>()
        //Creates object
        val newPost1 = Posts("CONTENT IN NEW POST 1")
        val newPost2 = Posts("CONTENT IN NEW POST 2")

        mutableListOfPosts.add(newPost1)
        mutableListOfPosts.add(newPost2)
        val newThread = Threads("TestingTitle", "TestingContent", mutableListOfPosts, "Other")
        //mutableListOfThreads.add(newThread)
        val newCategory = Categories("TESTING_TITLE_5")
        //-----------------------DUMMY DATA---------------------------


        DatabaseFirestore.instance.addCategory(newCategory)
        DatabaseFirestore.instance.addThread(newThread)
        val listOfThreads = DatabaseFirestore.instance.getAllThreadsInCategory("Campus")


        //---------------------Thread can now be added using this -----------------------


        //***************************************************************************
        //THIS IS ONLY FOR TESTING AND CAN SAFELY BE REMOVED
    }
}
