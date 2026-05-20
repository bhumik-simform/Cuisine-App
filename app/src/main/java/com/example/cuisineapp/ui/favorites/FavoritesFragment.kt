package com.example.cuisineapp.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cuisineapp.R
import com.example.cuisineapp.data.DishRepository

class FavoritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favoritesRecyclerView: RecyclerView = view.findViewById(R.id.recycler_view_favorites)
        favoritesRecyclerView.layoutManager = LinearLayoutManager(context)
        favoritesRecyclerView.adapter = FavoritesAdapter(DishRepository.getAllDishes()) { dishID ->
            print(dishID)
        }
    }
}