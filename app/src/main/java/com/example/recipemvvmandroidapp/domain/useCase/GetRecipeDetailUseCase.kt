package com.example.recipemvvmandroidapp.domain.useCase

import com.example.recipemvvmandroidapp.data.repositoryImplementation.RecipeRepository
import com.example.recipemvvmandroidapp.data.repositoryImplementation.recipeRepository
import com.example.recipemvvmandroidapp.dependency.Dependency

class GetRecipeDetailUseCase(
    private val recipeRepository: RecipeRepository
) {
    data class RecipeForDetailView(
        val title: String,
        val featuredImage: String,
        val ingredients: List<String>
    )

    suspend fun execute(
        id: Int
    ): UseCaseResult<RecipeForDetailView>
    {
        return try{
            UseCaseResult.Success(recipeRepository
                .getRecipeById(id)
                .let{
                    RecipeForDetailView(
                        it.title,
                        it.featuredImage,
                        it.ingredients
                    )
                }
            )
        } catch(exception: Exception){
            UseCaseResult.Error(exception)
        }
    }
}

fun Dependency.UseCase.getRecipeDetailUseCase(): GetRecipeDetailUseCase
{
    return GetRecipeDetailUseCase(repository.recipeRepository())
}