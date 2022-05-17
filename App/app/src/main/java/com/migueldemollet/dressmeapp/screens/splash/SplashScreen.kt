package com.migueldemollet.dressmeapp

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.migueldemollet.dressmeapp.ui.theme.DressMeAppTheme
import kotlinx.coroutines.delay

@Composable
fun SplashSscreen() {
    Splash()
}

@Composable
fun Splash() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val logo = if (isSystemInDarkTheme()) {
            painterResource(id = R.drawable.dress_me_app_white)
        } else {
            painterResource(id = R.drawable.dress_me_app_black)
        }
        Image(painter = logo, contentDescription = "logo")
    }
    Column(
        modifier = Modifier
            .padding(bottom = 15.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "from",
            style = MaterialTheme.typography.h6,
            color = Color.Gray.copy(alpha = 0.7f)
        )
    }
}

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreview() {
    DressMeAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val systemUiController = rememberSystemUiController()
            systemUiController.setStatusBarColor(MaterialTheme.colors.background)
            Splash()
        }
    }
}