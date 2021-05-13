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
import com.example.recipemvvmandroidapp.router.RouterController
import com.example.recipemvvmandroidapp.view.HomeView

object Singleton{
    private val recipeService: RecipeNetworkService = RecipeNetworkService()
    val appDependency= Dependency(
        recipeService = recipeService,
    )
}

class MainActivity : ComponentActivity() {
    private val dependency: Dependency = Singleton.appDependency
    private lateinit var router: RouterController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            router = RouterController(navController = navController)
            dependency.view().HomeView(router = router)
        }
    }
}
