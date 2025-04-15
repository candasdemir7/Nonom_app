package com.mustafacandasdemir.candasdemir_hw2.backgroundservice

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.mustafacandasdemir.candasdemir_hw2.db.RecipeRoomDatabase
import com.mustafacandasdemir.candasdemir_hw2.model.Recipe
import com.mustafacandasdemir.candasdemir_hw2.util.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BackgroundWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        // Girdi verilerini al
        val recipeName = inputData.getString("recipeName") ?: "Default Recipe"
        val ingredients = inputData.getString("ingredients") ?: "Default Ingredients"
        val details = inputData.getString("details") ?: "Default Details"
        val img = inputData.getString("img") ?: "default.png"

        return try {
            // Veritabanı erişimi
            val db = RecipeRoomDatabase.getDatabase(applicationContext)
            val recipeDao = db.recipeDao()

            val newRecipe = Recipe(
                name = recipeName,
                ingredients = ingredients,
                details = details,
                img = img
            )

            withContext(Dispatchers.IO) {
                recipeDao.insertRecipe(newRecipe)
            }

            Log.d(Utils.TAGFORLAGCAT, "BackgroundWorker: Tarif başarıyla eklendi -> $recipeName")

            // Başarı sonucu döndür
            Result.success(workDataOf("result" to "Tarif başarıyla eklendi!"))
        } catch (e: Exception) {
            Log.e(Utils.TAGFORLAGCAT, "BackgroundWorker Hatası: ${e.message}")
            Result.failure()
        }
    }
}
