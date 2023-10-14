package com.yape.domain.interactor

import com.yape.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias RecipeBookmarkBaseUseCase = BaseUseCase<Long, Flow<Int>>

class RecipeBookmarkUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) : RecipeBookmarkBaseUseCase {

    override suspend operator fun invoke(params: Long) =
        recipeRepository.setRecipeBookmarked(params)
}
