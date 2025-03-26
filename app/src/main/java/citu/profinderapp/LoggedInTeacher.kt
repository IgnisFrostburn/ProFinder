package citu.profinderapp

import com.example.mobdev.LoggedInAccount

class LoggedInTeacher: LoggedInAccount() {
    companion object {
        var background: String? = null
        var department: String? = null

        fun setTeacherDetails(background: String, department: String) {
            Companion.background = background
            Companion.department = department
        }
    }
}