package com.migueldemollet.dressmeapp.screens.logIn.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.migueldemollet.dressmeapp.R
import com.migueldemollet.dressmeapp.navigation.AppScreens
import com.migueldemollet.dressmeapp.screenWidth


@Composable
fun LogInForm(
    textState: MutableState<TextFieldValue>,
    passwordState: MutableState<TextFieldValue>,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .width(screenWidth),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserField(textState)
        Spacer(modifier = Modifier.height(15.dp))
        PasswordField(passwordState)
        Spacer(modifier = Modifier.height(15.dp))
        SignUpButton(textState, passwordState, navController)
    }
}

@Composable
private fun UserField(state: MutableState<TextFieldValue>) {
    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 35.dp)
            .width(screenWidth),
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        textStyle = TextStyle(
            color = if (isSystemInDarkTheme()) {
                Color.White
            } else {
                Color.Black
            },
            fontSize = 18.sp
        ),
        placeholder = {
            Text(
                text = "Email",
                style = TextStyle(
                    color = MaterialTheme.colors.onSurface
                )
            )
        },
        shape = RoundedCornerShape(15.dp),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.White,
            cursorColor = Color.Black,
            leadingIconColor = Color.Black,
            trailingIconColor = Color.Black,
            backgroundColor = MaterialTheme.colors.background,
            focusedBorderColor = MaterialTheme.colors.onSurface,
            unfocusedBorderColor = MaterialTheme.colors.onSurface,
        )

    )
}

@Composable
private fun PasswordField(state: MutableState<TextFieldValue>) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 35.dp)
            .width(screenWidth),
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        textStyle = TextStyle(
            color = if (isSystemInDarkTheme()) {
                Color.White
            } else {
                Color.Black
            },
            fontSize = 18.sp
        ),
        placeholder = {
            Text(
                text = "Password",
                style = TextStyle(
                    color = MaterialTheme.colors.onSurface
                )
            )
        },
        shape = RoundedCornerShape(15.dp),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.White,
            cursorColor = Color.Black,
            leadingIconColor = Color.Black,
            trailingIconColor = Color.Black,
            backgroundColor = MaterialTheme.colors.background,
            focusedBorderColor = MaterialTheme.colors.onSurface,
            unfocusedBorderColor = MaterialTheme.colors.onSurface,
        ),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible)
                painterResource(id = R.drawable.visibility)
            else painterResource(id = R.drawable.visibility_off)

            // Please provide localized description for accessibility services
            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = {passwordVisible = !passwordVisible}){
                Icon(
                    painter = image,
                    contentDescription = description,
                    tint = if (isSystemInDarkTheme()) {
                        Color.White
                    } else {
                        Color.Black
                    }
                )
            }
        }

    )
}

@Composable
private fun SignUpButton(
    textState: MutableState<TextFieldValue>,
    passwordState: MutableState<TextFieldValue>,
    navController: NavController
) {
    TextButton(
        modifier = Modifier
            .padding(horizontal = 35.dp)
            .width(screenWidth),
        onClick = {
            logIn(
                textState.value.text,
                passwordState.value.text,
                navController = navController
            ) },
        enabled = textState.value.text.isNotEmpty() && passwordState.value.text.isNotEmpty(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary
        ),
        shape = RoundedCornerShape(15.dp),
    ) {
        Text(text = "Log In")
    }
}

private fun logIn(
    email: String,
    password: String,
    navController: NavController
) {
    FirebaseAuth.getInstance()
        .signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { result ->
            if (result.isSuccessful) {
                navController.navigate(AppScreens.MainScreen.route) {
                    popUpTo(AppScreens.LogInScreen.route)
                }
            } else {
                TODO()
            }
        }
}


