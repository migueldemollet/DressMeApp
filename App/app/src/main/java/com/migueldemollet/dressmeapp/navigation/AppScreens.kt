package com.migueldemollet.dressmeapp.navigation

sealed class AppScreens(val route: String) {
    object MainScreen : AppScreens("main_screen")
    object ClotheScreen : AppScreens("clothe_screen")
}
