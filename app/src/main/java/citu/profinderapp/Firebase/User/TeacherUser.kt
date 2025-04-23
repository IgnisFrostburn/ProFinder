package citu.profinderapp.Firebase.User

import android.net.Uri

data class TeacherUser(
    override val id: String = "",
    override val username: String = "",
    override val password: String = "",
    override val email: String = "",
    override val accountType: String = "teacher",
    override val profileImg: String = "",
    val background: String = "",
    val courses: String = ""
) : User(id, username, password, email, accountType, profileImg)