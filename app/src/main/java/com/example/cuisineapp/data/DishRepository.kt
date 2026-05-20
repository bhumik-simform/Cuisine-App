package com.example.cuisineapp.data

import android.content.Context
import android.widget.Toast
import com.example.cuisineapp.model.Dish
import com.google.gson.Gson
import java.io.InputStreamReader

object DishRepository {

    private var allDishes: List<Dish> = emptyList()

    fun loadDishes(context: Context) {
        if (allDishes.isNotEmpty()) return
        try {
            val inputStream = context.assets.open("dishes.json")
            val reader = InputStreamReader(inputStream)

            val gson = Gson()
            val response: DishResponse = gson.fromJson(reader, DishResponse::class.java)

            allDishes = response.meals
            reader.close()
        } catch (_: Exception) {
            Toast.makeText(context, "Error to load Data", Toast.LENGTH_SHORT).show()
        }
    }

    fun getAllDishes(): List<Dish> = allDishes

    fun getDishById(id: String): Dish? = allDishes.find { it.idMeal == id }
}