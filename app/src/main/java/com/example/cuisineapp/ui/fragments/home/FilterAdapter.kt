package com.example.cuisineapp.ui.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cuisineapp.R
import com.google.android.material.chip.Chip

class FilterAdapter(
    private val dishCountriesList: List<String>,
    private val filterChipClicked: (Chip) -> Unit
): RecyclerView.Adapter<FilterViewHolder>() {
    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): FilterViewHolder {
        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.item_filters,p0,false)
        return FilterViewHolder(itemView)
    }

    override fun onBindViewHolder(
        p0: FilterViewHolder,
        p1: Int
    ) {
        val item = dishCountriesList.elementAt(p1)
        p0.bindData(item)
    }

    override fun getItemCount() = dishCountriesList.count()

    inner class FilterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val filterChip: Chip = itemView.findViewById(R.id.chip_filter)

        fun bindData(itemCountry: String) {
            filterChip.text = itemCountry
           filterChip.setOnClickListener {
               filterChipClicked(filterChip)
           }
        }
    }
}