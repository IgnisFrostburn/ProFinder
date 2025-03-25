package citu.profinderapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class LandingPageActivity : AppCompatActivity() {
    lateinit var viewPager:ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.browse_teachers)

        viewPager = findViewById(R.id.view_pager)
        val fragments:ArrayList<Fragment> = arrayListOf(
            BrowseFragment(),
            ProfileFragment(),
            SettingsFragment()
        )
        val adapter = ViewPageAdapter(fragments, this)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        viewPager.adapter = adapter;

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.setIcon(R.drawable.browse_icon)
                1 -> tab.setIcon(R.drawable.profile_icon)
                2 -> tab.setIcon(R.drawable.settings_icon)
            }
        }.attach()
    }

    fun navigateToPage(position: Int) {
        viewPager.currentItem = position
    }
}