package citu.profinderapp.Activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import citu.profinderapp.Firebase.Location.changeDateFormat
import citu.profinderapp.Firebase.Location.getLatestLocation
import citu.profinderapp.Firebase.User.TeacherUser
import citu.profinderapp.R
import com.bumptech.glide.Glide

class TeacherProfileActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.teacher_profile)
        val teacher = intent.getSerializableExtra("teacher", TeacherUser::class.java)
        val backBtn = findViewById<ImageView>(R.id.back_btn)
        val locationHistoryBtn = findViewById<Button>(R.id.location_history_btn)
        if (teacher == null) {
            finish()
            return
        }
        findViewById<TextView>(R.id.username_lbl).text = teacher.username
        findViewById<TextView>(R.id.email_lbl).text = teacher.email
        findViewById<TextView>(R.id.background_lbl).text = teacher.background
        findViewById<TextView>(R.id.courses_lbl).text = teacher.courses
        getLatestLocation(teacher.id) { latestLocation ->
            if (latestLocation != null) {
                findViewById<TextView>(R.id.current_location_lbl).text = latestLocation.location
                findViewById<TextView>(R.id.time_lbl).text = changeDateFormat(latestLocation.time)
            } else {
                findViewById<TextView>(R.id.current_location_lbl).text = "N/A"
                findViewById<TextView>(R.id.time_lbl).text = "N/A"
            }
        }
        Glide.with(applicationContext)
            .load(teacher.profileImg)
            .circleCrop()
            .placeholder(R.drawable.profile_placeholder_icon)
            .into(findViewById(R.id.profile_image_placeholder))

        backBtn.setOnClickListener {
            val intent = Intent(this, LandingPageActivity::class.java)
            val animation = ActivityOptions.makeCustomAnimation(this,
                R.anim.fade_in_fast,
                R.anim.slide_out_left
            )
            startActivity(intent, animation.toBundle())
        }

        locationHistoryBtn.setOnClickListener {
            val intent = Intent(this, LocationHistoryActivity::class.java)
            intent.putExtra("from_teacher_profile", true)
            startActivity(intent)
        }
    }
}











