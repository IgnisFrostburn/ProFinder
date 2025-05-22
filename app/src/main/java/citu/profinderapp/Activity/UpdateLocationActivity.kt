package citu.profinderapp.Activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import citu.profinderapp.Accounts.LoggedInTeacher
import citu.profinderapp.Firebase.Location.Location
import citu.profinderapp.Firebase.Location.getLatestLocation
import citu.profinderapp.Firebase.Location.saveLocation
import citu.profinderapp.Accounts.LoggedInAccount
import citu.profinderapp.R
import java.util.Calendar

class UpdateLocationActivity : Activity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_location)
        val arrivedBtn = findViewById<Button>(R.id.arrived_btn)
        val departedBtn = findViewById<Button>(R.id.departed_btn)
        val confirmUpdateLocation = findViewById<Button>(R.id.confirm_update_location_btn)
        val updateLocationACTV = findViewById<AutoCompleteTextView>(R.id.update_location_actv)
        val backBtn = findViewById<ImageView>(R.id.back_btn)

        val suggestions = createSuggestions()
        val adapter = ArrayAdapter(this, R.layout.item_dropdown, suggestions)

        updateLocationACTV.setAdapter(adapter)
        updateLocationACTV.threshold = 1
        updateLocationACTV.setDropDownBackgroundResource(R.color.light_bg)
        updateLocationACTV.dropDownHeight = 500
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
            saveLocation(LoggedInAccount.id, Location(updateLocationACTV.text.toString(), Calendar.getInstance().time))
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
        val animation = ActivityOptions.makeCustomAnimation(this,
            R.anim.fade_in_fast,
            R.anim.slide_out_left
        )
        startActivity(intent, animation.toBundle())
    }



    private fun createSuggestions(): List<String> {
        return listOf("GLE 101", "GLE 102", "GLE 103", "GLE 104", "GLE 105",
            "GLE 201", "GLE 202", "GLE 203", "GLE 204", "GLE 205",
            "GLE 301", "GLE 302", "GLE 303", "GLE 304", "GLE 305",
            "GLE 401", "GLE 402", "GLE 403", "GLE 404", "GLE 405",
            "GLE 501", "GLE 502", "GLE 503", "GLE 504", "GLE 505",
            "GLE 601", "GLE 602", "GLE 603", "GLE 604", "GLE 605",
            "GLE 701", "GLE 702", "GLE 703", "GLE 704", "GLE 705",
            "GLE 801", "GLE 802", "GLE 803", "GLE 804", "GLE 805",
            "RTL 201", "RTL 202", "RTL 203", "RTL 204", "RTL 205", "RTL 206", "RTL 207", "RTL 208", "RTL 209", "RTL 210",
            "RTL 211", "RTL 212", "RTL 213", "RTL 214", "RTL 215", "RTL 216", "RTL 217", "RTL 218", "RTL 219", "RTL 220",
            "RTL 221", "RTL 222", "RTL 223", "RTL 224", "RTL 225", "RTL 226", "RTL 227", "RTL 228", "RTL 229", "RTL 230",
            "RTL 301", "RTL 302", "RTL 303", "RTL 304", "RTL 305", "RTL 306", "RTL 307", "RTL 308", "RTL 309", "RTL 310",
            "RTL 311", "RTL 312", "RTL 313", "RTL 314", "RTL 315", "RTL 316", "RTL 317", "RTL 318", "RTL 319", "RTL 320",
            "RTL 321", "RTL 322", "RTL 323", "RTL 324", "RTL 325", "RTL 326", "RTL 327", "RTL 328", "RTL 329", "RTL 330",
            "RTL 401", "RTL 402", "RTL 403", "RTL 404", "RTL 405", "RTL 406", "RTL 407", "RTL 408", "RTL 409", "RTL 410",
            "RTL 411", "RTL 412", "RTL 413", "RTL 414", "RTL 415", "RTL 416", "RTL 417", "RTL 418", "RTL 419", "RTL 420",
            "RTL 421", "RTL 422", "RTL 423", "RTL 424", "RTL 425", "RTL 426", "RTL 427", "RTL 428", "RTL 429", "RTL 430",
            "NGE 101", "NGE 102", "NGE 103", "NGE 104", "NGE 105",
            "NGE 201", "NGE 202", "NGE 203", "NGE 204", "NGE 205", "NGE 206", "NGE 207")
    }
}