package com.example.cuisineapp.ui.share

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cuisineapp.R
import com.example.cuisineapp.data.FavoriteManger
import com.example.cuisineapp.databinding.ActivityDishDetailBinding
import com.example.cuisineapp.databinding.ItemDishBinding
import com.example.cuisineapp.model.Dish
import com.google.android.material.imageview.ShapeableImageView
class DishListAdapter(
    private val onItemClicked: (Dish) -> Unit,
    private val onFavoriteImgClicked: (Dish) -> Unit
) : ListAdapter<Dish, DishListAdapter.DishViewHolder>(DishDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DishViewHolder {
        val binding = ItemDishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DishViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: DishViewHolder,
        position: Int
    ) {
        val dish = getItem(position)
        holder.bindData(dish)
    }

    inner class DishViewHolder(val binding: ItemDishBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(dish: Dish) {
            Glide.with(itemView.context).load(dish.strMealThumb)
                .placeholder(R.drawable.ic_placeholder_image)
                .error(R.drawable.ic_error_image)
                .into(binding.ivDishImage)

            binding.tvDishName.text = dish.strMeal
            binding.tvDishArea.text = dish.strArea
            binding.rbDishRating.rating = dish.rating.toFloat()
            binding.tvSpiceLevel.text = dish.spiceLevel

            val favoriteManger = FavoriteManger(itemView.context)
            val isFavoriteItem = favoriteManger.isFavorite(dish.idMeal)
            binding.ivFavoriteBtn.isSelected = isFavoriteItem

            if (isFavoriteItem) {
                binding.ivFavoriteBtn.setImageResource(R.drawable.ic_favorite_filled)
            } else {
                binding.ivFavoriteBtn.setImageResource(R.drawable.ic_favorite_outlined)
            }

            itemView.setOnClickListener { onItemClicked(dish) }
            binding.ivFavoriteBtn.setOnClickListener { onFavoriteImgClicked(dish) }
        }
    }

    class DishDiffCallback : DiffUtil.ItemCallback<Dish>() {
        override fun areItemsTheSame(
            p0: Dish,
            p1: Dish
        ): Boolean = (p0.idMeal == p1.idMeal)

        override fun areContentsTheSame(
            p0: Dish,
            p1: Dish
        ): Boolean = (p0 == p1)
    }
}