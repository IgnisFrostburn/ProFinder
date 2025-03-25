package citu.profinderapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import citu.profinderapp.Firebase.User.TeacherUser

class TeacherAdapter(
    private val teacherList: List<TeacherUser>
) : RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder>() {

    class TeacherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val teacherImage: ImageView = view.findViewById(R.id.teacher_profile_image)
        val teacherName: TextView = view.findViewById(R.id.teacher_name)
        val teacherLocation: TextView = view.findViewById(R.id.location)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.browse_teacher_fragment, parent, false)
        return TeacherViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        val teacher = teacherList[position]
        holder.teacherName.text = teacher.username
//        holder.teacherLocation.text = teacher.location
        holder.teacherLocation.text = "test location"
    }

    override fun getItemCount(): Int {
        return teacherList.size
    }
}













