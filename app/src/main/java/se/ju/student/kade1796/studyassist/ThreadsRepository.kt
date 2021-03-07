package se.ju.student.kade1796.studyassist

class ThreadsRepository {

    companion object{
        val instance = ThreadsRepository()
    }

    var listOfThreads = mutableListOf<Threads>()
}