package com.erapps.moviesinfoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erapps.moviesinfoapp.ui.screens.MainScreen
import com.erapps.moviesinfoapp.ui.screens.SplashScreen

@Composable
fun SplashToLandingNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavItem.Splash.baseRoute) {
        composable(route = NavItem.Splash.baseRoute) {
            SplashScreen {
                navController.popBackStack()
                navController.navigate(NavItem.Main.route)
            }
        }
        composable(NavItem.Main.route) {
            MainScreen()
        }
    }
}