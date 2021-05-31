package com.example.recipemvvmandroidapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.recipemvvmandroidapp.domain.model.Recipe

@Dao
interface RecipeDAO{
    @Query("SELECT * FROM Recipe WHERE id = (:recipeId)")
    fun findByRecipeId(recipeId: Int): Recipe

    @Insert
    fun insertAll(vararg recipes: Recipe)
}