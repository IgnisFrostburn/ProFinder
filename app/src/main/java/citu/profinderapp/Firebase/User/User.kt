package citu.profinderapp.Firebase.User

import android.net.Uri
import java.io.Serializable

open class User(
    open val id:String = "",
    open val username:String = "",
    open val password:String = "",
    open val email:String = "",
    open val accountType:String = "",
    open val profileImg:String = ""
): Serializable