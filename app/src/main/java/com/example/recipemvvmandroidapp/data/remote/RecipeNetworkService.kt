package com.example.recipemvvmandroidapp.data.remote

import com.example.recipemvvmandroidapp.data.remote.model.RecipeDTO
import com.example.recipemvvmandroidapp.data.remote.model.Response
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

class RecipeNetworkService(): RecipeNetworkServiceInterface {
    private val authToken = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    private val apiUrl = "https://food2fork.ca/api/recipe"

    private val client: HttpClient = HttpClient(){
        install(JsonFeature){
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json{
                ignoreUnknownKeys = true
            })
        }
    }

    override suspend fun getRecipeById(id: Int): RecipeDTO {
        return client.get(urlString = "$apiUrl/get/?id=$id")
        {
            headers{ append("Authorization", authToken) }
        }
    }

    override suspend fun searchForRecipes(page: Int, query: String): List<RecipeDTO>{
        return client.get<Response>(urlString = "$apiUrl/search/?page=$page&query=$query")
        {
            headers{
                append("Authorization", authToken)
            }
        }.results

    }
}