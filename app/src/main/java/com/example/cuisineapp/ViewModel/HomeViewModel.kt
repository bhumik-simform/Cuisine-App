package com.example.cuisineapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cuisineapp.data.DishRepository
import com.example.cuisineapp.model.Dish
import java.time.temporal.TemporalQuery

class HomeViewModel : ViewModel() {

    private val _displayedDishes = MutableLiveData<List<Dish>>()

    val displayedDishes: LiveData<List<Dish>>
        get() = _displayedDishes

    private var selectedCountries = mutableSetOf<String>()

    private var searchQuery = ""

    init {
        _displayedDishes.value = DishRepository.getAllDishes()
    }

    fun toggleFilter(country: String) {
        if (selectedCountries.contains(country)) {
            selectedCountries.remove(country)
        } else {
            selectedCountries.add(country)
        }
        filterList()
    }

    fun updateSearchQuery(query: String?) {
        searchQuery = query?.lowercase() ?: ""
        filterList()
    }

    private fun filterList() {
        var filteredList = DishRepository.getAllDishes()

        if (selectedCountries.isNotEmpty()) {
            filteredList = filteredList.filter {
                selectedCountries.contains(it.strCountry)
            }
        }

        if (searchQuery.isNotEmpty()) {
            filteredList = filteredList.filter {
                it.strMeal.lowercase().contains(searchQuery)
            }
        }
        _displayedDishes.value = filteredList
    }
}