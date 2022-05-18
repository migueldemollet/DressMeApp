package com.migueldemollet.dressmeapp.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class AppScreens(
    val route: String,
    val arguments: List<NamedNavArgument>
){
    object MainScreen : AppScreens(
        route = "main_screen",
        arguments = emptyList()
    )
    object GarmentTryOnScreen: AppScreens(
        route = "garment_try_on_screen",
        arguments = listOf(navArgument("garmentId") {
            type = NavType.StringType
        })
    )
}
