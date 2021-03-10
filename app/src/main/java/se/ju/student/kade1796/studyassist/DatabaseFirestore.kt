package se.ju.student.kade1796.studyassist

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FieldValue.arrayUnion
import kotlinx.coroutines.tasks.await

class DatabaseFirestore {


    companion object {
        val instance = DatabaseFirestore()
    }


    // Loads in the instance of the database and Firestore authenticator
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    //************************************PRIVATE FUNCTIONS AND VARIABLES***************************
    private fun categoryTitleToId(cateogryTitle: String): String {
        val categoryId = titleToIdMap.get(cateogryTitle)
        return categoryId.toString()
    }

    private val titleToIdMap = mapOf(
        "Campus" to "jaXCitQgNMvZjMxeOUXD",
        "Other" to "bla bla bla"
    )
    //************************************PRIVATE FUNCTIONS AND VARIABLES***************************



    //*************************************CATEGORIES FUNCTIONS*************************************************

    //*****************************************************************************************************
    //                                      IMPORTANT!!!
    //     - NOT BE ACCESSED FROM THE APP IN FUTURE, ONLY FOR CREATING CATEGORIES BEFORE LAUNCH -
    //     -                    APPLIES TO BOTH addCategory FUNCTIONS!!!                        -
    //****************************************************************************************************

    //On Success: Adds the auto-generated id into the field "id" in the newly created category.
    //sub collection "threads" is added when a thread is added for the first time
    //TODO (FIX UP)
    fun addCategory(newCategory: Categories) {
        val categoriesRef = db.collection("categories")
            .add(newCategory)
        categoriesRef.addOnSuccessListener {
            Log.d("SuccessTag", "DocumentSnapshot successfully written!")
            val id = it.id
            val documentReference = db.collection("categories").document(id)
            documentReference.update("id", id)
        }
    }

    //On Success: Adds the auto-generated id into the field "id" for the newly created category.
    //TODO (FIX UP)
    fun addCategory() {
        //TESTING DUMMY DATA FOR CREATION OF CATEGORY
        val emptyListOfThreads = mutableListOf<Threads>()
        val newCategory = Categories("TEST_TITLE")

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
    fun getAllCategories(): MutableList<Categories> {
        var listOfResult = mutableListOf<Categories>()
        val categoryRef = db.collection("categories")
            .get()
            .addOnSuccessListener { result ->
                listOfResult = result.toObjects(Categories::class.java)
                Log.d("getAllCategories", "List of all categories: $listOfResult")

            }
        return listOfResult
    }


    //****************************************THREADS FUNC*************************************************


    //Returns all threads in a mutableList of Threads objects into Repository
    //TODO
    fun getAllThreadsInCategory(category: String, callback: (MutableList<Threads>) -> Unit) {
        var listOfThreads = mutableListOf<Threads>()
        val categoryId = categoryTitleToId(category)
        val docRef = db.collection("categories")
            .document(categoryId)
            .collection("threads")
            .get()

            //On Success: loops through and adds threads into list
            .addOnSuccessListener { result ->
                for (thread in result) {
                    listOfThreads.add(thread.toObject(Threads::class.java))
                }
                callback(listOfThreads)
            }
    }




    //Adds a new thread from the Threads-data class, recommended!
    //Give it the data class Threads and a category to be added to
    fun addThread(newThread: Threads) {
        db.collection("categories")
            .document(newThread.category.toString())
            .collection("threads")
            .add(newThread)
            .addOnSuccessListener {
                Log.d("SuccessAddThread", "Thread added: $newThread")
            }
    }

    //Adds a new thread by creating the new thread here, not recommended but does exist if programmer wants to use.
    fun addThread(
        title: String,
        content: String,
        listOfPosts: MutableList<Posts>,
        category: String
    ) {
        //Create the new Thread
        val newThread = Threads(title, content, listOfPosts, category)
        //Try to add the new thread to the category
        addThread(newThread)
    }


    //Gets a thread object from thread id and category.
    //TODO
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
    //TODO
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


    //******************************************POSTS FUNC*************************************************
    //TODO IMPLEMENT POSTS FUNCS


    //******************************************DUMMY DATA FUNC*************************************************


    fun dummyData() {
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
        val newThread = Threads("Dennis title", "Dennis Content", mutableListOfPosts, "Campus")
        mutableListOfThreads.add(newThread)
        val newCategoryCampus = Categories("Campus")
        val newCategoryOther = Categories("Other")

        //-----------------------DUMMY DATA---------------------------


        //addCategory(newCategoryCampus)
        //addCategory(newCategoryOther)

        //addThread(newThread)
        //val listOfThreads = getAllThreadsInCategory("Campus")
        //Log.d("listOfThreads", "This should be a list of threads in one category: $listOfThreads")


        //---------------------Thread can now be added using this -----------------------


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