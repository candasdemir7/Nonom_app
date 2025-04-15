package com.mustafacandasdemir.candasdemir_hw2.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mustafacandasdemir.candasdemir_hw2.model.Owner
import com.mustafacandasdemir.candasdemir_hw2.model.Recipe

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DbViewModel(application:Application):AndroidViewModel(application) {
    val readAllDataOwner: LiveData<List<Owner>>
    val readAllDataRecipe: LiveData<List<Recipe>>
    lateinit var ownerDAO: OwnerDAO
    lateinit var recipeDAO:RecipeDAO
    init {
        ownerDAO= RecipeRoomDatabase.getDatabase(application).ownerDao()
        recipeDAO= RecipeRoomDatabase.getDatabase(application).recipeDao()
        readAllDataOwner = ownerDAO.getAllOwners();
        readAllDataRecipe = recipeDAO.getAllRecipes();
    }
    fun addOwner(owner: Owner){
        viewModelScope.launch(Dispatchers.IO){ // that code will be run in background thread, coroutine scope
            ownerDAO.insertOwner(owner)
        }
    }
    fun addRecipe(recipe: Recipe){
        viewModelScope.launch(Dispatchers.IO){ // that code will be run in background thread, coroutine scope
            recipeDAO.insertRecipe(recipe)
        }
    }
    fun deleteOwner(owner: Owner){
        viewModelScope.launch(Dispatchers.IO){ // that code will be run in background thread, coroutine scope
            ownerDAO.deleteOwner(owner)
        }
    }
    fun updateOwner(owner: Owner){
        viewModelScope.launch(Dispatchers.IO){ // that code will be run in background thread, coroutine scope
            ownerDAO.updateOwner(owner)
        }
    }
}

