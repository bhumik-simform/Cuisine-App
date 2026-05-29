package com.example.cuisineapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cuisineapp.DishDetailActivity
import com.example.cuisineapp.FilterAdapter
import com.example.cuisineapp.HomeViewModel
import com.example.cuisineapp.data.DishRepository
import com.example.cuisineapp.data.FavoriteManger
import com.example.cuisineapp.databinding.FragmentHomeBinding
import com.example.cuisineapp.model.Dish

class HomeFragment : Fragment() {

    private lateinit var favoriteManger: FavoriteManger
    private lateinit var dishAdapter: DishListAdapter

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupRecyclerView()
        setupObserver()
        setupFilterCountries()
    }

    override fun onResume() {
        super.onResume()
        dishAdapter.notifyDataSetChanged()
    }

    private fun initView() {
        favoriteManger = FavoriteManger(requireContext())
    }

    private fun setupRecyclerView() {

        val onDishClick: (Dish) -> Unit = { clickedDish ->
           val intent = Intent(requireContext(), DishDetailActivity::class.java)
            intent.putExtra("dish_id",clickedDish.idMeal)
            startActivity(intent)
        }

        val onFavoriteClick: (Dish) -> Unit = { clickedDish ->
            favoriteManger.toggleFavorite(clickedDish.idMeal)

            val index = dishAdapter.currentList.indexOf(clickedDish)
            if(index != -1) {
                dishAdapter.notifyItemChanged(index)
            }
        }

        dishAdapter = DishListAdapter(onDishClick,onFavoriteClick)
        binding.recyclerViewHomeDish.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewHomeDish.adapter = dishAdapter

        val availableDishes = viewModel.displayedDishes.value
        dishAdapter.submitList(availableDishes)
    }

    private fun setupFilterCountries() {
        binding.appbarHome.recyclerViewFilters.adapter = FilterAdapter(DishRepository.getAllCountries()) { clickedChip ->
            clickedChip.isSelected = !clickedChip.isSelected
            viewModel.toggleFilter(clickedChip.text.toString())
        }
    }

    private fun setupObserver() {
        viewModel.displayedDishes.observe(viewLifecycleOwner) { dishAdapter.submitList(it) }
    }
}