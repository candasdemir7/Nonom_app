package com.mustafacandasdemir.candasdemir_hw2

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mustafacandasdemir.candasdemir_hw2.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding
    private var imgCrop = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Resim yükleme
        val imageName = intent.getStringExtra("img")!!.substringBefore(".")
        val resourceId = resources.getIdentifier(imageName, "drawable", packageName)
        Glide.with(this).load(resourceId).into(binding.itemImg)

        // Metinleri yerleştir
        binding.tittle.text = intent.getStringExtra("name")
        binding.stepData.text = intent.getStringExtra("details")

        // Malzemeleri ekle
        val ingredients = intent.getStringExtra("ingredients")?.split("\n") ?: listOf()
        binding.ingData.text = ""
        for (ingredient in ingredients) {
            binding.ingData.append("\uD83D\uDD2A $ingredient\n")
        }

        // Sekme geçişleri
        binding.step.setOnClickListener { toggleTab(true) }
        binding.ing.setOnClickListener { toggleTab(false) }

        // Görsel büyütme/küçültme
        binding.fullScreen.setOnClickListener {
            if (imgCrop) {
                binding.itemImg.scaleType = ImageView.ScaleType.FIT_CENTER
                binding.fullScreen.setColorFilter(Color.BLACK)
                binding.shade.visibility = View.GONE
            } else {
                binding.itemImg.scaleType = ImageView.ScaleType.CENTER_CROP
                binding.fullScreen.clearColorFilter()
                binding.shade.visibility = View.VISIBLE
            }
            imgCrop = !imgCrop
        }

        // Geri dönme butonu
        binding.backBtn.setOnClickListener { finish() }
    }

    private fun toggleTab(isStepSelected: Boolean) {
        if (isStepSelected) {
            // Adımlar (Steps) butonu turuncu, Malzemeler (Ingredients) butonu yeşil
            binding.step.setBackgroundColor(getColor(R.color.TextView)) // Turuncu
            binding.step.setTextColor(getColor(R.color.white))       // Beyaz yazı

            binding.ing.setBackgroundColor(getColor(R.color.green))  // Yeşil
            binding.ing.setTextColor(getColor(R.color.white))        // Beyaz yazı

            // Görünürlük ayarları
            binding.stepScroll.visibility = View.VISIBLE
            binding.ingScroll.visibility = View.GONE
        } else {
            // Malzemeler (Ingredients) butonu turuncu, Adımlar (Steps) butonu yeşil
            binding.ing.setBackgroundColor(getColor(R.color.TextView)) // Turuncu
            binding.ing.setTextColor(getColor(R.color.white))        // Beyaz yazı

            binding.step.setBackgroundColor(getColor(R.color.green)) // Yeşil
            binding.step.setTextColor(getColor(R.color.white))       // Beyaz yazı

            // Görünürlük ayarları
            binding.ingScroll.visibility = View.VISIBLE
            binding.stepScroll.visibility = View.GONE
        }
    }

}
