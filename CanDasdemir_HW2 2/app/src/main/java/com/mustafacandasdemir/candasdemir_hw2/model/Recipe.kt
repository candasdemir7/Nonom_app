package com.mustafacandasdemir.candasdemir_hw2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mustafacandasdemir.candasdemir_hw2.util.Utils

//Model class created according to JSON object and Table
@Entity(tableName = Utils.TABLENAME_RECIPE)
class Recipe (
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    @SerializedName("name") //name match with the key of JSON object, GSON according to that name will map the object to data members
    val name: String = "",
    @SerializedName("ingredients")
    val ingredients: String = "",
    @SerializedName("details")
    val details: String = "",
    @SerializedName("img")
    val img: String = "")
{
    override fun toString(): String {
        return "Recipe\nName: $name\nIngredients=$ingredients\nDetails='$details\n"
    }
}
