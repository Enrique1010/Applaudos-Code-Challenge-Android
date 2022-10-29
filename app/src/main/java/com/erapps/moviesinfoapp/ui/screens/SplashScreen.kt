package com.erapps.moviesinfoapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.erapps.moviesinfoapp.R
import com.erapps.moviesinfoapp.ui.theme.dimen
import com.erapps.moviesinfoapp.utils.convertDpToSp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(moveOn: () -> Unit) {

    LaunchedEffect(true) {
        delay(2000)
        moveOn()
    }

    SplashScreen()
}

@Composable
private fun SplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colors.secondary,
                        MaterialTheme.colors.primary,
                        MaterialTheme.colors.primary
                    )
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimen.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(MaterialTheme.dimen.imageLarge),
                imageVector = Icons.Default.PlayCircle,
                tint = Color.White,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimen.medium))
            Text(
                modifier = Modifier.fillMaxWidth(.5f),
                text = stringResource(id = R.string.app_title),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = MaterialTheme.dimen.textExtraLarge.convertDpToSp()
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun SlashScreenPreview() {
    SplashScreen()
}