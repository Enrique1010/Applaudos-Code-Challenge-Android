package com.erapps.moviesinfoapp.ui.shared

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.erapps.moviesinfoapp.R
import com.erapps.moviesinfoapp.ui.theme.dimen
import com.erapps.moviesinfoapp.utils.convertDpToSp

@Composable
fun <T> DetailsPageWithState(
    previousBackGroundColor: Color,
    uiState: UiState?,
    emptyText: Int? = null,
    onBackPressed: () -> Unit,
    successBlock: @Composable (T) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colors.surface,
                        previousBackGroundColor,
                        MaterialTheme.colors.surface
                    )
                )
            )
    ) {
        when (uiState) {
            UiState.Loading -> {
                LoadingScreen(modifier = Modifier.background(MaterialTheme.colors.surface))
            }
            is UiState.Empty -> {
                emptyText?.let {
                    ScreenWithMessage(message = emptyText)
                }
            }
            is UiState.Error -> {
                ErrorScreen(
                    errorMessage = uiState.errorMessage,
                    errorStringResource = uiState.errorStringResource
                )
            }
            is UiState.Success<*> -> {
                @Suppress("UNCHECKED_CAST")
                successBlock(uiState.data as T)
            }
            else -> {}
        }
        BackButtonBar(onBackPressed = onBackPressed)
    }
}

@Composable
fun <T> PageWithState(
    uiState: UiState?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    successBlock: @Composable (T) -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when (uiState) {
            UiState.Loading -> {
                LoadingScreen(modifier = Modifier.background(MaterialTheme.colors.surface))
            }
            is UiState.Empty -> {
                ScreenWithMessage(message = R.string.no_results, onClick = onClick)
            }
            is UiState.Error -> {
                ErrorScreen(
                    errorMessage = uiState.errorMessage,
                    errorStringResource = uiState.errorStringResource
                )
            }
            is UiState.Success<*> -> {
                @Suppress("UNCHECKED_CAST")
                successBlock(uiState.data as T)
            }
            else -> {}
        }
    }
}

@Composable
fun BackButtonBar(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    Box(
        modifier = modifier
            .background(Color.Transparent),
        contentAlignment = Alignment.TopStart
    ) {
        Icon(
            modifier = modifier
                .size(MaterialTheme.dimen.large)
                .offset(
                    MaterialTheme.dimen.small,
                    MaterialTheme.dimen.small
                )
                .clickable { onBackPressed() },
            imageVector = Icons.Default.ArrowBack,
            tint = MaterialTheme.colors.onBackground,
            contentDescription = null
        )
    }
}

@Composable
fun ScreenWithMessage(
    modifier: Modifier = Modifier,
    message: Int
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = message),
            fontSize = MaterialTheme.dimen.textMedium.convertDpToSp(),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ScreenWithMessage(
    modifier: Modifier = Modifier,
    message: Int,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = message),
            fontSize = MaterialTheme.dimen.textMedium.convertDpToSp(),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        OutlinedButton(onClick = onClick) {
            Text(text = stringResource(id = R.string.try_again_btn_text))
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun AnimatedSnackBar(
    modifier: Modifier = Modifier,
    isConnected: Boolean,
    snackbarHostState: SnackbarHostState
) {
    SnackbarHost(
        modifier = modifier,
        hostState = snackbarHostState
    ) { snackbarData ->
        AnimatedVisibility(
            visible = isConnected,
            enter = slideInVertically() + expandVertically(),
            exit = slideOutVertically() + shrinkVertically()
        ) {
            Snackbar {
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = snackbarData.message,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    errorMessage: String?,
    errorStringResource: Int?
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        errorMessage?.let {
            Text(
                text = it,
                fontSize = MaterialTheme.dimen.textMedium.convertDpToSp(),
                fontWeight = FontWeight.Bold
            )
            return@Column
        }
        errorStringResource?.let {
            Text(
                text = stringResource(id = it),
                fontSize = MaterialTheme.dimen.textMedium.convertDpToSp(),
                fontWeight = FontWeight.Bold
            )
            return@Column
        }
    }
}