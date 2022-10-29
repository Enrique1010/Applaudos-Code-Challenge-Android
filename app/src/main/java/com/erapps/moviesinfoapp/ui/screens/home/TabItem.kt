package com.erapps.moviesinfoapp.ui.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.erapps.moviesinfoapp.ui.navigation.NavItem

sealed class TabItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home: TabItem(NavItem.Home.route, "Home", Icons.Default.Home)
    object Favs: TabItem(NavItem.Favs.route, "Favs", Icons.Default.Favorite)
}
