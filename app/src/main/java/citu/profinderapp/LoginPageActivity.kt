package citu.profinderapp

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import citu.profinderapp.Firebase.User.FirestoreClient
import com.example.mobdev.LoggedInAccount
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class LoginPageActivity : Activity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        val signUpBtn = findViewById<Button>(R.id.sign_up_btn)
        val loginBtn = findViewById<Button>(R.id.login_btn)

        val emailField = findViewById<EditText>(R.id.email_tf)
        val passwordField = findViewById<EditText>(R.id.password_tf)
        auth = Firebase.auth;
        val db = FirebaseFirestore.getInstance()

        signUpBtn.setOnClickListener {
            Log.e("Sign up button is clicked", "SUCCESS!")
            val registerIntent = Intent(this, SignUpPage1Activity::class.java)
            val animation = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in_fast, R.anim.fade_out_fast)
            startActivity(registerIntent, animation.toBundle());
        }

        loginBtn.setOnClickListener {
            Log.e("Login button is clicked", "SUCCESS!")
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            Log.e("" + LoggedInAccount.username, "SUCCESS!")
            Log.e("" + LoggedInAccount.password, "SUCCESS!")
            Log.e("" + email, "SUCCESS!")
            Log.e("" + password, "SUCCESS!")

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            Log.e("Firebase Login", "SUCCESS! User ID: ${user?.uid}")

                            user?.let {
                                val uid = it.uid

                                db.collection("teachers").document(uid).get()
                                    .addOnSuccessListener { document ->
                                        if (document.exists()) {
                                            Log.e("Firestore", "Teacher account")
                                            val username = document.getString("username") ?: "Unknown"
                                            val emailTeacher = document.getString("email") ?: ""
                                            val passwordTeacher = document.getString("password") ?: ""

                                            LoggedInAccount.setValues(username, emailTeacher, passwordTeacher, false)
                                            val intent = Intent(this, LandingPageActivity::class.java)
                                            val animation = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in_fast, R.anim.fade_out_fast)
                                            startActivity(intent, animation.toBundle());
                                        } else {
                                            Log.e("Firestore", "Student Account")
                                            db.collection("students").document(uid).get()
                                                .addOnSuccessListener { document1 ->
                                                    if (document1.exists()) {
                                                        Log.e("Firestore", "Teacher account")
                                                        val username = document1.getString("username") ?: "Unknown"
                                                        val emailTeacher = document1.getString("email") ?: ""
                                                        val passwordTeacher = document1.getString("password") ?: ""

                                                        LoggedInAccount.setValues(username, emailTeacher, passwordTeacher, true)

                                                    }

                                                    val intent = Intent(this, LandingPageActivity::class.java)
                                                    val animation = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in_fast, R.anim.fade_out_fast)
                                                    startActivity(intent, animation.toBundle());
                                                }
                                                .addOnFailureListener { e ->
                                                    Log.e("Firestore Error", "Error getting teacher data", e)
                                                    Toast.makeText(this, "Failed to load user data", Toast.LENGTH_SHORT).show()
                                                }
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("Firestore Error", "Error getting teacher data", e)
                                        Toast.makeText(this, "Failed to load user data", Toast.LENGTH_SHORT).show()
                                    }


                            }
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Account does not exist or incorrect password", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Fill in the fields", Toast.LENGTH_SHORT).show()
                Log.e("Log In Fields" , "NULL!")
            }
        }
    }
}