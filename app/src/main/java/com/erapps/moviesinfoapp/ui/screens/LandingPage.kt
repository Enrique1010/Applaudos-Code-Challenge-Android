package com.erapps.moviesinfoapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.erapps.moviesinfoapp.ui.navigation.SplashToLandingNavigation
import com.erapps.moviesinfoapp.ui.theme.MoviesInfoAppTheme

@Composable
fun LandingPage() {

    MoviesInfoAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            SplashToLandingNavigation()
        }
    }
}