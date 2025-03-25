package citu.profinderapp

import com.example.mobdev.LoggedInAccount

class LoggedInStudent: LoggedInAccount() {
    companion object {
        var course: String? = null
        var phoneNumber: String? = null
        var personalInfo: String? = null

        fun setStudentDetails(course: String, phoneNumber: String, personalInfo: String) {
            Companion.course = course
            Companion.phoneNumber = phoneNumber
            Companion.personalInfo = personalInfo
        }
    }
}