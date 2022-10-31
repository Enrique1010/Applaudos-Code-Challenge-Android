package com.erapps.moviesinfoapp.ui.screens.shared

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.filters.MediumTest
import com.erapps.moviesinfoapp.ui.shared.getNetworkStatus
import com.erapps.moviesinfoapp.ui.theme.MoviesInfoAppTheme
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
class TestGetNetworkStatus {
    @get:Rule
    val composeRule = createComposeRule()

    private var status: Boolean = false

    @Before
    fun setUp() {
        composeRule.setContent {
            MoviesInfoAppTheme {
                status = getNetworkStatus()
            }
        }
    }

    //if no internet false
    @Test
    fun netWorkStatusIsWork() {

        assertThat(status).isTrue()
    }
}