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
import com.example.cuisineapp.model.Dish
import com.google.android.material.imageview.ShapeableImageView

class DishListAdapter(
    private val onItemClicked: (Dish) -> Unit,
    private val onFavoriteImgClicked: (Dish) -> Unit
) : ListAdapter<Dish, DishListAdapter.DishViewHolder>(DishDiffCallback()) {
    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): DishViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_dish, p0, false)
        return DishViewHolder(view)
    }

    override fun onBindViewHolder(
        p0: DishViewHolder,
        p1: Int
    ) {
        val dish = getItem(p1)
        p0.bindData(dish)
    }

    inner class DishViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val dishImageView: ShapeableImageView = view.findViewById(R.id.iv_dish_image)
        private val dishNameTextView: TextView = view.findViewById(R.id.tv_dish_name)
        private val dishAreaTextView: TextView = view.findViewById(R.id.tv_dish_area)
        private val dishRatingRatingBar: RatingBar = view.findViewById(R.id.rb_dish_rating)
        private val dishSpiceLevelTextView: TextView = view.findViewById(R.id.tv_spice_level)
        private val dishFavoriteImgBtn: ImageView = view.findViewById(R.id.iv_favoriteBtn)
        fun bindData(dish: Dish) {
            Glide.with(itemView.context).load(dish.strMealThumb)
                .placeholder(R.drawable.ic_placeholder_image)
                .error(R.drawable.ic_error_image)
                .into(dishImageView)

            dishNameTextView.text = dish.strMeal
            dishAreaTextView.text = dish.strArea
            dishRatingRatingBar.rating = dish.rating.toFloat()
            dishSpiceLevelTextView.text = dish.spiceLevel

            val favoriteManger = FavoriteManger(itemView.context)
            val isFavoriteItem = favoriteManger.isFavorite(dish.idMeal)
            dishFavoriteImgBtn.isSelected = isFavoriteItem

            if(isFavoriteItem) {
                dishFavoriteImgBtn.setImageResource(R.drawable.ic_favorite_filled)
            } else {
                dishFavoriteImgBtn.setImageResource(R.drawable.ic_favorite_outlined)
            }

            itemView.setOnClickListener { onItemClicked(dish) }
            dishFavoriteImgBtn.setOnClickListener { onFavoriteImgClicked(dish) }
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