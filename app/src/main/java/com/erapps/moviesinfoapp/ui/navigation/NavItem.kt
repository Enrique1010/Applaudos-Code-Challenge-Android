package com.erapps.moviesinfoapp.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavItem(
    val baseRoute: String,
    private val navArgs: List<NavArgs> = emptyList()
) {
    val route = run {
        val argKeys = navArgs.map { "{${it.key}}" }
        listOf(baseRoute).plus(argKeys).joinToString("/")
    }

    val args = navArgs.map { navArgument(name = it.key) { type = it.navType } }

    //navigation objects
    //splash to landing
    object Splash : NavItem("splash")
    object Main : NavItem("main")

    //main navigation
    object Home : NavItem("home")
    object Profile : NavItem("profile")
    object Details : NavItem("details", listOf(NavArgs.DetailsId)) {
        fun createRoute(id: Int) = "$baseRoute/$id"
    }
}

enum class NavArgs(
    val key: String,
    val navType: NavType<*>
) {
    DetailsId("detailsId", NavType.IntType)
}