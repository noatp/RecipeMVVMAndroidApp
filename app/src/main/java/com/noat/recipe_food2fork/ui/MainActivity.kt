package com.noat.recipe_food2fork.ui

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.compose.rememberNavController
import com.noat.recipe_food2fork.data.local.RecipeLocalDatabase
import com.noat.recipe_food2fork.data.remote.RecipeNetworkService
import com.noat.recipe_food2fork.dependency.Dependency
import com.noat.recipe_food2fork.ui.router.RouterController
import com.noat.recipe_food2fork.ui.view.HomeView

class ThisApplication: Application(){
    private val context: Context = applicationContext
    fun getAppContext(): Context{
        return context
    }
}

object Singleton{
    private val recipeNetworkService: RecipeNetworkService = RecipeNetworkService()
    private val recipeLocalDatabase: RecipeLocalDatabase = RecipeLocalDatabase(ThisApplication().getAppContext()`)
    val appDependency= Dependency(
        recipeNetworkService = recipeNetworkService,
        recipeLocalDatabase = recipeLocalDatabase
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
