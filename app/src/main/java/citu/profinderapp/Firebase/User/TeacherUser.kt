package citu.profinderapp.Firebase.User

data class TeacherUser(
    override val id: String = "",
    override val username: String = "",
    override val password: String = "",
    override val email: String = "",
    override val accountType: String = "teacher",
    val background: String = "",
    val department: String = ""
) : User(id, username, password, email, accountType)