package com.erapps.moviesinfoapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
fun Dp.convertDpToSp(): TextUnit {
    return with(LocalDensity.current) { this@convertDpToSp.toSp() }
}