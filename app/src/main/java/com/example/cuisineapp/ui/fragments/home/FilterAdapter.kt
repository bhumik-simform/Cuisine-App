package com.example.cuisineapp.ui.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cuisineapp.R
import com.example.cuisineapp.databinding.ItemFiltersBinding
import com.google.android.material.chip.Chip

class FilterAdapter(
    private val dishCountriesList: List<String>,
    private val filterChipClicked: (Chip) -> Unit
) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterViewHolder {
        val binding = ItemFiltersBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FilterViewHolder,
        position: Int
    ) {
        val item = dishCountriesList.elementAt(position)
        holder.bindData(item)
    }

    override fun getItemCount() = dishCountriesList.count()

    inner class FilterViewHolder(val binding: ItemFiltersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(itemCountry: String) {
            binding.chipFilter.text = itemCountry
            binding.chipFilter.setOnClickListener {
                filterChipClicked(binding.chipFilter)
            }
        }
    }
}