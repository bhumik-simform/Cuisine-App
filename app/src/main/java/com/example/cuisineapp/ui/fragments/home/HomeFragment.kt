package com.example.cuisineapp.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cuisineapp.R
import com.example.cuisineapp.ui.DishDetailActivity
import com.example.cuisineapp.ViewModel.HomeViewModel
import com.example.cuisineapp.data.DishRepository
import com.example.cuisineapp.data.FavoriteManger
import com.example.cuisineapp.databinding.FragmentHomeBinding
import com.example.cuisineapp.model.Dish
import com.example.cuisineapp.ui.share.DishListAdapter

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
        setupSearchMenu()
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
            intent.putExtra("dish_id", clickedDish.idMeal)
            startActivity(intent)
        }

        val onFavoriteClick: (Dish) -> Unit = { clickedDish ->
            favoriteManger.toggleFavorite(clickedDish.idMeal)

            val index = dishAdapter.currentList.indexOf(clickedDish)
            if (index != -1) {
                dishAdapter.notifyItemChanged(index)
            }
        }

        dishAdapter = DishListAdapter(onDishClick, onFavoriteClick)
        binding.recyclerViewHomeDish.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewHomeDish.adapter = dishAdapter

        val availableDishes = viewModel.displayedDishes.value
        dishAdapter.submitList(availableDishes)
    }

    private fun setupFilterCountries() {
        binding.appbarHome.recyclerViewFilters.adapter =
            FilterChipAdapter(DishRepository.getAllCountries()) { clickedChip ->
                clickedChip.isSelected = !clickedChip.isSelected
                viewModel.toggleFilter(clickedChip.text.toString())
            }

        binding.appbarHome.recyclerViewFilters.addItemDecoration(FilterChipDecor())
    }

    private fun setupObserver() {
        viewModel.displayedDishes.observe(viewLifecycleOwner) { dishAdapter.submitList(it) }
    }

    private fun setupSearchMenu() {

        binding.appbarHome.toolbarHome.inflateMenu(R.menu.menu_home)
        val searchItem = binding.appbarHome.toolbarHome.menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.queryHint = "Search here..."

        searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.updateSearchQuery(p0)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                viewModel.updateSearchQuery(p0)
                Log.d("Meow","Query: $p0")
//                Log.d("Meow","${searchView.hasFocus()}")
                return false
            }

        })

        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            Log.d("Meow","Focus mode: ${searchView.hasFocus()}")
        }

        searchView.setOnCloseListener {
            Log.d("Meow","close button is clicked")
            false
        }
    }
}