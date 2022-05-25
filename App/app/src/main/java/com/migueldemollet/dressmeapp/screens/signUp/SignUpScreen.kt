package com.migueldemollet.dressmeapp.screens.signUp

import android.content.Context
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
import com.migueldemollet.dressmeapp.screens.logIn.components.EventDialog
import com.migueldemollet.dressmeapp.screens.main.components.Logo
import com.migueldemollet.dressmeapp.screens.signUp.components.AlternativeSignUp
import com.migueldemollet.dressmeapp.screens.signUp.components.LogInButton
import com.migueldemollet.dressmeapp.screens.signUp.components.SignUpForm
import com.migueldemollet.dressmeapp.ui.theme.DressMeAppTheme

@Composable
fun SignUpScreen(
    state: SignUpState,
    onRegister: (String, String) -> Unit,
    onRegisterWithGoogle: (Context) -> Unit,
    onDismissDialog: () -> Unit,
    navController: NavController,
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
        SignUpForm(
            textState = textState,
            passwordState = passwordState,
            navController = navController,
            onRegister = onRegister,
        )
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Or",
            color = Color.LightGray
        )
        AlternativeSignUp(onRegisterWithGoogle = onRegisterWithGoogle)

        if(state.errorMessage != null){
            EventDialog(
                errorMessage = state.errorMessage,
                onDismiss = onDismissDialog
            )
        }

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 15.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogInButton(navController = navController)
    }
}

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignUpScreenPreview() {
    DressMeAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            screenWidth = LocalConfiguration.current.screenWidthDp.dp
            screenHeight = LocalConfiguration.current.screenHeightDp.dp
            //SignUpScreen()
        }
    }
}