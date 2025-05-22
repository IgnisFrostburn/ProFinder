package citu.profinderapp.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import citu.profinderapp.Firebase.Location.changeDateFormat
import citu.profinderapp.Firebase.User.Location
import citu.profinderapp.R

class AdapterLocation(
    private val context: Context,
    private val locationList: MutableList<Location>
) : RecyclerView.Adapter<AdapterLocation.LocationViewHolder>() {
    class LocationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val teacherLocation: TextView = view.findViewById(R.id.location)
        val teacherTime: TextView = view.findViewById(R.id.time)
        val locationBtn: LinearLayout = view.findViewById(R.id.location_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_location_history, parent, false)
        Log.e("ViewHolder", "GOOD")
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locationList[position]
        holder.teacherLocation.text = location.location
        location.time?.toDate()?.let {
            holder.teacherTime.text = changeDateFormat(it)
        }

        if (position == 0) {
            holder.locationBtn.backgroundTintList = context.getColorStateList(R.color.et_bg)
            holder.teacherTime.setTextColor(context.getColor(R.color.base_green))
        } else {
            holder.locationBtn.backgroundTintList = context.getColorStateList(R.color.light_bg)
            holder.teacherTime.setTextColor(context.getColor(R.color.white))
        }
    }

    override fun getItemCount(): Int {
        return locationList.size
    }
}