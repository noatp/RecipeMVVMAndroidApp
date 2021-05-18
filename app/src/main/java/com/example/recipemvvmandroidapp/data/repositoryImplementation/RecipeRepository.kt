package com.example.recipemvvmandroidapp.data.repositoryImplementation

import com.example.recipemvvmandroidapp.data.remote.RecipeNetworkService
import com.example.recipemvvmandroidapp.data.remote.util.RecipeDTOMapper
import com.example.recipemvvmandroidapp.domain.model.Recipe
import com.example.recipemvvmandroidapp.domain.repositoryInterface.RecipeRepositoryInterface

class RecipeRepository(
    private val recipeService: RecipeNetworkService,
    private val recipeDTOMapper: RecipeDTOMapper
): RecipeRepositoryInterface {
    override suspend fun getRecipeById(id: Int): Recipe {
        return recipeDTOMapper
            .mapToDomainModel(recipeService
                .getRecipeById(id)
            )
    }

    override suspend fun searchForRecipes(page: Int, query: String): List<Recipe> {
        return recipeDTOMapper
            .mapToListDomainModel(recipeService
                .searchForRecipes(page, query)
            )
    }
}
