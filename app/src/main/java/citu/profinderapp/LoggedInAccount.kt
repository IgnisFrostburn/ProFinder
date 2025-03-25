package com.example.mobdev

abstract class LoggedInAccount constructor(){
    companion object {
        var username: String? = null
        var email: String? = null
        var password: String? = null
        var isStudent: Boolean = false

        fun setValues(username: String, email: String, password: String, isStudent: Boolean) {
            this.username = username
            this.email = email
            this.password = password
            this.isStudent = isStudent
        }
    }
}