package citu.profinderapp.Activity

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import citu.profinderapp.Accounts.LoggedInTeacher
import citu.profinderapp.Firebase.User.Location
import citu.profinderapp.Firebase.User.SelectedTeacher
import citu.profinderapp.Firebase.User.TeacherUser
import citu.profinderapp.Accounts.LoggedInAccount
import citu.profinderapp.Adapter.AdapterLocation
import citu.profinderapp.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class LocationHistoryActivity : Activity() {
    val locationList = mutableListOf<Location>()
    lateinit var recyclerView:RecyclerView
    lateinit var swipeRefresh:SwipeRefreshLayout
    var fromTeacherProfile:Boolean = false
    lateinit var teacher:TeacherUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.location_history)
        fromTeacherProfile = intent.getBooleanExtra("from_teacher_profile", false)
        swipeRefresh = findViewById(R.id.swipe_refresh)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        swipeRefresh.setOnRefreshListener {
            fetchLocations()
        }
        fetchLocations()

        val backBtn = findViewById<ImageView>(R.id.back_btn)
        backBtn.setOnClickListener {
            val intent: Intent;
            if(fromTeacherProfile) {
                intent =Intent(this, TeacherProfileActivity::class.java)
                intent.putExtra("teacher", teacher)
            } else intent = Intent(this, TeacherBioActivity::class.java)
            val animation = ActivityOptions.makeCustomAnimation(this,
                R.anim.fade_in_fast,
                R.anim.slide_out_left
            )
            startActivity(intent, animation.toBundle())
        }
    }

    private fun fetchLocations() {
        swipeRefresh.isRefreshing = true
        teacher =
            if(fromTeacherProfile) SelectedTeacher.teacher
            else {
                TeacherUser(
                    LoggedInAccount.id,
                    LoggedInAccount.username!!,
                    LoggedInAccount.password!!,
                    LoggedInAccount.email!!,
                    "teacher",
                    LoggedInAccount.profileImg,
                    LoggedInTeacher.background!!,
                    LoggedInTeacher.department!!
                )
            }
        val db = FirebaseFirestore.getInstance()
        db.collection("teachers")
            .document(teacher.id)
            .collection("locations")
            .orderBy("time", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { snapshot ->
                locationList.clear()
                for (doc in snapshot.documents) {
                    val location = doc.toObject(Location::class.java)
                    location?.let { locationList.add(it) }
                }

                val adapter = AdapterLocation(this, locationList)
                recyclerView.adapter = adapter
                swipeRefresh.isRefreshing = false
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Error fetching locations", e)
            }
    }

}