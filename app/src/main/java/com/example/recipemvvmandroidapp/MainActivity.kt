package com.example.recipemvvmandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.recipemvvmandroidapp.router.RouterController
import com.example.recipemvvmandroidapp.view.HomeView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
            HomeView(router = router)
        }
    }
}
