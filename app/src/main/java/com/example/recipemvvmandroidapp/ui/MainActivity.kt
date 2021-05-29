package com.example.recipemvvmandroidapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.recipemvvmandroidapp.data.remote.RecipeNetworkService
import com.example.recipemvvmandroidapp.dependency.Dependency
import com.example.recipemvvmandroidapp.ui.router.RouterController
import com.example.recipemvvmandroidapp.ui.view.HomeView

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
            val tabController = rememberNavController()
            val modalController = rememberNavController()
            router = RouterController(
                tabController = tabController,
                modalController = modalController
            )
            dependency.view().HomeView(router = router)
        }
    }
}