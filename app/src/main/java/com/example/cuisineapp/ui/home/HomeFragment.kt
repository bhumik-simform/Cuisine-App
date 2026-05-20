package com.example.cuisineapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cuisineapp.R
import com.example.cuisineapp.data.DishRepository

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load dishes if they haven't been loaded yet
        DishRepository.loadDishes(requireContext())

        val homeRecyclerView: RecyclerView = view.findViewById(R.id.recycler_view_home)
        homeRecyclerView.layoutManager = LinearLayoutManager(context)
        homeRecyclerView.adapter = DishAdapter(DishRepository.getAllDishes()) { dishID ->
            print(dishID)
            
        }
    }
}