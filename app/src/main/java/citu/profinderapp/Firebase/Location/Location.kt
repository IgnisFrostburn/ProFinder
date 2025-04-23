package citu.profinderapp.Firebase.Location

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Location (
    val location:String = "",
    val time:Date = Date()
)

fun saveLocation(teacherId: String, location: Location) {
    val db = FirebaseFirestore.getInstance()
    Log.e("Teacher ID", teacherId)
    db.collection("teachers")
        .document(teacherId)
        .collection("locations")
        .add(location)
        .addOnSuccessListener {
            Log.d("LocationUpload", "Location saved successfully!")
        }
        .addOnFailureListener { e ->
            Log.e("LocationUpload", "Failed to save location: ${e.message}")
        }
}

fun getLatestLocation(teacherId: String, callback: (Location?) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("teachers")
        .document(teacherId)
        .collection("locations")
        .orderBy("time", com.google.firebase.firestore.Query.Direction.DESCENDING)
        .limit(1)
        .get()
        .addOnSuccessListener { result ->
            if (!result.isEmpty) {
                val doc = result.documents[0]
                val location = doc.toObject(Location::class.java)
                callback(location)
            } else {
                callback(null)
            }
        }
        .addOnFailureListener { e ->
            Log.e("GetLatestLocation", "Error getting latest location: ${e.message}")
            callback(null)
        }
}

fun changeDateFormat(date: Date): String {
    val outputFormat = SimpleDateFormat("h:mm MMMM dd, yyyy", Locale.ENGLISH)
    return outputFormat.format(date)
}
