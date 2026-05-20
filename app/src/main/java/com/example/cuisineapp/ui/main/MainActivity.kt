package com.example.cuisineapp.ui.main

import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.cuisineapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    lateinit var mainViewPager: ViewPager2
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

        setupViewPager()
        setupBottomNav()
        connectPagerBottomNav()
        if (savedInstanceState == null) {
            mainViewPager.currentItem = 0
        }
        //Handle BackButton Gesture
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if(mainViewPager.currentItem != 0) {
                        mainViewPager.setCurrentItem(0, false)
                    } else {
                        finish()
                    }
                }
            }
        )
    }

    private fun setupViewPager() {
        mainViewPager = findViewById(R.id.view_pager_main)
        mainViewPager.adapter = MainPagerAdapter(this)

    }

    private fun setupBottomNav() {
        mainBottomNavigation = findViewById(R.id.bottom_navigation_main)
        mainBottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> mainViewPager.setCurrentItem(0,false)
                R.id.nav_favourites -> mainViewPager.setCurrentItem(1, false)
                R.id.nav_settings -> mainViewPager.setCurrentItem(2, false)
            }
            true
        }

    }

    private fun connectPagerBottomNav() {
        mainViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mainBottomNavigation.selectedItemId = when (position) {
                    0 -> R.id.nav_home
                    1 -> R.id.nav_favourites
                    2 -> R.id.nav_settings
                    else -> R.id.nav_home
                }
            }
        }
        )
    }

}