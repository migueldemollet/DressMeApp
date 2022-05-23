package com.migueldemollet.dressmeapp.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.migueldemollet.dressmeapp.screens.garmentTryOn.GarmentTryOnScreen
import com.migueldemollet.dressmeapp.screens.garmentTryOn.GarmentTryOnViewModel
import com.migueldemollet.dressmeapp.screens.logIn.LogInScreen
import com.migueldemollet.dressmeapp.screens.logIn.LogInViewModel
import com.migueldemollet.dressmeapp.screens.main.GarmentListViewModel
import com.migueldemollet.dressmeapp.screens.main.MainScreen
import com.migueldemollet.dressmeapp.screens.signUp.SignUpViewModel
import com.migueldemollet.dressmeapp.screens.signUp.SignUpScreen

@ExperimentalMaterialApi
@Composable
fun AppNavigation(start: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = start) {
        composable(
            route = AppScreens.LogInScreen.route
        ) {
            val viewModel: LogInViewModel = hiltViewModel()
            if (viewModel.state.value.successLogin) {
                LaunchedEffect(key1 =Unit) {
                    navController.navigate(AppScreens.MainScreen.route) {
                        popUpTo(AppScreens.LogInScreen.route) { inclusive = true }
                    }
                }
            } else {
                LogInScreen(
                    state = viewModel.state.value,
                    onLogin = viewModel::login,
                    onLoginWithGoogle = viewModel::loginGoogle,
                    navController = navController,
                    onDismissDialog = viewModel::hideErrorDialog
                )
            }

        }

        composable(
            route = AppScreens.SignUpScreen.route
        ) {
            val viewModel: SignUpViewModel = hiltViewModel()
            if (viewModel.state.value.successRegister) {
                LaunchedEffect(key1 =Unit) {
                    navController.navigate(AppScreens.MainScreen.route) {
                        popUpTo(AppScreens.SignUpScreen.route) { inclusive = true }
                    }
                }
            } else {
                SignUpScreen(
                    state = viewModel.state.value,
                    onRegister = viewModel::signUp,
                    onRegisterWithGoogle = viewModel::signUpGoogle,
                    navController = navController,
                    onDismissDialog = viewModel::hideErrorDialog
                )
            }
        }

        composable(
            route = AppScreens.MainScreen.route
        ) {
            val viewModel: GarmentListViewModel = hiltViewModel()
            val state = viewModel.state.value
            val isRefreshing = viewModel.isRefreshing.collectAsState()
            MainScreen(
                state = state,
                navController = navController,
                isRefreshing = isRefreshing.value,
                refreshData = viewModel::getGarmentList
            )
        }
        composable(
            route = AppScreens.GarmentTryOnScreen.route + "/{garmentId}/{garmentColor}/{garmentType}",
        ) {
            val viewModel: GarmentTryOnViewModel = hiltViewModel()
            val state = viewModel.state.value
            val state2 = viewModel.state2.value
            val isRefreshing = viewModel.isRefreshing.collectAsState()
            GarmentTryOnScreen(
                state = state,
                state2 = state2,
                navController = navController,
                isRefreshing = isRefreshing.value,
                refreshData = viewModel::getGarment,
                refreshData2 = viewModel::getGarmentListFilters
            )
        }
    }
}