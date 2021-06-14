package com.noat.recipe_food2fork.data.local

import android.content.Context
import com.noat.recipe_food2fork.data.SearchResponse
import com.noat.recipe_food2fork.database.RecipeDatabase
import com.noat.recipe_food2fork.domain.model.Recipe
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import comnoatrecipefood2forksq.RecipeTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

const val PAGE_SIZE = 30

class RecipeLocalDatabase(
    applicationContext: Context
){
    private val sqlDriver: SqlDriver = AndroidSqliteDriver(
        schema = RecipeDatabase.Schema,
        context = applicationContext,
        name = "recipe.db"
    )
    private val recipeDatabase = RecipeDatabase(sqlDriver)
    private val recipeQueries = recipeDatabase.recipeQueries

    //mapper functions
    private fun recipeToRecipeTableMapper(recipe: Recipe): RecipeTable{
        return RecipeTable(
            id = recipe.id,
            title = recipe.title,
            publisher = recipe.publisher,
            featured_image = recipe.featuredImage,
            rating = recipe.rating,
            source_url = recipe.sourceUrl,
            ingredients = Json.encodeToString(recipe.ingredients),
            date_added = recipe.dateAdded,
            date_updated = recipe.dateUpdated
        )
    }
    private fun recipeTableToRecipeMapper(recipeTable: RecipeTable): Recipe{
        return Recipe(
            id = recipeTable.id,
            title = recipeTable.title,
            publisher = recipeTable.publisher,
            featuredImage = recipeTable.featured_image,
            rating = recipeTable.rating,
            sourceUrl = recipeTable.source_url,
            ingredients = Json.decodeFromString(recipeTable.ingredients),
            dateAdded = recipeTable.date_added,
            dateUpdated = recipeTable.date_updated
        )
    }

    suspend fun insertRecipe(recipe: Recipe){
        withContext(Dispatchers.IO){
            recipeQueries.insertRecipe(
                recipeToRecipeTableMapper(recipe = recipe)
            )
        }
    }

    suspend fun searchForRecipes(page: Int, query: String): SearchResponse{
        var searchResponse = SearchResponse.empty
        withContext(Dispatchers.IO){
            searchResponse = SearchResponse(
                count = 0,
                next = "test",
                previous = "another test",
                results = recipeQueries.searchRecipes(
                    query = query,
                    pageSize = PAGE_SIZE.toLong(),
                    page = page.toLong()
                ).executeAsList().map{ recipeTable ->
                    recipeTableToRecipeMapper(recipeTable = recipeTable)
                }
            )
        }
        return searchResponse
    }


    fun getRecipeById(id: Int): Flow<Recipe> {
        return recipeQueries.getRecipeById(id)
            .asFlow()
            .mapToOne()
            .map { recipeTable: RecipeTable ->
                recipeTableToRecipeMapper(recipeTable = recipeTable)
            }
    }
}