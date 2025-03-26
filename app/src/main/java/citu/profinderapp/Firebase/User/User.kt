package citu.profinderapp.Firebase.User

import citu.profinderapp.R

open class User(
    open val id:String = "",
    open val username:String = "",
    open val password:String = "",
    open val email:String = "",
    open val accountType:String = "",
//    open var profilePic: Int = R.drawable.profile_placeholder_icon
)