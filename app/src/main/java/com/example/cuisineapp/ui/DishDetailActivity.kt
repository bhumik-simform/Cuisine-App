package com.example.cuisineapp.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.cuisineapp.R
import com.example.cuisineapp.data.DishRepository
import com.example.cuisineapp.data.FavoriteManger
import com.example.cuisineapp.model.Dish

class  DishDetailActivity : AppCompatActivity() {

    private lateinit var favoriteManger: FavoriteManger
    private var mainDishId: String? = null
    private lateinit var mainDish: Dish
    private var isFavDish: Boolean = false
    private lateinit var dishImageView: ImageView
    private lateinit var dishNameTextView: TextView
    private lateinit var dishCountryTextView: TextView
    private lateinit var dishCategoryTextView: TextView
    private lateinit var dishRatingTextView: TextView
    private lateinit var dishTimeTextView: TextView
    private lateinit var dishInstructionTextView: TextView
    private lateinit var backBtn: ImageView
    private lateinit var favoriteBtn: ImageView

    private lateinit var dishDetailToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dish_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dish_detail)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainDishId = intent.getStringExtra("dish_id")
        mainDish = DishRepository.getDishById(mainDishId ?: "") ?: return

        favoriteManger = FavoriteManger(this)
        isFavDish = favoriteManger.isFavorite(mainDish.idMeal)

        dishImageView = findViewById(R.id.iv_dish_image)
        dishDetailToolbar = findViewById(R.id.toolbar_dish_detail)
        dishNameTextView = findViewById(R.id.tv_dish_name)
        dishCountryTextView = findViewById(R.id.tv_dish_country)
        dishCategoryTextView = findViewById(R.id.tv_dish_category)
        dishRatingTextView = findViewById(R.id.tv_dish_rating)
        dishTimeTextView = findViewById(R.id.tv_dish_time)
        dishInstructionTextView = findViewById(R.id.tv_instruction)

        backBtn = findViewById(R.id.iv_back_btn)
        favoriteBtn = findViewById(R.id.iv_fav_btn)

        bindData()
        setClickEventForBtn()
    }

    private fun bindData() {
        Glide.with(this).load(mainDish.strMealThumb)
            .placeholder(R.drawable.ic_placeholder_image).error(R.drawable.ic_error_image)
            .into(dishImageView)

        dishDetailToolbar.title = mainDish.strMeal
        dishNameTextView.text = mainDish.strMeal
        dishCountryTextView.text = mainDish.strCountry
        dishCategoryTextView.text = mainDish.strCategory
        dishRatingTextView.text = mainDish.rating
        dishTimeTextView.text = mainDish.cookingTime
        dishInstructionTextView.text = mainDish.strInstructions

        updateFavBtnUI()
    }

    private fun setClickEventForBtn() {
        favoriteBtn.setOnClickListener {
            favoriteManger.toggleFavorite(mainDish.idMeal)
            isFavDish = favoriteManger.isFavorite(mainDish.idMeal)
            updateFavBtnUI()
        }

        backBtn.setOnClickListener {
            finish()
        }
    }

    private fun updateFavBtnUI() {
        favoriteBtn.isSelected = isFavDish
        if (isFavDish) {
            favoriteBtn.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            favoriteBtn.setImageResource(R.drawable.ic_favorite_outlined)
        }
    }
}