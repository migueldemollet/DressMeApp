package com.migueldemollet.dressmeapp.screens.main.components.garments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.migueldemollet.dressmeapp.AnimatedShimmer
import com.migueldemollet.dressmeapp.SplashSscreen
import com.migueldemollet.dressmeapp.model.Garment
import com.migueldemollet.dressmeapp.screenWidth
import com.migueldemollet.dressmeapp.screens.main.GarmentListState

@Composable
fun BoxComponent(
    state: GarmentListState,
    isRefreshing: Boolean,
    refreshData: () -> Unit,
    onItemClick: (Garment) -> Unit
) {
    val componentWidth = screenWidth / 3 - 10.dp
    val componentHeight = screenWidth / 3 - 10.dp
    Box(modifier = Modifier.fillMaxSize()) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = refreshData
        ) {
            LazyColumn() {
                items(state.garments.windowed(3, 3, true)) { garment ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        garment.forEach {
                            CardBox(it, componentWidth, componentHeight, onItemClick = onItemClick)
                        }
                    }
                }
            }
        }
        if (state.isLoading) {
            AnimatedShimmer(screenWidth = screenWidth)
        }
    }
}