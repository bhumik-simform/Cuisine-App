package com.example.cuisineapp.ui.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cuisineapp.ui.favorites.FavoritesFragment
import com.example.cuisineapp.ui.home.HomeFragment
import com.example.cuisineapp.ui.settings.SettingsFragment

class MainPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(p0: Int): Fragment {
        return when (p0) {
            0 -> HomeFragment()
            1 -> FavoritesFragment()
            2 -> SettingsFragment()
            else -> HomeFragment()
        }
    }

    override fun getItemCount() = 3
}