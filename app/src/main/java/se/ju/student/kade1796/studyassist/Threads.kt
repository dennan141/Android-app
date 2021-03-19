package se.ju.student.kade1796.studyassist

data class Threads(
        val title: String? = "-Empty Title-",
        val content: String? = "-Empty Content-",
        var likes: Int = 0,
        val posts: MutableList<Posts>? = mutableListOf<Posts>(),
        val category: String? = "Other",
        val id: String? = " "
)