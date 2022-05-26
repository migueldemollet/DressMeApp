package com.migueldemollet.dressmeapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.migueldemollet.dressmeapp.navigation.AppNavigation
import com.migueldemollet.dressmeapp.navigation.AppScreens
import com.migueldemollet.dressmeapp.screens.load.LoadActivity
import com.migueldemollet.dressmeapp.screens.logIn.LogInViewModel
import com.migueldemollet.dressmeapp.screens.signUp.SignUpViewModel
import com.migueldemollet.dressmeapp.ui.theme.DressMeAppTheme
import dagger.hilt.android.AndroidEntryPoint

var screenWidth = 0.dp
var screenHeight = 0.dp
var isCameraSelected = false
var imageUri: Uri? = null
var garmentId: String? = null
var imgResult: String? = null

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_DressMeApp)
        super.onCreate(savedInstanceState)
        setContent {
            DressMeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val systemUiController = rememberSystemUiController()
                    systemUiController.setStatusBarColor(MaterialTheme.colors.background)
                    screenWidth = LocalConfiguration.current.screenWidthDp.dp
                    screenHeight = LocalConfiguration.current.screenHeightDp.dp
                    val start = if (Firebase.auth.currentUser != null) {
                        AppScreens.MainScreen.route
                    } else {
                        AppScreens.LogInScreen.route
                    }
                    AppNavigation(start)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            1 -> {
                val viewModel: LogInViewModel by viewModels()
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                viewModel.handleLogInResult(task)
            }
            2 -> {
                val viewModel: SignUpViewModel by viewModels()
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                viewModel.handleSignUpResult(task)
            }
            else -> {
                if (resultCode == Activity.RESULT_OK) {
                    val intent = Intent(this, LoadActivity::class.java)
                    intent.putExtra("uri", imageUri)
                    intent.putExtra("garmentId", garmentId)
                    this.startActivity(intent)
                }
            }
        }
    }
}
