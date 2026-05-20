package com.example.cuisineapp.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class FavoriteManger(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("food_app_prefs",Context.MODE_PRIVATE)

    companion object {
        private const val FAVORITES_KEY = "favorite_dish_ids"
    }

    fun getFavoritesIds(): Set<String> = prefs.getStringSet(FAVORITES_KEY,emptySet()) ?: emptySet()

    fun isFavorite(dishId: String): Boolean = getFavoritesIds().contains(dishId)

    fun toggleFavorite(dishId: String) {
        val currFavorites = getFavoritesIds().toMutableSet()

        if(currFavorites.contains(dishId)) {
            currFavorites.remove(dishId)
        } else {
            currFavorites.add(dishId)
        }

        prefs.edit { putStringSet(FAVORITES_KEY, currFavorites) }
    }
}