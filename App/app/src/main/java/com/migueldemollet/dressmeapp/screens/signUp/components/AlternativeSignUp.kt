package com.migueldemollet.dressmeapp.screens.signUp.components

import android.provider.Settings.Global.getString
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.migueldemollet.dressmeapp.R
import com.migueldemollet.dressmeapp.screenWidth

@Composable
fun AlternativeSignUp() {
    GoogleLogin()
    Spacer(modifier = Modifier.height(15.dp))
    FacebookLogin()
}

@Composable
private fun GoogleLogin() {
    TextButton(
        modifier = Modifier
            .padding(horizontal = 35.dp)
            .width(screenWidth),
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(219, 68,55)
        ),
        shape = RoundedCornerShape(15.dp),
    ) {
        Text(
            text = "Sign Up with Google",
            color = Color.White
        )

    }
}

@Composable
private fun FacebookLogin() {
    Button(
        modifier = Modifier
            .padding(horizontal = 35.dp)
            .width(screenWidth),
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(
            backgroundColor= Color(59, 89, 152)
        ),
        shape = RoundedCornerShape(15.dp),
    ) {
        Text(
            text = "Sign Up with Facebook",
            color = Color.White
        )
    }
}