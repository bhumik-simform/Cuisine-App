package com.example.cuisineapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cuisineapp.R
import com.example.cuisineapp.data.DishRepository
import com.example.cuisineapp.data.FavoriteManger
import com.example.cuisineapp.model.Dish

class HomeFragment : Fragment() {

    private lateinit var favoriteManger: FavoriteManger
    private lateinit var adapter: DishListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteManger = FavoriteManger(requireContext())

        val onDishClick: (Dish) -> Unit = { clickedDish ->
            val action =  HomeFragmentDirections.actionHomeToDetails(
                clickedDish.idMeal
            )
            findNavController().navigate(action)
        }

        val onFavoriteClick: (Dish) -> Unit = { clickedDish ->
            favoriteManger.toggleFavorite(clickedDish.idMeal)

            val index = adapter.currentList.indexOf(clickedDish)
            if(index != -1) {
                adapter.notifyItemChanged(index)
            }
        }

        adapter = DishListAdapter(onDishClick,onFavoriteClick)


        val homeRecyclerView: RecyclerView = view.findViewById(R.id.recycler_view_home)
        homeRecyclerView.layoutManager = LinearLayoutManager(context)
        homeRecyclerView.adapter = adapter

        val allDishes = DishRepository.getAllDishes()
        adapter.submitList(allDishes)
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}