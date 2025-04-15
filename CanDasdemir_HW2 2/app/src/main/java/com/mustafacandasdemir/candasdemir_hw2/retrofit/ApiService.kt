package com.mustafacandasdemir.candasdemir_hw2.retrofit

import com.mustafacandasdemir.candasdemir_hw2.model.Parent
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {


    @GET("b/R99X/")
    fun getRecipes(): Call<Parent>
}