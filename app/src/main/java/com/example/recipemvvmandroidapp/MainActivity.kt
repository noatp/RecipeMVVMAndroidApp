package com.example.recipemvvmandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.recipemvvmandroidapp.data.remote.RecipeNetworkService
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.view.HomeView

object Singleton{
    private val recipeService: RecipeNetworkService = RecipeNetworkService()
    val appDependency= Dependency(
        recipeService = recipeService,
    )
}

class MainActivity : ComponentActivity() {
    private val dependency: Dependency = Singleton.appDependency
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            dependency.view().HomeView()
        }
    }
}
