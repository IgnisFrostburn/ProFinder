package citu.profinderapp.Activity

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import citu.profinderapp.Accounts.LoggedInTeacher
import citu.profinderapp.Firebase.Location.changeDateFormat
import com.bumptech.glide.Glide
import citu.profinderapp.Accounts.LoggedInAccount
import citu.profinderapp.R
import java.util.Calendar
import java.util.Date

class TeacherBioActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bio_teacher)

        val backBtn = findViewById<ImageView>(R.id.back_btn)
        val locationHistoryBtn = findViewById<Button>(R.id.location_history_btn)
        findViewById<TextView>(R.id.username_lbl).text = LoggedInAccount.username
        findViewById<TextView>(R.id.email_lbl).text = LoggedInAccount.email
        findViewById<TextView>(R.id.background_lbl).text = LoggedInTeacher.background
        findViewById<TextView>(R.id.courses_lbl).text = LoggedInTeacher.department
        findViewById<TextView>(R.id.current_location_lbl).text = if((LoggedInTeacher.latestLocation?.location
                ?: String) == ""
        ) {
            "N/A"
        } else {
            LoggedInTeacher.latestLocation?.location
        }
        findViewById<TextView>(R.id.time_lbl).text = if (LoggedInTeacher.latestLocation?.time == Date(0)) {
            changeDateFormat(Calendar.getInstance().time)
        } else {
            changeDateFormat(LoggedInTeacher.latestLocation?.time ?: Calendar.getInstance().time)
        }

        Glide.with(applicationContext)
            .load(LoggedInAccount.profileImg)
            .circleCrop()
            .placeholder(R.drawable.profile_placeholder_icon)
            .into(findViewById(R.id.profile_image_placeholder))
        locationHistoryBtn.setOnClickListener {
            val intent = Intent(this, LocationHistoryActivity::class.java)
            startActivity(intent)
        }

        backBtn.setOnClickListener {
            val intent = Intent(this, LandingPageActivity::class.java)
            intent.putExtra("navigate_to_page", 1)
            val animation = ActivityOptions.makeCustomAnimation(this,
                R.anim.fade_in_fast,
                R.anim.slide_out_left
            )
            startActivity(intent, animation.toBundle())
        }
    }

    override fun onResume() {
        super.onResume()
        updateLocationUI()
    }

    private fun updateLocationUI() {
        findViewById<TextView>(R.id.username_lbl).text = LoggedInAccount.username
        findViewById<TextView>(R.id.email_lbl).text = LoggedInAccount.email
        findViewById<TextView>(R.id.background_lbl).text = LoggedInTeacher.background
        findViewById<TextView>(R.id.courses_lbl).text = LoggedInTeacher.department

        LoggedInTeacher.latestLocation?.let { location ->
            findViewById<TextView>(R.id.current_location_lbl).text = location.location
            findViewById<TextView>(R.id.time_lbl).text = changeDateFormat(location.time)
        } ?: run {
            findViewById<TextView>(R.id.current_location_lbl).text = "N/A"
            findViewById<TextView>(R.id.time_lbl).text = "N/A"
        }

        Glide.with(applicationContext)
            .load(LoggedInAccount.profileImg)
            .circleCrop()
            .placeholder(R.drawable.profile_placeholder_icon)
            .into(findViewById(R.id.profile_image_placeholder))
    }

}