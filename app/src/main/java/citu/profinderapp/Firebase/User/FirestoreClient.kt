package citu.profinderapp.Firebase.User

import android.util.Log
import citu.profinderapp.Accounts.LoggedInTeacher
import citu.profinderapp.Accounts.LoggedInAccount
import citu.profinderapp.Accounts.LoggedInStudent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreClient {
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

    fun updateTeacher(teacherUser: TeacherUser, onComplete: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("teachers").document(teacherUser.id)
            .set(teacherUser)
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreClient", "Document overwrite failed", e)
                onComplete(false)
            }
    }


    fun updateStudent(studentUser: StudentUser, onComplete: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("students").document(studentUser.id)
            .set(studentUser)
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreClient", "Document overwrite failed", e)
                onComplete(false)
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

                        studentUser?.let {
                            LoggedInAccount.setID(it.id)

                            LoggedInAccount.setValues(
                                username = it.username,
                                email = it.email,
                                password = it.password,
                                isStudent = true,
                                profileImg = it.profileImg
                            )

                            LoggedInStudent.setStudentDetails(
                                course = it.course,
                                phoneNumber =  it.phoneNumber,
                                personalInfo = it.personalInfo
                            )
                        }

                        callback(studentUser)
                    } else {
                        callback(null)
                    }
                }
        } else {
            callback(null)
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

                        teacherUser?.let {
                            LoggedInAccount.setID(it.id)

                            LoggedInAccount.setValues(
                                username = it.username,
                                email = it.email,
                                password = it.password,
                                isStudent = false,
                                profileImg = it.profileImg
                            )

                            LoggedInTeacher.setTeacherDetails(
                                background = it.background,
                                department = it.courses
                            )
                        }

                        callback(teacherUser)
                    } else {
                        callback(null)
                    }
                }
        } else {
            callback(null)
        }
    }

    fun fetchTeachers(callback: (List<TeacherUser>) -> Unit) {
        val teachersList = mutableListOf<TeacherUser>()

        db.collection("teachers")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val teacher = document.toObject(TeacherUser::class.java)
                    teachersList.add(teacher)
                }
                callback(teachersList)
            }
            .addOnFailureListener { exception ->
                Log.e("FirestoreClient", "Error getting documents: ", exception)
            }
    }
}












