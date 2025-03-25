package citu.profinderapp.Firebase.User

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreClient {
    private val tag = "FirestoreClient: "
    private val db = FirebaseFirestore.getInstance()

    fun saveTeacher(teacherUser: TeacherUser):Boolean {
        return try {
            db.collection("teachers").document(teacherUser.id).set(teacherUser)
            true
        } catch (e:Exception) {
             false
        }
    }

    fun saveStudent(studentUser: StudentUser):Boolean {
        return try {
            db.collection("students").document(studentUser.id).set(studentUser)
            true
        } catch (e:Exception) {
            false
        }
    }

    fun getStudentData(callback: (StudentUser?) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()

        if (userId != null) {
            db.collection("students").document(userId)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val documentSnapshot: DocumentSnapshot? = task.result
                        val studentUser = documentSnapshot?.toObject(StudentUser::class.java)
                        callback(studentUser)
                    }
                }
        }
    }

    fun getTeacherData(callback: (TeacherUser?) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()

        if (userId != null) {
            db.collection("teachers").document(userId)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val documentSnapshot: DocumentSnapshot? = task.result
                        val teacherUser = documentSnapshot?.toObject(TeacherUser::class.java)
                        callback(teacherUser)
                    }
                }
        }
    }

    fun fetchTeachers(teacherList : MutableList<TeacherUser>) : MutableList<TeacherUser> {
        db.collection("teachers")
            .get()
            .addOnSuccessListener { documents ->
                teacherList.clear()
                for(document in documents) {
                    val teacherUser = document.toObject(TeacherUser::class.java)
                    teacherList.add(teacherUser)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Fetch Teachers", "Error getting documents: ", exception)
            }
        return teacherList
    }

//    fun insertUser(
//        user:User
//    ) : Flow<String?> {
//        return callbackFlow {
//            db.collection(collection)
//                .add(user)
//        }
//    }
//
//    private fun User.ToHashMap(): HashMap<String, Any> {
//        return hashMapOf(
//            "username" to username,
//            "password" to password,
//            "email" to email,
//            "accountType" to accountType
//        )
//    }
//
//    private fun Map<String, Any>.toUser(): User {
//        return User(
//            id = this["id"] as String,
//            username = this["username"] as String,
//            password = this["password"] as String,
//            email = this["email"] as String,
//            accountType = this["accountType"] as String
//        )
//    }
}












