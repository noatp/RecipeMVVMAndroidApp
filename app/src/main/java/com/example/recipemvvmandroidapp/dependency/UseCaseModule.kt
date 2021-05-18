package com.example.recipemvvmandroidapp.dependency

import com.example.recipemvvmandroidapp.data.repositoryImplementation.RecipeRepository
import com.example.recipemvvmandroidapp.domain.useCase.GetRecipeDetailUseCase
import com.example.recipemvvmandroidapp.domain.useCase.GetRecipeListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetRecipeDetailUseCase(
        recipeRepository: RecipeRepository
    ): GetRecipeDetailUseCase{
        return GetRecipeDetailUseCase(recipeRepository = recipeRepository)
    }

    @Singleton
    @Provides
    fun provideGetRecipeListUseCase(
        recipeRepository: RecipeRepository
    ): GetRecipeListUseCase{
        return GetRecipeListUseCase(recipeRepository = recipeRepository)
    }

}