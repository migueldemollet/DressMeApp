package com.migueldemollet.dressmeapp.screens.garmentTryOn.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.migueldemollet.dressmeapp.AnimatedShimmer
import com.migueldemollet.dressmeapp.model.Garment
import com.migueldemollet.dressmeapp.screenHeight
import com.migueldemollet.dressmeapp.screenWidth
import com.migueldemollet.dressmeapp.screens.main.GarmentListState

@Composable
fun ProductRecommendations(state: GarmentListState) {
    val garments = state.garments
    Box() {
        LazyRow(
            modifier = Modifier
                .height(screenHeight / 4 + screenHeight / 20)
        ) {
            items(garments) { garment ->
                RecommendationBox(garment, screenWidth / 3, screenHeight)
                //ScreenHeight de momento no se utiliza
            }
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}