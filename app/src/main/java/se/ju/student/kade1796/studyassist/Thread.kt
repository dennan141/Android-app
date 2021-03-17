package se.ju.student.kade1796.studyassist

data class Thread(
        var title: String = "",
        var content: String = ""
){
        override fun toString(): String {
                return title
        }
}