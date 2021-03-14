package se.ju.student.kade1796.studyassist

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log

class DatabaseFirestore {


    companion object {
        val instance = DatabaseFirestore()
        var listthreads = mutableListOf<Threads>()
    }

    // Loads in the instance of the database and Firestore authenticator
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()


    //*************************************CATEGORIES FUNCTIONS*************************************************

    //*****************************************************************************************************
    //                                      IMPORTANT!!!
    //     - NOT BE ACCESSED FROM THE APP IN FUTURE, ONLY FOR CREATING CATEGORIES BEFORE LAUNCH -
    //     -                    APPLIES TO BOTH addCategory FUNCTIONS!!!                        -
    //****************************************************************************************************

    //On Success: Adds the auto-generated id into the field "id" in the newly created category.
    fun addCategory(newCategory: Categories) {
        val docRef = db.collection("categories")
                .add(newCategory)
        docRef.addOnSuccessListener {
            Log.d("SuccessTag", "DocumentSnapshot successfully written!")
            //Gets the documents auto-generated id
            val id = it.id
            //Adds the auto-generated id into the field "id" in the object
            val documentReference = db.collection("categories")
                    .document(id)
            documentReference.update("id", id)
        }
    }

    //On Success: Adds the auto-generated id into the field "id" in the newly created category.
    fun addCategory() {
        //TESTING DUMMY DATA FOR CREATION OF CATEGORY
        val emptyListOfThreads = mutableListOf<Threads>()
        val newCategory = Categories("category_title")

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


    //Gets a list of all categories and loads them into Repository as well as return a list
    //TODO IMPLEMENT THIS!!!
    fun getAllCategories() {
        val categoryRef = db.collection("categories")
                .whereEqualTo("title", "Campus")
                .get()
                .addOnSuccessListener { result ->
                    for (category in result) {
                        Log.d("getAllCat", "List of all categories in Firestore:")
                        Repository.instance.listOfCategories.add(category.toObject(Categories::class.java))
                    }
                }
    }


    //****************************************THREADS FUNC*************************************************

    //Returns all threads in a mutableList of Threads objects
    fun getAllThreadsInCategory(category: String){
        var listOfThreads = mutableListOf<Threads>()
        db.collection("categories")
            .document(category)
            .collection("threads")
            .get()
            //On success loops through threads and adds them to threadsRepository : listOfThreads
            .addOnSuccessListener { result ->
                for (thread in result){
                    listOfThreads.add(thread.toObject(Threads::class.java))
                    Log.d("getAllThreads", "List of all threads in category: $listOfThreads")
                }
                listthreads = listOfThreads
                println(listOfThreads)
                println("--------------------------------------------------")
                println(listthreads)
            }
            .addOnFailureListener {exception ->
                Log.w("internalError", "Error getting documents", exception)
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

    //Gets a thread object from thread id and category.
    fun getThreadsByTitle(threadTitle: String, categoryName: String) {
        val docRef = db.collection(categoryName)
                .whereEqualTo("title", threadTitle)
                .get()
                .addOnSuccessListener { result ->
                    val returnedThreads = result.toObjects(Threads::class.java)
                    Log.d("SuccessTag", "Thread successfully returned, id is: ${returnedThreads}")
                }
        docRef.addOnFailureListener { e ->
            Log.e("FailTag", "Couldn't find a thread by that id", e)
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
    //Give it the data class Threads and a category to be added to
    fun addThread(newThread: Threads) {
        //Goes into the first collection "categories"
        val docRef = db.collection("categories")
                .whereEqualTo("categoryTitle", newThread.category.toString())
                .get()


        //On success
        docRef.addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d("OnSuccessAddThread", "${document.id} => ${document.data}")

            }

            //Gets the documents auto-generated id
            //val id = it.id
            //Adds the auto-generated id into the field "id" in the object
            //val documentReference = db.collection(newThread.category.toString()).document(id)
            //documentReference.update("id", id)
        }
                .addOnFailureListener { exception ->
                    Log.w("addThreadHasFailed", "Error getting documents: ", exception)
                }
    }

    //******************************************POSTS FUNC*************************************************
    //TODO IMPLEMENT POSTS FUNCS






}