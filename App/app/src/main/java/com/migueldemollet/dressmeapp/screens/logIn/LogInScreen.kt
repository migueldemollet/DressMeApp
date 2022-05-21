package com.migueldemollet.dressmeapp.screens.logIn

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.migueldemollet.dressmeapp.screenHeight
import com.migueldemollet.dressmeapp.screenWidth
import com.migueldemollet.dressmeapp.screens.logIn.components.AlternativeLogIn
import com.migueldemollet.dressmeapp.screens.logIn.components.LogInForm
import com.migueldemollet.dressmeapp.screens.logIn.components.SignUpButton
import com.migueldemollet.dressmeapp.screens.main.Logo
import com.migueldemollet.dressmeapp.ui.theme.DressMeAppTheme

@Composable
fun LogInScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(MaterialTheme.colors.background)
        val textState = remember { mutableStateOf(TextFieldValue("")) }
        val passwordState = remember { mutableStateOf(TextFieldValue("")) }
        Logo()
        LogInForm(
            textState = textState,
            passwordState = passwordState,
            navController = navController
        )
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Or",
            color = Color.LightGray
        )
        AlternativeLogIn()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 15.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignUpButton(navController = navController)
    }
}

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LogInScreenPreview() {
    DressMeAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            screenWidth = LocalConfiguration.current.screenWidthDp.dp
            screenHeight = LocalConfiguration.current.screenHeightDp.dp
            //LogInScreen()
        }
    }
}