package com.example.cuisineapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.cuisineapp.R
import com.example.cuisineapp.data.DishRepository
import com.example.cuisineapp.ui.details.DetailsFragment
import com.example.cuisineapp.ui.favorites.FavoritesFragment
import com.example.cuisineapp.ui.home.HomeFragment
import com.example.cuisineapp.ui.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    lateinit var mainBottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        DishRepository.loadDishes(this)

        setupBottomNav()
        if (savedInstanceState == null) {
            inflateFragment(HomeFragment())
        }

        supportFragmentManager.addOnBackStackChangedListener {
            showBottomNav()
        }
    }

    private fun setupBottomNav() {
        mainBottomNavigation = findViewById(R.id.bottom_navigation_main)
        mainBottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> inflateFragment(HomeFragment())
                R.id.nav_favourites -> inflateFragment(FavoritesFragment())
                R.id.nav_settings -> inflateFragment(SettingsFragment())
            }
            true
        }
    }

    private fun inflateFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_main, fragment)
            if (fragment !is HomeFragment) {
                addToBackStack("home_fragment")
            }
            commit()
        }
        showBottomNav(fragment)
    }

    private fun showBottomNav(fragment: Fragment? = null) {
        val currentFragment = fragment ?: supportFragmentManager.findFragmentById(R.id.fragment_container_main)
        if (currentFragment is DetailsFragment) {
            mainBottomNavigation.visibility = View.GONE
        } else {
            mainBottomNavigation.visibility = View.VISIBLE
        }
    }
}
