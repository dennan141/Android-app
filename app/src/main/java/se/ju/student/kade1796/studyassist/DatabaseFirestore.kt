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

    fun addCategory(newCategory: Category) {
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
    fun getAllCategories(): MutableList<Category> {
        var listOfResult = mutableListOf<Category>()
        db.collection("categories")
            .get()
            .addOnSuccessListener { result ->
                listOfResult = result.toObjects(Category::class.java)
                Log.d("getAllCategories", "List of all categories: $listOfResult")
            }
        return listOfResult
    }


    //****************************************THREADS FUNC*************************************************


    //callback of all threads in a mutableList of Threads objects
    fun getAllThreadsInCategory(categoryName: String) {
        var listOfThreads = mutableListOf<Threads>()
        val categoryId = categoryTitleToId(categoryName)
        db.collection("categories")

            .document("u6XsDE4PJ32OPmg3xPKl")
            .collection("threads")
            .get()

            //On Success: loops through and adds threads into list
            .addOnSuccessListener { result ->
                for (thread in result) {
                    listOfThreads.add(thread.toObject(Threads::class.java))
                }
                load(listOfThreads)
            }
    }

    private fun load(list: MutableList<Threads>){
        listthreads = list
        println("load")
        println(listthreads)
    }



    //Adds a new thread from the Threads-data class, recommended!
    //Give it the data class Threads and a category to be added to
    fun addThread(newThread: Threads){
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
        val newThread = Threads(title, content, likes, listOfPosts, category)
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
            for (threads in result){
                listOfThreads.add(threads.toObject(Threads::class.java))
            }
            Log.d("SuccessTagThreadsTitle", "Threads are: $listOfThreads")
            callback(listOfThreads)
        }
        docRef.addOnFailureListener { e ->
            Log.e("FailTagThreadsTitle", "Couldn't find a thread by that id", e)
        }

    }

    fun updateLikes(thread: Threads, likes: Int){
        val categoryId = categoryTitleToId(thread.category.toString())
        db.collection("categories")
            .document(categoryId)
            .collection("threads")
            .document(thread.id.toString())
            .update("likes", likes)
    }

    //******************************************POSTS FUNC*************************************************
    //TODO IMPLEMENT POSTS FUNCS


    //******************************************DUMMY DATA FUNC*************************************************

    //THIS IS ONLY FOR TESTING AND CAN SAFELY BE REMOVED
    fun dummyData() {
        //-----------------------DUMMY DATA---------------------------
        //Lists
        var mutableListOfPosts = mutableListOf<Posts>()
        var mutableListOfThreads = mutableListOf<Threads>()
        //Posts
        val newPost1 = Posts("CONTENT IN NEW POST 1")
        val newPost2 = Posts("CONTENT IN NEW POST 2")
        mutableListOfPosts.add(newPost1)
        mutableListOfPosts.add(newPost2)
        //Threads
        /*val newThread =
            Threads("Dennis title_testing", "Dennis Content_testing", 3, mutableListOfPosts, "Campus")
        mutableListOfThreads.add(newThread)*/
        //Categories
        //val newCategory = Categories("IT")

        //-----------------------DUMMY DATA---------------------------

        //*********************Adding data******************************


        //addCategory(newCategory)
        //addThread(newThread)


        //*********************Adding data******************************

        //***************************************************************************
        //THIS IS ONLY FOR TESTING AND CAN SAFELY BE REMOVED
    }


/*

    //Goes into the first collection "categories"
    val categoryRef = db.collection("categories")
            .whereEqualTo("categoryTitle", newThread.category.toString())
            .get()



    //On success (Should only have one result and thus FOR-loop is okay in this case.)
    categoryRef.addOnSuccessListener { documents ->
        for (document in documents) {
            Log.d("OnSuccessAddThread", "${document.id} => ${document.data}")
            val documentReference = document.id

            val updates = hashMapOf<String, Any>(
                    "listOfThreadsInCategory" to arrayUnion(newThread),

                    )

            db.document(documentReference)
                    .update("listOfThreadsInCategory", arrayUnion(newThread)).addOnCompleteListener {

                    }




        }

        //Gets the documents auto-generated id
        //val id = documents.id
        //Adds the auto-generated id into the field "id" in the object
        //val documentReference = db.collection(newThread.category.toString()).document(id)
        //documentReference.update("id", id)
    }
    .addOnFailureListener { exception ->
        Log.w("addThreadHasFailed", "Error getting documents: ", exception)
    }

 */
}