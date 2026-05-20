package com.example.cuisineapp.ui.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cuisineapp.R
import com.example.cuisineapp.model.Dish
import com.example.cuisineapp.data.FavoriteManger
import com.google.android.material.imageview.ShapeableImageView

class FavoritesAdapter(
    private val dishList: List<Dish>,
    private val onItemClick: (String) -> Unit
) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): FavoritesViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_dish, p0, false)
        return FavoritesViewHolder(view)
    }

    override fun onBindViewHolder(
        p0: FavoritesViewHolder,
        p1: Int
    ) {
        val dish = dishList.elementAt(p1)
        val favoriteManger = FavoriteManger(p0.itemView.context)
        if (favoriteManger.isFavorite(dish.idMeal)) {
            p0.bindData(dish)
        }
    }

    override fun getItemCount() = dishList.count()

    inner class FavoritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val dishImageView: ShapeableImageView = view.findViewById(R.id.iv_dish_image)
        val dishNameTextView: TextView = view.findViewById(R.id.tv_dish_name)
        val dishAreaTextView: TextView = view.findViewById(R.id.tv_dish_area)
        val dishRatingRatingBar: RatingBar = view.findViewById(R.id.rb_dish_rating)
        val dishSpiceLevelTextView: TextView = view.findViewById(R.id.tv_spice_level)
        val dishFavoriteImgBtn: ImageButton = view.findViewById(R.id.imgBtn_favourite)

        @SuppressLint("ResourceAsColor")
        fun bindData(dish: Dish) {
            Glide.with(itemView.context).load(dish.strMealThumb)
                .placeholder(R.drawable.ic_placeholder_image).error(R.drawable.error_image)
                .into(dishImageView)
            dishNameTextView.text = dish.strMeal
            dishAreaTextView.text = dish.strArea
            dishRatingRatingBar.rating = dish.rating.toFloat()
            dishSpiceLevelTextView.text = dish.spiceLevel

            val favoriteManger = FavoriteManger(itemView.context)
            dishFavoriteImgBtn.isActivated = favoriteManger.isFavorite(dish.idMeal)
            dishFavoriteImgBtn.setImageResource(R.drawable.ic_favorite_filled)
            dishFavoriteImgBtn.setOnClickListener {
                favoriteManger.toggleFavorite(dish.idMeal)
                notifyItemChanged(bindingAdapterPosition)
            }
        }
    }
}
