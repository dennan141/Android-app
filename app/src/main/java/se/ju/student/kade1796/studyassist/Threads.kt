package se.ju.student.kade1796.studyassist

data class Threads(
        val title: String? = "-Empty Title-",
        val content: String? = "-Empty Content-",
        val posts: MutableList<Posts>? = mutableListOf<Posts>(),
        val category: String? = "Other",
        val ownerId: String? = " ",
        val id: String? = " "
)