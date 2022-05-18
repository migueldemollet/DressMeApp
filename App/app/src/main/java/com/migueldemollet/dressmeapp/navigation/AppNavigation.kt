package com.migueldemollet.dressmeapp.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.migueldemollet.dressmeapp.screens.garmentTryOn.GarmentTryOnScreen
import com.migueldemollet.dressmeapp.screens.garmentTryOn.GarmentTryOnViewModel
import com.migueldemollet.dressmeapp.screens.main.GarmentListViewModel
import com.migueldemollet.dressmeapp.screens.main.MainScreen

@ExperimentalMaterialApi
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.MainScreen.route) {
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