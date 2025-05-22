package citu.profinderapp.Adapter

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPageAdapter(
    val items:ArrayList<Fragment>,
    activity: AppCompatActivity
    ):FragmentStateAdapter(
        activity
    ) {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        Log.e("ViewPageAdapter", "Creating fragment at position $position: ${items[position]::class.java.simpleName}")
        return items[position]
    }
}