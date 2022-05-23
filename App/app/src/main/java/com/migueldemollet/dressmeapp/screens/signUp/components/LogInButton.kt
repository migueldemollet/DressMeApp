package com.migueldemollet.dressmeapp.screens.signUp.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.migueldemollet.dressmeapp.navigation.AppScreens
import com.migueldemollet.dressmeapp.screenWidth

@Composable
fun LogInButton(
    navController: NavController,
) {
    TextButton(
        modifier = Modifier
            .padding(horizontal = 35.dp)
            .width(screenWidth),
        onClick = {
            navController.navigate(AppScreens.LogInScreen.route) {
                popUpTo(0)
            }
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
        ),
        shape = RoundedCornerShape(15.dp),
    ) {
        Text(
            text = "I already have an account",
            color = MaterialTheme.colors.onPrimary
        )
    }
}