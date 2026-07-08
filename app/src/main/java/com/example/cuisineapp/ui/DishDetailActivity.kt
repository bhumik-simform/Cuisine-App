package com.example.cuisineapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.cuisineapp.R
import com.example.cuisineapp.data.DishRepository
import com.example.cuisineapp.data.FavoriteManger
import com.example.cuisineapp.databinding.ActivityDishDetailBinding
import com.example.cuisineapp.model.Dish
import androidx.core.net.toUri

class  DishDetailActivity : AppCompatActivity() {

    private lateinit var favoriteManger: FavoriteManger
    private var mainDishId: String? = null
    private lateinit var mainDish: Dish
    private var isFavDish: Boolean = false

    private lateinit var binding: ActivityDishDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDishDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dish_detail)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainDishId = intent.getStringExtra("dish_id")
        mainDish = DishRepository.getDishById(mainDishId ?: "") ?: return

        favoriteManger = FavoriteManger(this)
        isFavDish = favoriteManger.isFavorite(mainDish.idMeal)

        bindData()
        setClickEventForBtns()
    }

    private fun bindData() {
        Glide.with(this).load(mainDish.strMealThumb)
            .placeholder(R.drawable.ic_placeholder_image).error(R.drawable.ic_error_image)
            .into(binding.toolbarDishDetail.ivDishImage)

        binding.toolbarDishDetail.titleToolbarDishDetail.title = mainDish.strMeal
        binding.tvDishName.text = mainDish.strMeal
        binding.tvDishCountry.text = mainDish.strCountry
        binding.tvDishCategory.text = mainDish.strCategory
        binding.tvDishRating.text = mainDish.rating
        binding.tvDishCookingTime.text = mainDish.cookingTime
        binding.tvInstruction.text = mainDish.strInstructions

        updateFavBtnUI()
    }

    private fun setClickEventForBtns() {
        binding.toolbarDishDetail.ivFavBtn.setOnClickListener {
            favoriteManger.toggleFavorite(mainDish.idMeal)
            isFavDish = favoriteManger.isFavorite(mainDish.idMeal)
            updateFavBtnUI()
        }

        binding.toolbarDishDetail.ivBackBtn.setOnClickListener {
            finish()
        }

        binding.toolbarDishDetail.titleToolbarDishDetail.setNavigationOnClickListener {
            finish()
        }

        binding.btnKnowMore.setOnClickListener {
            val intent = Intent(this, WebviewActivity::class.java)
            intent.putExtra("webUrl", mainDish.strSource)
            startActivity(intent)
        }

        binding.btnWatchTutorial.setOnClickListener {
            val tutorialUrl = mainDish.strYoutube
            val intent = Intent(Intent.ACTION_VIEW, tutorialUrl.toUri())

            startActivity(intent)
        }
    }

    private fun updateFavBtnUI() {
        binding.toolbarDishDetail.ivFavBtn.isSelected = isFavDish
        if (isFavDish) {
            binding.toolbarDishDetail.ivFavBtn.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            binding.toolbarDishDetail.ivFavBtn.setImageResource(R.drawable.ic_favorite_outlined)
        }
    }
}