package com.erapps.moviesinfoapp.ui.screens

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.erapps.moviesinfoapp.ui.navigation.MainNavigation

@Composable
fun MainScreen() {

    Scaffold {
        MainNavigation(it)
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun MainScreenPreview() {
    MainScreen()
}