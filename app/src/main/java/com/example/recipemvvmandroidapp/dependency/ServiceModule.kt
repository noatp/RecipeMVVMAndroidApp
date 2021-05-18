package com.example.recipemvvmandroidapp.dependency

import com.example.recipemvvmandroidapp.data.remote.RecipeNetworkService
import com.example.recipemvvmandroidapp.data.remote.util.RecipeDTOMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideRecipeDTOMapper(): RecipeDTOMapper{
        return RecipeDTOMapper()
    }

    @Singleton
    @Provides
    fun provideRecipeNetworkService(): RecipeNetworkService{
        return RecipeNetworkService()
    }

}