package com.erapps.moviesinfoapp.ui.screens

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.erapps.moviesinfoapp.ui.navigation.MainNavigation

@Composable
fun MainScreen() {

    Scaffold {
        MainNavigation(it)
    }
}