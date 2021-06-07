package com.noat.recipe_food2fork.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.compose.rememberNavController
import com.noat.recipe_food2fork.data.remote.RecipeNetworkService
import com.noat.recipe_food2fork.dependency.Dependency
import com.noat.recipe_food2fork.ui.router.RouterController
import com.noat.recipe_food2fork.ui.view.HomeView

object Singleton{
    private val recipeService: RecipeNetworkService =
        RecipeNetworkService()
    val appDependency= Dependency(
        recipeService = recipeService,
    )
}

class MainActivity : ComponentActivity() {
    private val dependency: Dependency = Singleton.appDependency
    private lateinit var router: RouterController
    @ExperimentalMaterialApi
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
    override fun onBackPressed() {
        if(router.popOffModal())
        {
            return
        }
        super.onBackPressed()
    }
}
