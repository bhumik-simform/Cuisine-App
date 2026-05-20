package com.example.cuisineapp.model

data class Dish(
    val idMeal: String,
    val strMeal: String,
    val strCategory: String,
    val strArea: String,
    val strInstructions: String,
    val strMealThumb: String, // The image URL
    val strYoutube: String,

    val rating: Double,
    val cookingTime: String,
    val spiceLevel: String,

    val strIngredient1: String,
    val strMeasure1: String,
)
