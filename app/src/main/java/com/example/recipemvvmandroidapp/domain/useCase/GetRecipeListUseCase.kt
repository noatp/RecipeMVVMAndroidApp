package com.example.recipemvvmandroidapp.domain.useCase

import com.example.recipemvvmandroidapp.data.repositoryImplementation.RecipeRepository
import com.example.recipemvvmandroidapp.data.repositoryImplementation.recipeRepository
import com.example.recipemvvmandroidapp.dependency.Dependency

class GetRecipeListUseCase(
    private val recipeRepository: RecipeRepository
) {
    sealed class Result<out R>{
        data class Success<out T>(val resultValue: T): Result<T>()
        data class Error(val exception: Exception): Result<Nothing>()
    }

    data class RecipeForCardView(
        val id: Int,
        val title: String,
        val featuredImage: String
    )

    suspend fun execute(
        page: Int,
        query: String
    ): Result<List<RecipeForCardView>>
    {
        return try{
            Result.Success(recipeRepository
                .searchForRecipes(page, query)
                .map{
                    RecipeForCardView(
                        it.id,
                        it.title,
                        it.featuredImage
                    )
                })
        } catch(exception: Exception){
            Result.Error(exception)
        }
    }
}

fun Dependency.UseCase.getRecipeListUseCase(): GetRecipeListUseCase
{
    return GetRecipeListUseCase(repository.recipeRepository())
}