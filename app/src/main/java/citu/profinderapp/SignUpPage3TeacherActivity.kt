package citu.profinderapp

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import citu.profinderapp.Firebase.User.FirestoreClient
import citu.profinderapp.Firebase.User.TeacherUser
import com.example.mobdev.LoggedInAccount
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

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
            val teacher = TeacherUser(
                auth.currentUser?.uid?:"",
                LoggedInAccount.username?:"",
                password,
                email,
                background,
                course
            )

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.e("Sign Up Email and Password", "SUCCESS!")
                    val user = auth.currentUser

                    firestoreClient.saveTeacher(teacher).let {  success ->
                        if(success) Log.e("Saved Teacher Account", "SUCCESS!")
                        else Log.e("Saved Student Account", "FAIL!")
                    }

                    val signUpIntent = Intent(this, LandingPageActivity::class.java)
                    val animation = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in_fast, R.anim.fade_out_fast)
                    startActivity(signUpIntent, animation.toBundle());

                } else {
                    Log.e("Sign Up Email and Password", "FAIL!")
                }
            }
        }
    }
}