package com.mustafacandasdemir.candasdemir_hw2

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.mustafacandasdemir.candasdemir_hw2.adapter.CustomRecyclerViewAdapter
import com.mustafacandasdemir.candasdemir_hw2.databinding.ActivityMainBinding
import com.mustafacandasdemir.candasdemir_hw2.databinding.DialogBinding
import com.mustafacandasdemir.candasdemir_hw2.db.RecipeDAO
import com.mustafacandasdemir.candasdemir_hw2.db.RecipeRoomDatabase
import com.mustafacandasdemir.candasdemir_hw2.model.Parent
import com.mustafacandasdemir.candasdemir_hw2.model.Recipe
import com.mustafacandasdemir.candasdemir_hw2.retrofit.ApiClient
import com.mustafacandasdemir.candasdemir_hw2.retrofit.ApiService
import com.mustafacandasdemir.candasdemir_hw2.util.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Data
import com.mustafacandasdemir.candasdemir_hw2.backgroundservice.BackgroundWorker

class MainActivity : AppCompatActivity(), CustomRecyclerViewAdapter.RecyclerAdapterInterface {

    lateinit var apiService: ApiService
    lateinit var adapter: CustomRecyclerViewAdapter
    lateinit var bindingMain: ActivityMainBinding

    // Veritabanı için gerekli nesneler
    lateinit var recipeDao: RecipeDAO
    lateinit var recipeDb: RecipeRoomDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

        // Veritabanı başlatma
        recipeDb = Room.databaseBuilder(this, RecipeRoomDatabase::class.java, Utils.DATABASENAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        recipeDao = recipeDb.recipeDao()

        ViewCompat.setOnApplyWindowInsetsListener(bindingMain.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // RecyclerView ayarları
        bindingMain.recyclerRecipies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = CustomRecyclerViewAdapter(this)
        bindingMain.recyclerRecipies.adapter = adapter

        // Veritabanından tarifleri gözlemle
        recipeDao.getAllRecipes().observe(this, Observer { recipes ->
            recipes?.let {
                adapter.setData(it)
            }
        })

        // Retrofit API servisi ayarı
        apiService = ApiClient.getClient().create(ApiService::class.java)
        val request = apiService.getRecipes()

        Log.d("JSONARRAYPARSE", "Before Request")
        request.enqueue(object : Callback<Parent> {
            override fun onFailure(call: Call<Parent>, t: Throwable) {
                Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_LONG).show()
                Log.d("JSONARRAYPARSE", "Error: " + t.message.toString())
            }

            override fun onResponse(call: Call<Parent>, response: Response<Parent>) {
                Log.d("JSONARRAYPARSE", "Response taken")
                if (response.isSuccessful) {
                    Utils.parent = (response.body() as Parent?)!!
                    Log.d("JSONARRAYPARSE", "Recipes taken from server" + Utils.parent.toString())
                    adapter.setData(Utils.parent.recipes!!)
                }
            }
        })

        // Ekranlar arası geçiş butonları
        bindingMain.btnAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        // Arka plan görevini başlatan buton
        bindingMain.btnStartWorker.setOnClickListener {
            startBackgroundTask()
        }
    }

    // Tarif Detayları Dialogu
    fun displayDialog(recipeSelected: Recipe) {
        val dialog = Dialog(this)
        val bindingDialog: DialogBinding = DialogBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)

        bindingDialog.tvDialogRecipeName.text = recipeSelected.name
        bindingDialog.btnCloseDialog.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

    override fun displayItem(sc: Recipe) {
        displayDialog(sc)
    }

    // Arka plan görevi başlatma
    private fun startBackgroundTask() {
        val inputData = Data.Builder()
            .putString("recipeName", "Yeni Tarif")
            .putString("ingredients", "Un, Şeker, Yumurta")
            .putString("details", "Fırında 20 dakika pişirin.")
            .putString("img", "default.png")
            .build()

        val workRequest = OneTimeWorkRequest.Builder(BackgroundWorker::class.java)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)
        Toast.makeText(this, "Background Task Started", Toast.LENGTH_SHORT).show()
    }
}
