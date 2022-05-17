package com.migueldemollet.dressmeapp.screens.main.components.garments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.migueldemollet.dressmeapp.model.Garment
import com.migueldemollet.dressmeapp.screenWidth
import com.migueldemollet.dressmeapp.screens.main.GarmentListState
import com.migueldemollet.dressmeapp.screens.main.components.CardBox

@Composable
fun BoxComponent(
    state: GarmentListState,
    isRefreshing: Boolean,
    refreshData: () -> Unit,
    navController: NavHostController
) {
    val componentWidth = screenWidth / 3 - 10.dp
    val componentHeight = screenWidth / 3 - 10.dp
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn() {
            items(state.garments.windowed(3, 3, true)) { garment ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    garment.forEach {
                        CardBox(it, componentWidth, componentHeight, navController)
                    }
                }
            }
        }
    }

}