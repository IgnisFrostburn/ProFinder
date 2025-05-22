package citu.profinderapp.Activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import citu.profinderapp.Accounts.LoggedInAccount
import citu.profinderapp.Accounts.LoggedInStudent
import citu.profinderapp.Firebase.User.FirestoreClient
import citu.profinderapp.Firebase.User.StudentUser
import citu.profinderapp.R
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import java.io.File

class ChangeStudentBioActivity : Activity() {
    private val chooseImgRequest = 1
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_student_bio)
        val confirmChangesBtn = findViewById<Button>(R.id.confirm_changes_btn)
        val backBtn = findViewById<ImageView>(R.id.back_btn)
        val uploadBtn = findViewById<Button>(R.id.upload_img_btn)
        val username = findViewById<EditText>(R.id.username_tf)
        val email = findViewById<EditText>(R.id.email_tf)
        val password = findViewById<EditText>(R.id.password_tf)
        val courseYear = findViewById<EditText>(R.id.course_year_tf)
        val phoneNumber = findViewById<EditText>(R.id.phone_number_tf)
        val customInfo = findViewById<EditText>(R.id.custom_info_tf)

        username.setText(LoggedInAccount.username)
        email.setText(LoggedInAccount.email)
        password.setText(LoggedInAccount.password)
        courseYear.setText(LoggedInStudent.course)
        phoneNumber.setText(LoggedInStudent.phoneNumber)
        customInfo.setText(LoggedInStudent.customInfo)

        backBtn.setOnClickListener {
            val intent = Intent(this, LandingPageActivity::class.java)
            intent.putExtra("navigate_to_page", 1)
            startActivity(intent)
        }

        uploadBtn.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Profile Picture"),
                chooseImgRequest
            )
        }

        confirmChangesBtn.setOnClickListener {
            val firestoreClient = FirestoreClient()
            uploadImageToCloudinary { imageUrl ->
                val student = StudentUser(
                    LoggedInAccount.id,
                    username.text.toString(),
                    password.text.toString(),
                    email.text.toString(),
                    "teacher",
                    imageUrl ?: LoggedInAccount.profileImg,
                    courseYear.text.toString(),
                    phoneNumber.text.toString(),
                    customInfo.text.toString()
                )

                LoggedInAccount.username = student.username
                LoggedInAccount.password = student.password
                LoggedInAccount.email = student.email
                LoggedInAccount.profileImg = student.profileImg
                LoggedInStudent.course = student.course
                LoggedInStudent.phoneNumber = student.phoneNumber
                LoggedInStudent.customInfo = student.personalInfo


                firestoreClient.updateStudent(student) { success ->
                    if (success) {
                        Toast.makeText(this, "Bio updated successfully!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Bio update failed!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == chooseImgRequest && resultCode == RESULT_OK) {
            imageUri = data?.data
            Toast.makeText(this, "Image Selected!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadImageToCloudinary(callback: (String?) -> Unit) {
        if (imageUri == null) {
            callback(null)
            return
        }

        Thread {
            try {
                val cloudinary = Cloudinary(
                    ObjectUtils.asMap(
                        "cloud_name", "djraazgo4",
                        "api_key", "319276583488733",
                        "api_secret", "Fz31dNDDXbw_88hOrLyqx7e4zGs"
                    )
                )

                val imageFile = uriToFile(imageUri!!)
                val result = cloudinary.uploader().upload(
                    imageFile,
                    ObjectUtils.asMap(
                        "public_id", "students/${LoggedInAccount.id}",
                        "overwrite", true,
                        "folder", "students"
                    )
                )
                val imageUrl = result["secure_url"] as String
                runOnUiThread { callback(imageUrl) }

            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
                    callback(null)
                }
            }
        }.start()
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