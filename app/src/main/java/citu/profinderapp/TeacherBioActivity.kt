package citu.profinderapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import citu.profinderapp.Accounts.LoggedInTeacher
import citu.profinderapp.Firebase.Location.changeDateFormat
import com.bumptech.glide.Glide
import citu.profinderapp.Accounts.LoggedInAccount

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
        findViewById<TextView>(R.id.current_location_lbl).text = LoggedInTeacher.latestLocation!!.location
        findViewById<TextView>(R.id.time_lbl).text = changeDateFormat(LoggedInTeacher.latestLocation!!.time)
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
            startActivity(intent)
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
            findViewById<TextView>(R.id.current_location_lbl).text = "No location"
            findViewById<TextView>(R.id.time_lbl).text = "N/A"
        }

        Glide.with(applicationContext)
            .load(LoggedInAccount.profileImg)
            .circleCrop()
            .placeholder(R.drawable.profile_placeholder_icon)
            .into(findViewById(R.id.profile_image_placeholder))
    }

}