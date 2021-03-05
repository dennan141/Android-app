package se.ju.student.kade1796.studyassist

class Repository {

    companion object{
        val instance = Repository()
    }

    var listOfThreads = mutableListOf<Threads>()
    var listOfCategories = mutableListOf<Categories>()
}