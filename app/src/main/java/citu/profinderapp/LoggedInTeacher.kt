package citu.profinderapp

import com.example.mobdev.LoggedInAccount

class LoggedInTeacher: LoggedInAccount() {
    companion object {
        var background: String? = null
        var department: String? = null

        fun setTeacherDetails(background: String, course: String) {
            Companion.background = background
            Companion.department = course
        }
    }
}