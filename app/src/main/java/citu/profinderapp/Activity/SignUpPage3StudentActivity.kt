package citu.profinderapp.Activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import citu.profinderapp.Accounts.LoggedInStudent
import citu.profinderapp.Firebase.User.FirestoreClient
import citu.profinderapp.Firebase.User.StudentUser
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import citu.profinderapp.Accounts.LoggedInAccount
import citu.profinderapp.Activity.LandingPageActivity
import citu.profinderapp.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import java.io.File

class SignUpPage3StudentActivity : Activity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestoreClient: FirestoreClient

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_page_3_student)
        val signUpBtn = findViewById<Button>(R.id.sign_up_btn)
        val backBtn = findViewById<Button>(R.id.back_btn)
        var courseAndYear:String
        var phoneNumber:String
        var customInfo:String
        auth = Firebase.auth
        firestoreClient = FirestoreClient()


        backBtn.setOnClickListener {
            Log.e("Back button is clicked", "SUCCESS!")
            val backIntent = Intent(this, SignUpPage2Activity::class.java)
            val animation = ActivityOptions.makeCustomAnimation(this,
                R.anim.fade_in_fast,
                R.anim.fade_out_fast
            )
            startActivity(backIntent, animation.toBundle());
        }

        signUpBtn.setOnClickListener {
            courseAndYear = findViewById<EditText>(R.id.course_year_tf).text.toString()
            phoneNumber = findViewById<EditText>(R.id.phone_number_tf).text.toString()
            customInfo = findViewById<EditText>(R.id.custom_info_tf).text.toString()
            Log.e("Sign up button is clicked", "SUCCESS!")
            LoggedInStudent.setStudentDetails(courseAndYear, phoneNumber, customInfo)

            val email = LoggedInAccount.email ?: ""
            val password = LoggedInAccount.password ?: ""

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.e("Sign Up Email and Password", "SUCCESS!")
                    val user = auth.currentUser

                    Thread {
                        try {
                            val cloudinary = Cloudinary(
                                ObjectUtils.asMap(
                                    "cloud_name", "djraazgo4",
                                    "api_key", "319276583488733",
                                    "api_secret", "Fz31dNDDXbw_88hOrLyqx7e4zGs"
                                )
                            )

                            val imageUri = Uri.parse(LoggedInAccount.profileImg)
                            val imageFile = uriToFile(imageUri)
                            val result = cloudinary.uploader().upload(
                                imageFile,
                                ObjectUtils.asMap(
                                    "public_id", "students/${user?.uid}",
                                    "overwrite", true,
                                    "folder", "students"
                                )
                            )

                            val imageUrl = result["secure_url"] as String
                            Log.d("Cloudinary", "Uploaded URL: $imageUrl")

                        val student = StudentUser(
                            user?.uid?:"",
                            LoggedInAccount.username?:"",
                            password,
                            email,
                            "student",
                            LoggedInAccount.profileImg,
                            courseAndYear,
                            phoneNumber,
                            customInfo
                        )

                            runOnUiThread {
                                firestoreClient.saveStudent(student).let { success ->
                                    if (success) {
                                        Toast.makeText(this, "Registration Complete!", Toast.LENGTH_SHORT).show()
                                        Log.e("Saved Student", "SUCCESS!")
                                    }
                                    else {
                                        Toast.makeText(this, "Your registration failed!", Toast.LENGTH_SHORT).show()
                                        Log.e("Saved Student", "FAIL!")
                                    }
                                }
                                clearLoggedInStudent()
                                val signUpIntent = Intent(this, LoginPageActivity::class.java)
                                val animation = ActivityOptions.makeCustomAnimation(this,
                                    R.anim.fade_in_fast,
                                    R.anim.fade_out_fast
                                )
                                startActivity(signUpIntent, animation.toBundle())
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                            runOnUiThread {
                                Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }.start()
                } else {
                    Toast.makeText(this, "Your registration failed!", Toast.LENGTH_SHORT).show()
                    Log.e("Sign Up Email and Password", "FAIL!")
                }
            }


//            auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        Log.e("Sign Up Email and Password", "SUCCESS!")
//                        val user = auth.currentUser
//                        val student = StudentUser(
//                            user?.uid?:"",
//                            LoggedInAccount.username?:"",
//                            password,
//                            email,
//                            "student",
//                            LoggedInAccount.profileImg,
//                            courseAndYear,
//                            phoneNumber,
//                            customInfo
//                        )
//                        firestoreClient.saveStudent(student).let {  success ->
//                            if(success) Log.e("Saved Student Account", "SUCCESS!")
//                            else Log.e("Saved Student Account", "FAIL!")
//                        }
//
//                        val signUpIntent = Intent(this, LandingPageActivity::class.java)
//                        val animation = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in_fast, R.anim.fade_out_fast)
//                        startActivity(signUpIntent, animation.toBundle());
//
//                    } else {
//                        Log.e("Sign Up Email and Password", "FAIL!")
//                    }
//                }
        }
    }

    private fun uriToFile(uri: Uri): File {
        val inputStream = contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("upload", ".jpg", cacheDir)
        inputStream.use { input ->
            tempFile.outputStream().use { output ->
                input?.copyTo(output)
            }
        }
        return tempFile
    }

    private fun clearLoggedInAccount() {
        LoggedInAccount.id = ""
        LoggedInAccount.username = null
        LoggedInAccount.email = null
        LoggedInAccount.password = null
        LoggedInAccount.isStudent = false
        LoggedInAccount.profileImg = ""
    }

    private fun clearLoggedInStudent() {
        clearLoggedInAccount()
        LoggedInStudent.course = null
        LoggedInStudent.phoneNumber = null
        LoggedInStudent.customInfo = null
    }
}