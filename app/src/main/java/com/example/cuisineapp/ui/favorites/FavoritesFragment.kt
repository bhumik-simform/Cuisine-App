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
import com.example.cuisineapp.data.FavoriteManger
import com.example.cuisineapp.model.Dish
import com.example.cuisineapp.ui.details.DetailsFragment
import com.example.cuisineapp.ui.home.DishListAdapter

class FavoritesFragment : Fragment() {

    private lateinit var favoriteManger: FavoriteManger
    private lateinit var adapter: DishListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteManger = FavoriteManger(requireContext())
        val favoritesRecyclerView: RecyclerView = view.findViewById(R.id.recycler_view_favorites)
        val emptyView: View = view.findViewById(R.id.view_empty_state)

        val onDishClicked: (Dish) -> Unit = { clickedDish ->
            val detailsFragment = DetailsFragment()

            val bundle = Bundle()
            bundle.putString("DISH_ID", clickedDish.idMeal)

            detailsFragment.arguments = bundle

            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.detail_fragment_Container, detailsFragment)
                .addToBackStack(null)
                .commit()
        }

        val onFavoriteIconClick: (Dish) -> Unit = { clickedDish ->
            favoriteManger.toggleFavorite(clickedDish.idMeal)

            loadFavoriteDishes(favoritesRecyclerView,emptyView)
        }

        adapter = DishListAdapter(onDishClicked, onFavoriteIconClick)

        favoritesRecyclerView.layoutManager = LinearLayoutManager(context)
        favoritesRecyclerView.adapter = adapter

        loadFavoriteDishes(favoritesRecyclerView,emptyView)
    }

    private fun loadFavoriteDishes(recyclerView: RecyclerView, emptyView: View) {
        val allFavoriteIds = favoriteManger.getFavoritesIds()

        val favoriteDishes = DishRepository.getAllDishes().filter { allFavoriteIds.contains(it.idMeal) }

        if (favoriteDishes.isEmpty()) {
          emptyView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        }

        adapter.submitList(favoriteDishes)
    }
}