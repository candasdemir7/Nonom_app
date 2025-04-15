package com.mustafacandasdemir.candasdemir_hw2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.mustafacandasdemir.candasdemir_hw2.databinding.ActivityAddBinding
import com.mustafacandasdemir.candasdemir_hw2.db.RecipeRoomDatabase
import com.mustafacandasdemir.candasdemir_hw2.model.Recipe
import com.mustafacandasdemir.candasdemir_hw2.util.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



import android.view.GestureDetector
import android.view.MotionEvent


class AddActivity : AppCompatActivity(),GestureDetector.OnGestureListener {
    private lateinit var binding: ActivityAddBinding

    private lateinit var gestureDetector: GestureDetector
    //gesture


    // Initialize the database
    private val recipeDb: RecipeRoomDatabase by lazy {
        Room.databaseBuilder(this, RecipeRoomDatabase::class.java, Utils.DATABASENAME)
            .allowMainThreadQueries()  // Consider removing this and using background threads
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gestureDetector = GestureDetector(this, this)

        // Set touch listener for swipe gestures
        binding.root.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }

//       binding.textView5.setOnTouchListener{_, event ->
//           gestureDetector.onTouchEvent(event)
//           true
//       }


//        binding.btnAdd.setOnClickListener {
//            val id = 0
//            val name = binding.etName.text.toString()
//            val ingredients = binding.etIngredients.text.toString()
//            val details = binding.etDetails.text.toString()
//
//            val recipes = Recipe(id,name, ingredients, details)
//
//            recipeDb.recipeDao().insertRecipe(recipes)
//            Toast.makeText(this, "Recipes $name is added", Toast.LENGTH_LONG).show()
//            clear()
//        }
        // Adding the recipe to the database
        binding.btnAdd.setOnClickListener {

            val name = binding.etName.text.toString()
            val ingredients = binding.etIngredients.text.toString()
            val details = binding.etDetails.text.toString()
           // val image = binding.etChoice.text.toString()
            val image = "manti.png"

            if (name.isNotBlank() && ingredients.isNotBlank() && details.isNotBlank()) {
                // Create a new Recipe object
                val recipe = Recipe(id = 0, name = name, ingredients = ingredients, details = details, img = image)

                // Insert the recipe into the database
                GlobalScope.launch(Dispatchers.IO) {
                    recipeDb.recipeDao().insertRecipe(recipe)

                    // After insertion, show a message in the UI thread
                    launch(Dispatchers.Main) {
                        Toast.makeText(this@AddActivity, "Recipe '$name' is added", Toast.LENGTH_LONG).show()
                        clear()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        //delete

        binding.btnDelete.setOnClickListener {
            val name = binding.etName.text.toString() // Kullanıcıdan ad alıyoruz
            if (name.isNotBlank()) {
                // Silinecek tarifi veritabanından ad ile bul
                GlobalScope.launch(Dispatchers.IO) {
                    val recipe = recipeDb.recipeDao().getRecipeByName(name)
                    if (recipe != null) {
                        // Recipe varsa sil
                        recipeDb.recipeDao().deleteRecipe(recipe)

                        // UI thread üzerinde Toast mesajı göster
                        launch(Dispatchers.Main) {
                            Toast.makeText(this@AddActivity, "Recipe '$name' is deleted", Toast.LENGTH_LONG).show()
                            clear() // input alanlarını temizle
                        }
                    } else {
                        launch(Dispatchers.Main) {
                            Toast.makeText(this@AddActivity, "Recipe '$name' not found", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please enter a valid name", Toast.LENGTH_SHORT).show()
            }
        }



        binding.btnBack2 .setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Navigate back to MainActivity
        binding.btnList.setOnClickListener {
            val intent = Intent(this, NewList::class.java)
            startActivity(intent)
        }



    }

    // Clear the input fields
    private fun clear() {
        binding.etName.setText("")
        binding.etIngredients.setText("")
        binding.etDetails.setText("")
       // binding.etChoice.setText("")
    }

    override fun onDown(e: MotionEvent): Boolean {


        return true

    }

    override fun onShowPress(e: MotionEvent) {


           }

    override fun onSingleTapUp(e: MotionEvent): Boolean {

        return true    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
//        binding.textView5.text = "onScroll gesture detected"
        return false    }

    override fun onLongPress(e: MotionEvent) {


    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        // Detect swipe direction
        if (velocityX > 0) {
            // Swipe to the right (forward action)
            binding.textView5.text = "Swiped Right: Performing forward action"
            // Add any logic you want to perform on swipe right
        } else {
            // Swipe to the left (backward action)
            binding.textView5.text = "Swiped Left: Performing backward action"
            // Add any logic you want to perform on swipe left
        }
        return true
    }


//    <com.google.android.material.bottomnavigation.BottomNavigationView
//    android:id="@+id/bottomNavView"
//    android:layout_width="match_parent"
//    android:layout_height="match_parent"
//    app:menu="@menu/bottom_navigation_menu"
//    />

}
