package com.yape.remote.models

import com.squareup.moshi.Json

data class RecipeResponseModel(
    @field:Json(name = "results")
    val recipes: List<RecipeModel>
)
