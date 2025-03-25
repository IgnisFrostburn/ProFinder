package citu.profinderapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TeacherBioActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bio_teacher)

        val locationHistoryBtn = findViewById<Button>(R.id.location_history_btn)

        locationHistoryBtn.setOnClickListener {
            val intent = Intent(this, LocationHistoryActivity::class.java)
            startActivity(intent)
        }
    }
}