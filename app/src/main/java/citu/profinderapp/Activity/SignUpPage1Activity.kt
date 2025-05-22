package citu.profinderapp.Activity

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import citu.profinderapp.R

class  SignUpPage1Activity : Activity() {

    companion object {
        var isStudent:Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_page_1)
        val backBtn = findViewById<Button>(R.id.back_to_login_btn)
        val studentBtn = findViewById<Button>(R.id.student_acc_type_btn)
        val teacherBtn = findViewById<Button>(R.id.teacher_acc_type_btn)

        backBtn.setOnClickListener {
            Log.e("Back to login button is clicked", "SUCCESS!")
            val backIntent = Intent(this, LoginPageActivity::class.java)
            val animation = ActivityOptions.makeCustomAnimation(this,
                R.anim.fade_in_fast,
                R.anim.fade_out_fast
            )
            startActivity(backIntent, animation.toBundle());
        }

        studentBtn.setOnClickListener {
            Log.e("Student button is clicked", "SUCCESS!")
            isStudent = true;
            val studentIntent = Intent(this, SignUpPage2Activity::class.java)
            studentIntent.putExtra("isStudent", isStudent);
            val animation = ActivityOptions.makeCustomAnimation(this,
                R.anim.fade_in_fast,
                R.anim.fade_out_fast
            )
            startActivity(studentIntent, animation.toBundle());
        }

        teacherBtn.setOnClickListener {
            Log.e("Teacher button is clicked", "SUCCESS!")
            isStudent = false;
            val teacherIntent = Intent(this, SignUpPage2Activity::class.java)
            teacherIntent.putExtra("isStudent", isStudent);
            val animation = ActivityOptions.makeCustomAnimation(this,
                R.anim.fade_in_fast,
                R.anim.fade_out_fast
            )
            startActivity(teacherIntent, animation.toBundle());
        }
    }
}















