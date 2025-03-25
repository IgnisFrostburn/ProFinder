package citu.profinderapp.Firebase.User

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class Location(
    val location:String,
    val time:String
)

fun addLocation(location: Location) {
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    if(userId != null) {
        val teacherCollection = db.collection("teachers").document(userId)
        val locationSubCollection = teacherCollection.collection("locations")
        locationSubCollection.add(location).addOnSuccessListener {
            Log.e("Location added", "SUCCESS!")
        }.addOnFailureListener {
            Log.e("Location added", "FAILED!")

        }
    }
}