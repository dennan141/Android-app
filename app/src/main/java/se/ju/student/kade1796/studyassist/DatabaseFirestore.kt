package se.ju.student.kade1796.studyassist

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log

class DatabaseFirestore {


    companion object {
        val instance = DatabaseFirestore()
    }

    // Loads in the instance of the database and Firestore authenticator
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()


    //Gets a list of all categories and loads them into Repository as well as return a list

    //TODO IMPLEMENT THIS!!!
    fun getAllCategories() {
        val categoryRef = db.collection("categories")
                .whereEqualTo("title", "Campus")
                .get()

                .addOnSuccessListener { result ->
                    for (category in result) {
                        Repository.instance.listOfCategories.add(category.toObject(Categories::class.java))
                    }
                }
    }

    //Adds a new thread by creating the new thread here, not recommended but does exist if programmer wants to use.
    fun addThread(title: String, content: String, listOfPosts: MutableList<Posts>, category: String) {
        //Create the new Thread
        val newThread = Threads(title, content, listOfPosts, category)
        //Try to add the new thread to the category
        val categoryRef = db.collection("categories")
        val docRef = categoryRef.document(newThread.category.toString())
                .collection("threads")
                .add(newThread)

        //On success
        docRef.addOnSuccessListener {
            Log.d("SuccessTag", "DocumentSnapshot successfully written!")
            //Gets the documents auto-generated id
            val id = it.id
            //Adds the auto-generated id into the field "id" in the object
            val documentReference = db.collection(newThread.category.toString()).document(id)
            documentReference.update("id", id)
        }
    }


    //Adds a new thread from the Threads-data class, recommended!
    fun addThread(newThread: Threads) {
        //Goes into the first collection "categories"
        val categoryRef = db.collection("categories")
        //Chooses the category sent into the func
        val docRef = categoryRef.document(newThread.category.toString())
                .collection("threads")
                .add(newThread)

        //On success
        docRef.addOnSuccessListener {
            Log.d("SuccessTag", "DocumentSnapshot successfully written!")
            //Gets the documents auto-generated id
            val id = it.id
            //Adds the auto-generated id into the field "id" in the object
            val documentReference = db.collection(newThread.category.toString()).document(id)
            documentReference.update("id", id)
        }
    }



    //------------------NOT BE ACCESSED IN FUTURE, ONLY FOR CREATION CATEGORIES BEFORE LAUNCH-------------------------
    fun addCategory(newCategory: Categories) {

        //TESTING DUMMY DATA FOR CREATION OF CATEGORY
        val listOfThreads = mutableListOf<Threads>()
        val newCategory = Categories(listOfThreads, "TESTING_DENNIS")


        val docRef = db.collection("categories")
                .add(newCategory)
        docRef.addOnSuccessListener {
            Log.d("SuccessTag", "DocumentSnapshot successfully written!")
            //Gets the documents auto-generated id
            val id = it.id
            //Adds the auto-generated id into the field "id" in the object
            val documentReference = db.collection(newCategory.categoryTitle.toString()).document(id)
            documentReference.update("id", id)
        }
    }

    fun addCategory() {

        //TESTING DUMMY DATA FOR CREATION OF CATEGORY
        val listOfThreads = mutableListOf<Threads>()
        val newCategory = Categories(listOfThreads, "TESTING_DENNIS")


        val docRef = db.collection("categories")
                .add(newCategory)
        docRef.addOnSuccessListener {
            Log.d("SuccessTag", "DocumentSnapshot successfully written!")
            //Gets the documents auto-generated id
            val id = it.id
            //Adds the auto-generated id into the field "id" in the object
            val documentReference = db.collection(newCategory.categoryTitle.toString()).document(id)
            documentReference.update("id", id)
        }
    }
    //------------------NOT BE ACCESSED IN FUTURE, ONLY FOR CREATION CATEGORIES BEFORE LAUNCH-------------------------












    //Returns all threads in a mutableList of Threads objects
    fun getAllThreadsInCategory(category: String) {
        db.collection(category)
                .get()
                //On success loops through threads and adds them to threadsRepository : listOfThreads
                .addOnSuccessListener { result ->
                    for (thread in result) {
                        Repository.instance.listOfThreads.add(thread.toObject(Threads::class.java))
                    }
                }
    }


    //Gets a thread object from thread id and category.
    fun getThreadById(threadId: String, categoryName: String) {
        val docRef = db.collection(categoryName)
                .whereEqualTo("id", threadId)
                .get()
                .addOnSuccessListener { result ->
                    val returnedThread = result.toObjects(Threads::class.java)
                    Log.d("SuccessTag", "Thread successfully returned, id is: ${returnedThread}")
                }
        docRef.addOnFailureListener { e ->
            Log.e("FailTag", "Couldn't find a thread by that id", e)
        }
    }


}