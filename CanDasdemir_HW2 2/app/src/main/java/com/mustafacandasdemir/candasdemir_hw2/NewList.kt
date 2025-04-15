package com.mustafacandasdemir.candasdemir_hw2

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.mustafacandasdemir.candasdemir_hw2.adapter.CustomRecyclerViewAdapter
import com.mustafacandasdemir.candasdemir_hw2.adapter.CustomRecyclerViewAdapter2
import com.mustafacandasdemir.candasdemir_hw2.databinding.ActivityNewListBinding
import com.mustafacandasdemir.candasdemir_hw2.db.RecipeRoomDatabase
import com.mustafacandasdemir.candasdemir_hw2.util.Utils
//
//class NewList : AppCompatActivity() {
//
//    private lateinit var binding: ActivityNewListBinding
//    private lateinit var recipeDb: RecipeRoomDatabase
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityNewListBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Veritabanı başlatma
//        recipeDb = Room.databaseBuilder(this, RecipeRoomDatabase::class.java, Utils.DATABASENAME)
//            .allowMainThreadQueries()
//            .fallbackToDestructiveMigration()
//            .build()
//
//        // Veritabanından tarifleri al
//        val recipes = recipeDb.recipeDao().getAllsRecipes() // Bu metot LiveData dönecek, onu gözlemleyeceğiz
//
//        // Tarifleri TextView'da göstermek için
//        val textView = findViewById<TextView>(R.id.textView6)
//
//        var recipeDetails = ""
//
//        // Veritabanından gelen tarif adlarını metin olarak birleştirme
//        for (recipe in recipes) {
//            recipeDetails += "Name: ${recipe.name}\n" +
//                    "Ingredients: ${recipe.ingredients}\n" +
//                    "Details: ${recipe.details}\n" +
//                    "Image: ${recipe.img}\n\n"
//
//        }
//
//        // Tarif adlarını textView'da gösterme
//        textView.text = recipeDetails
//
//
//        binding.btnBack.setOnClickListener {
//
//            val intent = Intent(this, AddActivity::class.java)
//            startActivity(intent)
//        }
//
//
//    }
//}

class NewList : AppCompatActivity() {

    private lateinit var binding: ActivityNewListBinding
    private lateinit var recipeDb: RecipeRoomDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Veritabanı başlatma
        recipeDb = Room.databaseBuilder(this, RecipeRoomDatabase::class.java, Utils.DATABASENAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        // Veritabanından tarifleri al
        val recipes = recipeDb.recipeDao().getAllsRecipes() // Veritabanından gelen tarif listesi

        // RecyclerView için adapter ayarla
        val adapter = CustomRecyclerViewAdapter2(recipes)
        binding.recyclerRecipies.layoutManager = LinearLayoutManager(this)
        binding.recyclerRecipies.adapter = adapter

        // Geri butonunun işlevselliği
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        binding.btnMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}

