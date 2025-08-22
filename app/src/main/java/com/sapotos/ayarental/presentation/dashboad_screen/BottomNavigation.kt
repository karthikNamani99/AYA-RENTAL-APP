// package com.sapotos.ayarental.presentation.dashboad_screen
package com.sapotos.ayarental.presentation.dashboad_screen

import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseActivity
import com.sapotos.ayarental.databinding.ActivityWithBottomNavBinding
import com.sapotos.ayarental.presentation.fragments.hostCarFragment.HostCarFragment
import com.sapotos.ayarental.presentation.tabs.BookingFragment
import com.sapotos.ayarental.presentation.tabs.HomeFragment
import com.sapotos.ayarental.presentation.tabs.ProfileFragment

class BottomNavigation : BaseActivity() {

    private lateinit var binding: ActivityWithBottomNavBinding
    private lateinit var bottom: BottomNavigationView

    private val fragments by lazy {
        mapOf(
            R.id.nav_home to HomeFragment(),
            R.id.nav_booking to BookingFragment(),
            R.id.nav_profile to ProfileFragment(),
            R.id.nav_host to HostCarFragment()
        )
    }
    private var currentId = R.id.nav_home

    override fun getLayoutResourceId(): View {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_with_bottom_nav)
        bottom = binding.bottomNav
        setupFragments()
        setupBottomNav()
        return binding.root
    }

    private fun setupFragments() {
        val fm = supportFragmentManager
        fragments.forEach { (id, f) ->
            fm.beginTransaction().add(R.id.fragmentContainer, f, id.toString()).hide(f).commitNow()
        }
        fm.beginTransaction().show(fragments[currentId]!!).commitNow()
        bottom.selectedItemId = currentId
    }

    private fun setupBottomNav() {
        bottom.setOnItemSelectedListener { item ->
            if (item.itemId == currentId) return@setOnItemSelectedListener true
            val fm = supportFragmentManager
            fm.beginTransaction()
                .hide(fragments[currentId]!!)
                .show(fragments[item.itemId]!!)
                .commit()
            currentId = item.itemId
            true
        }
    }
}
