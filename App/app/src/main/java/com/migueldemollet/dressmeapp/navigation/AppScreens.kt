package com.migueldemollet.dressmeapp.navigation

import androidx.navigation.NamedNavArgument

sealed class AppScreens(
    val route: String,
    val arguments: List<NamedNavArgument>
){
    object MainScreen : AppScreens("main_screen", emptyList())
    object GarmentTryOnScreen: AppScreens("garment_try_on_screen", emptyList())
}
