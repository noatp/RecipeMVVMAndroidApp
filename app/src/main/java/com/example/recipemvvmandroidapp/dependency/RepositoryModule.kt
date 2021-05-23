package com.example.recipemvvmandroidapp.dependency

import com.example.recipemvvmandroidapp.data.remote.RecipeNetworkService
import com.example.recipemvvmandroidapp.data.remote.util.RecipeDTOMapper
import com.example.recipemvvmandroidapp.data.repositoryImplementation.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeNetworkService,
        recipeDTOMapper: RecipeDTOMapper
    ): RecipeRepository{
        return RecipeRepository(
            recipeService = recipeService,
            recipeDTOMapper = recipeDTOMapper
        )
    }
}