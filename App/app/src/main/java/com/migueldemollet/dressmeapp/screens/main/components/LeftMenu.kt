package com.migueldemollet.dressmeapp.screens.main.components

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.migueldemollet.dressmeapp.R
import com.migueldemollet.dressmeapp.navigation.AppScreens
import com.migueldemollet.dressmeapp.screenHeight
import com.migueldemollet.dressmeapp.screenWidth
import com.migueldemollet.dressmeapp.ui.theme.DressMeAppTheme

@Composable
fun LeftMenu(
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Logo()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    Firebase.auth.signOut()
                    navController.navigate(AppScreens.LogInScreen.route) {
                        popUpTo(0)
                    }

                })
                .height(45.dp)
                .background(MaterialTheme.colors.background)
                .padding(start = 10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.log_out),
                contentDescription = "Log out",
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(35.dp)
                    .width(35.dp)
            )
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = "Log out",
                fontSize = 18.sp,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = "From the DressMeApp team we want to thank you for using our app.",
            color = Color.Gray.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Logo() {
    val logo = if (isSystemInDarkTheme()) {
        painterResource(id = R.drawable.dress_me_app_white)
    } else {
        painterResource(id = R.drawable.dress_me_app_black)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,

        ) {
        Image(
            painter = logo,
            contentDescription = "logo_image",
            modifier = Modifier
                .width(screenWidth / 4)
        )
    }

}

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LeftMenuPreview() {
    DressMeAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            screenWidth = LocalConfiguration.current.screenWidthDp.dp
            screenHeight = LocalConfiguration.current.screenHeightDp.dp
            //LeftMenu()
        }
    }
}