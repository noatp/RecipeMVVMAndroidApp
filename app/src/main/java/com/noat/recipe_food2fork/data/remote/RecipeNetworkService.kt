package com.noat.recipe_food2fork.data.remote

import android.util.Log
import com.noat.recipe_food2fork.data.SearchResponse
import com.noat.recipe_food2fork.domain.model.Recipe
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

class RecipeNetworkService {
    private val authToken = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    private val apiUrl = "https://food2fork.ca/api/recipe"

    private val client: HttpClient = HttpClient(){
        install(JsonFeature){
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json{
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getRecipeById(id: Int): Recipe {
        return client.get(urlString = "$apiUrl/get/?id=$id")
        {
            headers{ append("Authorization", authToken) }
        }
    }

    suspend fun searchForRecipes(page: Int, query: String): SearchResponse {
        return client.get<SearchResponse>(urlString = "$apiUrl/search/?page=$page&query=$query")
        {
            headers{
                append("Authorization", authToken)
            }
        }
    }
}