package citu.profinderapp.Accounts

import citu.profinderapp.Firebase.Location.Location

class LoggedInTeacher: LoggedInAccount() {
    companion object {
        var background: String? = null
        var department: String? = null
        var latestLocation: Location? = null

        fun setTeacherDetails(background: String, department: String) {
            Companion.background = background
            Companion.department = department
        }

        fun setLatestLoc(latestLocation: Location) {
            Companion.latestLocation = latestLocation
        }
    }
}