package citu.profinderapp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import citu.profinderapp.Firebase.User.SelectedTeacher
import citu.profinderapp.Firebase.User.TeacherUser
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class TeacherAdapter(
    private val context: Context,
    private val teacherList: MutableList<TeacherUser>
) : RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder>() {

    class TeacherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val teacherImage: ImageView = view.findViewById(R.id.teacher_profile_image)
        val teacherName: TextView = view.findViewById(R.id.teacher_name)
        val teacherLocation: TextView = view.findViewById(R.id.location)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.browse_teacher_item, parent, false)
        Log.e("ViewHolder", "GOOD")
        return TeacherViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        val teacher = teacherList[position]
        holder.teacherName.text = teacher.username

        val db = FirebaseFirestore.getInstance()
        db.collection("teachers")
            .document(teacher.id)
            .collection("locations")
            .orderBy("time", Query.Direction.DESCENDING)
            .limit(1)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("TeacherLocation", "Listen failed: ${error.message}")
                    holder.teacherLocation.text = "Unavailable"
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val doc = snapshot.documents[0]
                    val latestLocation = doc.toObject(citu.profinderapp.Firebase.Location.Location::class.java)
                    holder.teacherLocation.text = latestLocation?.location ?: "Departed"

                    Glide.with(holder.itemView.context)
                        .load(teacher.profileImg)
                        .circleCrop()
                        .placeholder(R.drawable.profile_placeholder_icon)
                        .into(holder.teacherImage)
                } else {
                    holder.teacherLocation.text = "Departed"
                }
            }


        Glide.with(holder.itemView.context)
            .load(teacher.profileImg)
            .circleCrop()
            .placeholder(R.drawable.profile_placeholder_icon)
            .into(holder.teacherImage)

        holder.itemView.setOnClickListener {
            SelectedTeacher.teacher = teacher
            val intent = Intent(context, TeacherProfileActivity::class.java)
            intent.putExtra("teacher", teacher)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return teacherList.size
    }
}













