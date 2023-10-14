package com.yape.domain.interactor

import com.yape.domain.models.Recipe
import com.yape.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias GetRecipeListBaseUseCase = BaseUseCase<Unit, Flow<List<Recipe>>>

class GetRecipeListUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) : GetRecipeListBaseUseCase {

    override suspend operator fun invoke(params: Unit) = recipeRepository.gerRecipes()
}
