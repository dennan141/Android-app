package se.ju.student.kade1796.studyassist

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log

class DatabaseFirestore {


    companion object{
        val instance = DatabaseFirestore()
    }


    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()



    fun getAllCategories(){
        val docRef = db.collection("Categories")
                .get()
                .addOnSuccessListener { result ->

                    for(category in result){
                        Repository.instance.listOfThreads.add(category.toObject(Threads::class.java))
                    }


                }
    }

    //Adds a new thread by creating the new thread here, not recommended but does exist if programmer wants to use.
    fun addThread(title: String, content: String, listOfPosts: MutableList<Posts>, category: String){
        //Create the new Thread
        val newThread = Threads(title, content, listOfPosts, category)
        //Try to add the new thread to the category
        val docRef = db.collection(newThread.category.toString())
                .add(newThread)
        //On success
        docRef.addOnSuccessListener {
            Log.d("SuccessTag", "DocumentSnapshot successfully written!")
            //Gets the documents auto-generated id
            val id = it. id
            //Adds the auto-generated id into the field "id" in the object
            val documentReference = db.collection(newThread.category.toString()).document(id)
            documentReference.update("id", id)
        }
    }



    //Adds a new thread from the Threads-data class, recommended!
    fun addThread(newThread: Threads){
        val docRef = db.collection(newThread.category.toString())
                .add(newThread)
        docRef.addOnSuccessListener {
            Log.d("SuccessTag", "DocumentSnapshot successfully written!")
            //Gets the documents auto-generated id
            val id = it.id
            //Adds the auto-generated id into the field "id" in the object
            val documentReference = db.collection(newThread.category.toString()).document(id)
            documentReference.update("id", id)
        }
    }

    //Returns all threads in a mutableList of Threads objects
    fun getAllThreadsInCategory(category: String){
        db.collection(category)
                .get()
                //On success loops through threads and adds them to threadsRepository : listOfThreads
                .addOnSuccessListener { result ->
                for(thread in result){
                    Repository.instance.listOfThreads.add(thread.toObject(Threads::class.java))
                }


                }
    }

    //Gets a thread object from thread id and category.
    fun getThreadById(threadId: String, category: String){
        val docRef = db.collection(category)
                .whereEqualTo("id", threadId)
                .get()
                .addOnSuccessListener { result ->
                    val returnedThread = result.toObjects(Threads::class.java)
                    Log.d("SuccessTag", "Thread successfully returned, id is: ${returnedThread}")
                }
                docRef.addOnFailureListener{ e->
                    Log.e("FailTag", "Couldn't find a thread by that id", e)
                }
    }











}