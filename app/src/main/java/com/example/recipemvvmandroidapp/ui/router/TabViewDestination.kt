package com.example.recipemvvmandroidapp.ui.router

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class TabViewDestination(
    val route: String,
    val icon: ImageVector
){
    Search(route = "search", icon = Icons.Outlined.Search),
    Discovery(route = "discovery", icon = Icons.Outlined.Lightbulb);

    companion object{
        fun getTabFromRoute(route: String?): TabViewDestination {
            return when (route?.substringBefore(delimiter = "/")){
                Search.route -> Search
                Discovery.route -> Discovery
                else -> Search
            }
        }
    }
}