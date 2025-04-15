package com.mustafacandasdemir.candasdemir_hw2.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Parent (
    //owner burada platform
    @SerializedName("owner")
    @Expose
    var owner: Owner,
    @SerializedName("recipes")
    var recipes: List<Recipe>?)
{
    override fun toString(): String {
        return "${owner.toString()} ${recipes.toString()}\n"
    }
}