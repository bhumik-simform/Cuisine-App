package com.example.cuisineapp.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cuisineapp.model.Dish

class DishListAdapter: ListAdapter<Dish, DishListAdapter.DishViewHolder>(DishDiffCallback()) {
    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): DishViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        p0: DishViewHolder,
        p1: Int
    ) {
        TODO("Not yet implemented")
    }

    class DishViewHolder(view: View): RecyclerView.ViewHolder(view) {

    }

    class DishDiffCallback: DiffUtil.ItemCallback<Dish>() {
        override fun areItemsTheSame(
            p0: Dish,
            p1: Dish
        ): Boolean {

        }

        override fun areContentsTheSame(
            p0: Dish,
            p1: Dish
        ): Boolean {

        }

    }
}