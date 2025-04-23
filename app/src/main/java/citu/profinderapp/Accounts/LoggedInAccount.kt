package citu.profinderapp.Accounts

abstract class LoggedInAccount {
    companion object {
        var id:String = ""
        var username: String? = null
        var email: String? = null
        var password: String? = null
        var isStudent: Boolean = false
        var profileImg: String = ""

        fun setValues(username: String, email: String, password: String, isStudent: Boolean, profileImg:String) {
            Companion.username = username
            Companion.email = email
            Companion.password = password
            Companion.isStudent = isStudent
            Companion.profileImg = profileImg
        }

        fun setID(id:String) {
            Companion.id = id}
    }
}