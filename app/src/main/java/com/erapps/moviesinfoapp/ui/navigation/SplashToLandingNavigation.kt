package com.erapps.moviesinfoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erapps.moviesinfoapp.ui.screens.LandingPage
import com.erapps.moviesinfoapp.ui.screens.SplashScreen

@Composable
fun SplashToLandingNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavItem.Splash.baseRoute) {
        composable(route = NavItem.Splash.route){
            SplashScreen {
                navController.popBackStack()
                navController.navigate(NavItem.Landing.route)
            }
        }
        composable(NavItem.Landing.route){
            LandingPage()
        }
    }
}