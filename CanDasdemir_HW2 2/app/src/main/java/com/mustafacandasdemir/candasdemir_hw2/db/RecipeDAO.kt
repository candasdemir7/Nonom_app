package com.mustafacandasdemir.candasdemir_hw2.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mustafacandasdemir.candasdemir_hw2.model.Recipe
import com.mustafacandasdemir.candasdemir_hw2.util.Utils


@Dao

interface RecipeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: Recipe) // suspend is written because it will be used with coroutine

    @Update
    fun updateRecipe(recipe: Recipe)


    @Delete
    fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * FROM ${Utils.TABLENAME_RECIPE} ORDER BY name")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Query("SELECT *FROM ${Utils.TABLENAME_RECIPE}")
    fun getAllsRecipes(): List<Recipe>


    //sonradan eklendi
    @Query("SELECT * FROM ${Utils.TABLENAME_RECIPE} WHERE name LIKE :searchKey")
    fun getCustomersBySearchKey(searchKey: String): LiveData<List<Recipe>>


    @Query("SELECT * FROM ${Utils.TABLENAME_RECIPE} WHERE name = :name")
    suspend fun getRecipeByName(name: String): Recipe?
}