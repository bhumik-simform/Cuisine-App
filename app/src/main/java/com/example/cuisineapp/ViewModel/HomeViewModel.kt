package com.example.cuisineapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cuisineapp.data.DishRepository
import com.example.cuisineapp.model.Dish

class HomeViewModel: ViewModel() {

    private val _displayedDishes= MutableLiveData<List<Dish>>()

    val displayedDishes: LiveData<List<Dish>>
        get() = _displayedDishes

    private var selectedCountries = mutableSetOf<String>()

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

    private fun filterList() {
        if(selectedCountries.isEmpty()) {
            _displayedDishes.value = DishRepository.getAllDishes()
        } else {
            _displayedDishes.value = DishRepository.getAllDishes().filter {
                selectedCountries.contains(it.strCountry)
            }
        }
    }
}