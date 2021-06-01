package com.example.recipemvvmandroidapp.data.local

import androidx.paging.PagingSource
import androidx.room.*
import com.example.recipemvvmandroidapp.domain.model.Recipe

@Dao
interface RecipeDAO{
    @Query("SELECT * FROM Recipe " +
            "WHERE id = (:recipeId) ")
    fun findByRecipeId(recipeId: Int): Recipe

    @Query("SELECT * FROM Recipe " +
            "WHERE title LIKE ('%' || :query || '%') " +
            "OR ingredients LIKE ('%' || :query || '%') ")
    fun searchForRecipe(query: String): PagingSource<Int, Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(vararg recipes: Recipe)

    @Delete
    fun delete(recipe: Recipe)
}