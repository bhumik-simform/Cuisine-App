package com.example.cuisineapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cuisineapp.data.DishRepository
import com.example.cuisineapp.model.Dish
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
class HomeViewModel : ViewModel() {

    private val _displayedDishes = MutableLiveData<List<Dish>>()

    val displayedDishes: LiveData<List<Dish>>
        get() = _displayedDishes

    private var selectedCountries = mutableSetOf<String>()

    private val _searchQuery = MutableStateFlow("")


    init {
        _displayedDishes.value = DishRepository.getAllDishes()

        observeSearchQuery()
    }

    fun toggleFilter(country: String) {
        if (selectedCountries.contains(country)) {
            selectedCountries.remove(country)
        } else {
            selectedCountries.add(country)
        }
        filterList()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query.lowercase()
    }

    private fun filterList() {
        var filteredList = DishRepository.getAllDishes()

        if (selectedCountries.isNotEmpty()) {
            filteredList = filteredList.filter {
                selectedCountries.contains(it.strCountry)
            }
        }

        if (_searchQuery.value.isNotEmpty()) {
            filteredList = filteredList.filter {
                it.strMeal.lowercase().contains(_searchQuery.value)
            }
        }
        _displayedDishes.value = filteredList
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery.debounce(500).distinctUntilChanged().collect {
                Log.d("SEARCH",it)
                filterList()
            }
        }
    }
}