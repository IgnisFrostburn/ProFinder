package citu.profinderapp

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import citu.profinderapp.Accounts.LoggedInTeacher
import citu.profinderapp.Firebase.Location.getLatestLocation
import citu.profinderapp.Firebase.User.FirestoreClient
import citu.profinderapp.Accounts.LoggedInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginPageActivity : Activity() {
    private lateinit var auth: FirebaseAuth
    private val firestoreClient = FirestoreClient()
    private lateinit var loadingScreen:ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        val signUpBtn = findViewById<Button>(R.id.sign_up_btn)
        val loginBtn = findViewById<Button>(R.id.login_btn)
        val emailField = findViewById<EditText>(R.id.email_tf)
        val passwordField = findViewById<EditText>(R.id.password_tf)

        loadingScreen = findViewById(R.id.loading_screen_constraint)
        val loginScreen = findViewById<ConstraintLayout>(R.id.login_constraint)

        auth = Firebase.auth

        signUpBtn.setOnClickListener {
            val registerIntent = Intent(this, SignUpPage1Activity::class.java)
            val animation = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in_fast, R.anim.fade_out_fast)
            startActivity(registerIntent, animation.toBundle())
        }

        loginBtn.setOnClickListener {
            hideKeyboard()
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            loadingScreen.visibility = View.VISIBLE
                            loginScreen.visibility = View.GONE
                            val user = auth.currentUser
                            Log.e("Firebase Login", "SUCCESS! User ID: ${user?.uid}")

                            user?.let {
                                //teacher
                                firestoreClient.getTeacherData { teacherUser ->
                                    if (teacherUser != null) {
                                        Log.e("Firestore", "Teacher account loaded")
                                        getLatestLocation(LoggedInAccount.id) { latestLocation ->
                                            if (latestLocation != null) {
                                                LoggedInTeacher.setLatestLoc(latestLocation)
                                                Log.d("LatestLocation", "Location: ${latestLocation.location}, Time: ${latestLocation.time}")
                                            } else {
                                                Log.d("LatestLocation", "No location found.")
                                            }
                                        }
                                        goToLandingPage()
                                    } else {
                                        // student
                                        firestoreClient.getStudentData { studentUser ->
                                            if (studentUser != null) {
                                                Log.e("Firestore", "Student account loaded")
                                                Log.e("Account name", LoggedInAccount.username!!)
                                                Log.e("Account type", LoggedInAccount.isStudent.toString())
                                                goToLandingPage()
                                            } else {
                                                Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    .addOnFailureListener {
                        loginScreen.visibility = View.VISIBLE
                        loadingScreen.visibility = View.GONE
                        Toast.makeText(this, "Account does not exist or incorrect password", Toast.LENGTH_SHORT).show()
                    }
                    .addOnCompleteListener {
//                        loadingScreen.visibility = View.GONE
                    }
            } else {
                Toast.makeText(this, "Fill in the fields", Toast.LENGTH_SHORT).show()
                Log.e("Log In Fields", "NULL!")
            }
        }
    }

    private fun goToLandingPage() {
        Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
        loadingScreen.visibility = View.GONE
        val intent = Intent(this, LandingPageActivity::class.java)
        val animation = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in_fast, R.anim.fade_out_fast)
        startActivity(intent, animation.toBundle())
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
