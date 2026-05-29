package com.example.cuisineapp.ui.fragments.favorites

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
import com.example.cuisineapp.ui.DishDetailActivity
import com.example.cuisineapp.R
import com.example.cuisineapp.data.DishRepository
import com.example.cuisineapp.data.FavoriteManger
import com.example.cuisineapp.model.Dish
import com.example.cuisineapp.ui.share.DishListAdapter
class FavoritesFragment : Fragment() {

    private lateinit var favoriteManger: FavoriteManger
    private lateinit var adapter: DishListAdapter

    private lateinit var favoritesRecyclerView: RecyclerView
    private lateinit var emptyView: View

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
        favoritesRecyclerView = view.findViewById(R.id.recycler_view_favorites)
        emptyView = view.findViewById(R.id.view_empty_state)

        val onDishClicked: (Dish) -> Unit = { clickedDish ->
            val intent = Intent(requireContext(), DishDetailActivity::class.java)
            intent.putExtra("dish_id",clickedDish.idMeal)
            startActivity(intent)
        }

        val onFavoriteIconClick: (Dish) -> Unit = { clickedDish ->
            favoriteManger.toggleFavorite(clickedDish.idMeal)

            loadFavoriteDishes(favoritesRecyclerView,emptyView)
        }

        adapter = DishListAdapter(onDishClicked, onFavoriteIconClick)

        favoritesRecyclerView.layoutManager = LinearLayoutManager(context)
        favoritesRecyclerView.adapter = adapter

        loadFavoriteDishes(favoritesRecyclerView,emptyView)
        adapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
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