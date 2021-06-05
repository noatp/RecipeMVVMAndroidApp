package com.example.recipemvvmandroidapp.data.remote

import android.util.Log
import com.example.recipemvvmandroidapp.data.remote.model.Response
import com.example.recipemvvmandroidapp.domain.model.Recipe
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

class RecipeNetworkService: RecipeNetworkServiceInterface {
    private val authToken = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    private val apiUrl = "https://food2fork.ca/api/recipe"

    private val client: HttpClient = HttpClient(){
        install(JsonFeature){
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json{
                ignoreUnknownKeys = true
            })
        }
    }

    override suspend fun getRecipeById(id: Int): Recipe {
        try{
            return client.get(urlString = "$apiUrl/get/?id=$id")
            {
                headers{ append("Authorization", authToken) }
            }
        } catch (exception: Exception){
            Log.d("Rethrow exception in RecipeNetworkService: getRecipeById", "$exception")
            throw exception
        }
    }

    override suspend fun searchForRecipes(page: Int, query: String): Response{
        try{
            return client.get<Response>(urlString = "$apiUrl/search/?page=$page&query=$query")
            {
                headers{
                    append("Authorization", authToken)
                }
            }
        } catch (exception: Exception){
            Log.d("Rethrow exception in RecipeNetworkService: searchForRecipes", "$exception")
            throw exception
        }

    }
}