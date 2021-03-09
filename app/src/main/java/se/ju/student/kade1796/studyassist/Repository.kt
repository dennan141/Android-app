package se.ju.student.kade1796.studyassist

class Repository {

    companion object{
        val instance = Repository()
    }


    //Use this to access data from the database by loading them into proper variables

    //This is a list of thread, uses: All threads in a category. All threads that a user has created
    var listOfThreads = mutableListOf<Threads>()

    //This is a list of categories, used in start to display all categories
    var listOfCategories = mutableListOf<Categories>()
}