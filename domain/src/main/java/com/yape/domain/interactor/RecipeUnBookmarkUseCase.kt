package com.yape.domain.interactor

import com.yape.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias RecipeUnBookmarkBaseUseCase = BaseUseCase<Long, Flow<Int>>

class RecipeUnBookmarkUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) : RecipeUnBookmarkBaseUseCase {

    override suspend operator fun invoke(params: Long) =
        recipeRepository.setRecipeUnBookMarked(params)
}
