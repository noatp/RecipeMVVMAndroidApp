package com.example.recipemvvmandroidapp.data.repositoryImplementation.recipeRepository

import com.example.recipemvvmandroidapp.data.remote.RecipeNetworkService
import com.example.recipemvvmandroidapp.domain.model.RecipeDTO
import com.example.recipemvvmandroidapp.domain.util.RecipeDTOMapper
import com.example.recipemvvmandroidapp.domain.util.recipeDTOMapper
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.dependency.recipeService
import com.example.recipemvvmandroidapp.domain.repositoryInterface.RecipeRepositoryInterface

class RecipeRepository(
    private val recipeNetworkService: RecipeNetworkService,
    private val recipeDTOMapper: RecipeDTOMapper
): RecipeRepositoryInterface {
    override suspend fun getRecipeById(id: Int): RecipeDTO {
        return recipeDTOMapper.mapDomainModelToDTO(
            recipeNetworkService.getRecipeById(id)
        )
    }

    override suspend fun searchForRecipes(page: Int, query: String): List<RecipeDTO> {
        return recipeDTOMapper.mapListDomainModelToListDTO(
            recipeNetworkService.searchForRecipes(page, query)
        )
    }

    companion object{
        const val API_PAGE_SIZE = 30
    }
}

fun Dependency.Repository.recipeRepository(): RecipeRepository
{
    return RecipeRepository(
        service.recipeService(),
        service.recipeDTOMapper()
    )
}