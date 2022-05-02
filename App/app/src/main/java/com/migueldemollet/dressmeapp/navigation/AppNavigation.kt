package com.migueldemollet.dressmeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.migueldemollet.dressmeapp.screens.MainScreen
import com.migueldemollet.dressmeapp.screens.ClotheScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.MainScreen.route) {
        composable(
            route = AppScreens.MainScreen.route) {
            MainScreen(navController)
        }
        composable(
            route = AppScreens.ClotheScreen.route + "/{garmentId}",
            arguments = listOf(navArgument(name = "garmentId") {type = NavType.IntType})) {
                ClotheScreen(navController, it.arguments?.getInt("garmentId")!!)
        }
    }
}