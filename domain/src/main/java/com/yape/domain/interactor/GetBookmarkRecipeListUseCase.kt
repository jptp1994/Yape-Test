package com.yape.domain.interactor

import com.yape.domain.models.Recipe
import com.yape.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias GetBookmarkRecipeListBaseUseCase = BaseUseCase<Unit, Flow<List<Recipe>>>

class GetBookmarkRecipeListUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) : GetBookmarkRecipeListBaseUseCase {

    override suspend operator fun invoke(params: Unit) = recipeRepository.getBookMarkedRecipes()
}
