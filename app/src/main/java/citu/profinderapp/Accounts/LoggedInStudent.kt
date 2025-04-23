package citu.profinderapp.Accounts

class LoggedInStudent: LoggedInAccount() {
    companion object {
        var course: String? = null
        var phoneNumber: String? = null
        var customInfo: String? = null

        fun setStudentDetails(course: String, phoneNumber: String, personalInfo: String) {
            Companion.course = course
            Companion.phoneNumber = phoneNumber
            customInfo = personalInfo
        }
    }
}