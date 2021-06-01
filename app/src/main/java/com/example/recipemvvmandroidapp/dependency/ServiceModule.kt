package com.example.recipemvvmandroidapp.dependency

import android.content.Context
import androidx.room.Room
import com.example.recipemvvmandroidapp.data.local.RecipeRoomDatabase
import com.example.recipemvvmandroidapp.data.remote.RecipeNetworkService
import com.example.recipemvvmandroidapp.domain.util.RecipeDTOMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideRecipeDTOMapper(): RecipeDTOMapper {
        return RecipeDTOMapper()
    }

    @Singleton
    @Provides
    fun provideRecipeNetworkService(): RecipeNetworkService{
        return RecipeNetworkService()
    }

    @Singleton
    @Provides
    fun provideRecipeRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            RecipeRoomDatabase::class.java,
            "recipe.db"
        ).build()

}