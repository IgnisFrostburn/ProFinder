package citu.profinderapp

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import citu.profinderapp.Accounts.LoggedInTeacher
import citu.profinderapp.Firebase.User.FirestoreClient
import citu.profinderapp.Firebase.User.TeacherUser
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import citu.profinderapp.Accounts.LoggedInAccount
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import java.io.File

class SignUpPage3TeacherActivity : Activity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestoreClient: FirestoreClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_page_3_teacher)

        val signUpBtn = findViewById<Button>(R.id.sign_up_btn)
        val backBtn = findViewById<Button>(R.id.back_btn)
        var background:String
        var course:String
        auth = Firebase.auth
        firestoreClient = FirestoreClient()

        backBtn.setOnClickListener {
            Log.e("Back button is clicked", "SUCCESS!")
            val backIntent = Intent(this, SignUpPage2Activity::class.java)
            val animation = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in_fast, R.anim.fade_out_fast)
            startActivity(backIntent, animation.toBundle());
        }

        signUpBtn.setOnClickListener {
            Log.e("Sign up button is clicked", "SUCCESS!")
            background = findViewById<EditText>(R.id.background_tf).text.toString()
            course = findViewById<EditText>(R.id.courses_tf).text.toString()
            LoggedInTeacher.setTeacherDetails(background, course)

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
                                    "public_id", "teachers/${user?.uid}",
                                    "overwrite", true,
                                    "folder", "teachers"
                                )
                            )

                            val imageUrl = result["secure_url"] as String
                            Log.d("Cloudinary", "Uploaded URL: $imageUrl")

                            val teacher = TeacherUser(
                                user?.uid ?: "",
                                LoggedInAccount.username ?: "",
                                password,
                                email,
                                "teacher",
                                imageUrl,
                                background,
                                course
                            )

                            runOnUiThread {
                                firestoreClient.saveTeacher(teacher).let { success ->
                                    if (success) {
                                        Toast.makeText(this, "Registration Complete!", Toast.LENGTH_SHORT).show()
                                        Log.e("Saved Teacher", "SUCCESS!")
                                    }
                                    else {
                                        Toast.makeText(this, "Your registration failed!", Toast.LENGTH_SHORT).show()
                                        Log.e("Saved Teacher", "FAIL!")
                                    }
                                }

                                val signUpIntent = Intent(this, LandingPageActivity::class.java)
                                val animation = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in_fast, R.anim.fade_out_fast)
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

}