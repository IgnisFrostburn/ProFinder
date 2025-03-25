package citu.profinderapp

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.mobdev.LoggedInAccount

class LoginPageActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        val signUpBtn = findViewById<Button>(R.id.sign_up_btn)
        val loginBtn = findViewById<Button>(R.id.login_btn)

        var username:String?
        var password:String?

        signUpBtn.setOnClickListener {
            Log.e("Sign up button is clicked", "SUCCESS!")
            val registerIntent = Intent(this, SignUpPage1Activity::class.java)
            val animation = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in_fast, R.anim.fade_out_fast)
            startActivity(registerIntent, animation.toBundle());
        }

        loginBtn.setOnClickListener {
            Log.e("Login button is clicked", "SUCCESS!")
            username = findViewById<EditText>(R.id.username_tf).text.toString()
            password = findViewById<EditText>(R.id.password_tf).text.toString()

            Log.e("" + LoggedInAccount.username, "SUCCESS!")
            Log.e("" + LoggedInAccount.password, "SUCCESS!")
            Log.e("" + username, "SUCCESS!")
            Log.e("" + password, "SUCCESS!")

//            if(LoggedInAccount.username == username && LoggedInAccount.password == password) {
                Log.e("Log in" , "SUCCESS!")
                val intent = Intent(this, LandingPageActivity::class.java)
                val animation = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in_fast, R.anim.fade_out_fast)
                startActivity(intent, animation.toBundle());
//            }
        }
    }
}