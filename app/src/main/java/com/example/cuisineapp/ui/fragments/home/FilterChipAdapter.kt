package com.example.cuisineapp.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cuisineapp.databinding.ItemFiltersBinding
import com.google.android.material.chip.Chip

class FilterChipAdapter(
    private val dishCountriesList: List<String>,
    private val filterChipClicked: (Chip) -> Unit
) : RecyclerView.Adapter<FilterChipAdapter.FilterChipViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterChipViewHolder {
        val binding = ItemFiltersBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FilterChipViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FilterChipViewHolder,
        position: Int
    ) {
        val item = dishCountriesList.elementAt(position)
        holder.bindData(item)
    }

    override fun getItemCount() = dishCountriesList.count()

    inner class FilterChipViewHolder(val binding: ItemFiltersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(itemCountry: String) {
            binding.chipFilter.text = itemCountry
            binding.chipFilter.setOnClickListener {
                filterChipClicked(binding.chipFilter)
            }
        }
    }
}