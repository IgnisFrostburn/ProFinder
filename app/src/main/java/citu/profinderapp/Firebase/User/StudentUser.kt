package citu.profinderapp.Firebase.User

data class StudentUser(
    override val id: String = "",
    override val username: String = "",
    override val password: String = "",
    override val email: String = "",
    override val accountType: String = "student",
    val course: String = "",
    val phoneNumber: String = "",
    val personalInfo: String = ""
) : User(id, username, password, email, accountType)