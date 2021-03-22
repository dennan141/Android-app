package se.ju.student.kade1796.studyassist

data class Threads(
    val title: String? = "-Empty Title-",
    val content: String? = "-Empty Content-",
    val category: String? = "Other",
    val userId: String? = DatabaseFirestore.instance.auth.currentUser?.uid,
    var likes: Int? = 0,
    val posts: MutableList<Comment>? = mutableListOf<Comment>(),
    val id: String? = " "
)