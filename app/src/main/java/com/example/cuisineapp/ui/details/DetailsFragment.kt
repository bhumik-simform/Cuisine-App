package com.example.cuisineapp.ui.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.cuisineapp.R
import com.example.cuisineapp.data.DishRepository
import com.example.cuisineapp.data.FavoriteManger
import com.example.cuisineapp.model.Dish

class DetailsFragment : Fragment() {

    private lateinit var favoriteManger: FavoriteManger
    private var mainDishId: String? = null
    private lateinit var mainDish: Dish
    private  var isFavDish: Boolean = false
    lateinit var dishImageView: ImageView
    lateinit var dishNameTextView: TextView
    lateinit var dishCountryTextView: TextView
    lateinit var dishCategoryTextView: TextView
    lateinit var dishRatingTextView: TextView
    lateinit var dishTimeTextView: TextView
    lateinit var dishInstructionTextView: TextView
    lateinit var backBtn: ImageView
    lateinit var favoriteBtn: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainDishId = requireArguments().getString("DISH_ID")
        mainDish = DishRepository.getDishById(mainDishId ?: "") ?: return

        favoriteManger = FavoriteManger(requireContext())
        isFavDish = favoriteManger.isFavorite(mainDish.idMeal)

        dishImageView = view.findViewById(R.id.iv_dish_image)
        dishNameTextView = view.findViewById(R.id.tv_dish_name)
        dishCountryTextView = view.findViewById(R.id.tv_dish_country)
        dishCategoryTextView = view.findViewById(R.id.tv_dish_category)
        dishRatingTextView = view.findViewById(R.id.tv_dish_rating)
        dishTimeTextView = view.findViewById(R.id.tv_dish_time)
        dishInstructionTextView = view.findViewById(R.id.tv_instruction)

//        Log.e("checkMeow","${R.id.iv_fav_icon}")
        backBtn = view.findViewById(R.id.iv_back_btn)
       favoriteBtn = view.findViewById(R.id.bhumik)

        bindData()
        setClickEventForBtn()
    }

    private fun bindData() {
        Glide.with(requireContext()).load(mainDish.strMealThumb)
            .placeholder(R.drawable.ic_placeholder_image).error(R.drawable.ic_error_image)
            .into(dishImageView)

        dishNameTextView.text = mainDish.strMeal
        dishCountryTextView.text = mainDish.strCountry
        dishCategoryTextView.text = mainDish.strCategory
        dishRatingTextView.text = mainDish.rating
        dishTimeTextView.text = mainDish.cookingTime
        dishInstructionTextView.text = mainDish.strInstructions

        favoriteBtn.isSelected = isFavDish

        if (isFavDish) {
            favoriteBtn.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            favoriteBtn.setImageResource(R.drawable.ic_favorite_outlined)
        }
    }

    private fun setClickEventForBtn() {
        favoriteBtn.setOnClickListener {
            favoriteManger.toggleFavorite(mainDish.idMeal)
            if (isFavDish) {
                favoriteBtn.setImageResource(R.drawable.ic_favorite_filled)
            } else {
                favoriteBtn.setImageResource(R.drawable.ic_favorite_outlined)
            }
        }

        backBtn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}