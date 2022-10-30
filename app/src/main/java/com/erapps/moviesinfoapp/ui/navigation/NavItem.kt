package com.erapps.moviesinfoapp.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.erapps.moviesinfoapp.utils.Constants.DETAILS_SCREEN
import com.erapps.moviesinfoapp.utils.Constants.HOME_SCREEN
import com.erapps.moviesinfoapp.utils.Constants.MAIN_SCREEN
import com.erapps.moviesinfoapp.utils.Constants.PROFILE_SCREEN
import com.erapps.moviesinfoapp.utils.Constants.SPLASH_SCREEN
import com.erapps.moviesinfoapp.utils.Constants.TV_SHOW_ID_ARGUMENT

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
    object Splash : NavItem(SPLASH_SCREEN)
    object Main : NavItem(MAIN_SCREEN)

    //main navigation
    object Home : NavItem(HOME_SCREEN)
    object Profile : NavItem(PROFILE_SCREEN)
    object Details : NavItem(DETAILS_SCREEN, listOf(NavArgs.DetailsId)) {
        fun createRoute(id: Int) = "$baseRoute/$id"
    }
}

enum class NavArgs(
    val key: String,
    val navType: NavType<*>
) {
    DetailsId(TV_SHOW_ID_ARGUMENT, NavType.IntType)
}