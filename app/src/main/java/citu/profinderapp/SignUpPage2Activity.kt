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
import citu.profinderapp.Accounts.LoggedInAccount

class SignUpPage2Activity : Activity() {
    private val chooseImgRequest = 1
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_page_2)
        val isStudent = intent.getBooleanExtra("isStudent", false)
        val continueBtn = findViewById<Button>(R.id.continue_btn)
        val backBtn = findViewById<Button>(R.id.back_btn)
        val uploadBtn = findViewById<Button>(R.id.upload_img_btn)
        var username:String
        var email:String
        var password:String
        var confirmPassword:String


        uploadBtn.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), chooseImgRequest)
        }

        backBtn.setOnClickListener {
            Log.e("Back button is clicked", "SUCCESS!")
            val backIntent = Intent(this, SignUpPage1Activity::class.java)
            val animation = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in_fast, R.anim.fade_out_fast)
            startActivity(backIntent, animation.toBundle());
        }

        continueBtn.setOnClickListener {
            Log.e("Continue button is clicked", "SUCCESS!")
            username = findViewById<EditText>(R.id.username_tf).text.toString()
            email = findViewById<EditText>(R.id.email_tf).text.toString()
            password = findViewById<EditText>(R.id.password_tf).text.toString()
            confirmPassword = findViewById<EditText>(R.id.confirm_password_tf).text.toString()

            if(confirmPassword != password ||
                isBlank(username) ||
                isBlank(email) ||
                isBlank(password) ||
                imageUri == null) {
                Log.e("Input error", "SUCCESS!")
            } else if(password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
            } else {
                val continueIntent:Intent = if(isStudent) Intent(this, SignUpPage3StudentActivity::class.java)
                else Intent(this, SignUpPage3TeacherActivity::class.java)

                if(isStudent) LoggedInAccount.setValues(username, email, password, true, imageUri.toString())
                else LoggedInAccount.setValues(username, email, password, false, imageUri.toString())

                val animation = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in_fast, R.anim.fade_out_fast)
                startActivity(continueIntent, animation.toBundle());
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == chooseImgRequest && resultCode == RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            Log.d("Image URI", "Selected image URI: $imageUri")
        }
    }

    private fun isBlank(text: String):Boolean {
        return text.isBlank()
    }

}