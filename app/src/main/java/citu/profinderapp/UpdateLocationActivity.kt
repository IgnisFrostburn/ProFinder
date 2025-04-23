package citu.profinderapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import citu.profinderapp.Accounts.LoggedInTeacher
import citu.profinderapp.Firebase.Location.Location
import citu.profinderapp.Firebase.Location.getLatestLocation
import citu.profinderapp.Firebase.Location.saveLocation
import citu.profinderapp.Accounts.LoggedInAccount
import java.util.Calendar

class UpdateLocationActivity : Activity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_location)
        val arrivedBtn = findViewById<Button>(R.id.arrived_btn)
        val departedBtn = findViewById<Button>(R.id.departed_btn)
        val confirmUpdateLocation = findViewById<Button>(R.id.confirm_update_location_btn)
        val updateLocationET = findViewById<EditText>(R.id.update_location_et)
        val backBtn = findViewById<ImageView>(R.id.back_btn)

        arrivedBtn.setOnClickListener {
            saveLocation(LoggedInAccount.id, Location("Arrived", Calendar.getInstance().time))
            getLatestLocation()
            goToProfile()
        }

        departedBtn.setOnClickListener {
            saveLocation(LoggedInAccount.id, Location("Departed", Calendar.getInstance().time))
            getLatestLocation()
            goToProfile()
        }

        confirmUpdateLocation.setOnClickListener {
            saveLocation(LoggedInAccount.id, Location(updateLocationET.text.toString(), Calendar.getInstance().time))
            getLatestLocation()
            goToProfile()
        }

        backBtn.setOnClickListener {
            goToProfile()
        }
    }

    private fun getLatestLocation(){
        getLatestLocation(LoggedInAccount.id) { latestLocation ->
            if (latestLocation != null) {
                LoggedInTeacher.setLatestLoc(latestLocation)
                Log.d("LatestLocation", "Location: ${latestLocation.location}, Time: ${latestLocation.time}")
            } else {
                Log.d("LatestLocation", "No location found.")
            }
        }
    }

    private fun goToProfile() {
        Toast.makeText(this, "Location Updated Successfully!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LandingPageActivity::class.java)
        intent.putExtra("navigate_to_page", 1)
        startActivity(intent)
    }
}