package com.yape.remote.api

import com.yape.remote.models.RecipeModel
import com.yape.remote.models.RecipeResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeService {

    @GET("recipes")
    suspend fun getRecipes(): RecipeResponseModel

    @GET("recipe/{id}")
    suspend fun getRecipe(@Path("id") id: Long): RecipeModel
}
