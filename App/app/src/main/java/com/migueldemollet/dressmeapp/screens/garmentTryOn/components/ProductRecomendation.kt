package com.migueldemollet.dressmeapp.screens.garmentTryOn.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.migueldemollet.dressmeapp.model.Garment
import com.migueldemollet.dressmeapp.screenHeight
import com.migueldemollet.dressmeapp.screenWidth

@Composable
fun ProductRecomendations(garments: List<Garment>) {
    LazyRow(
        modifier = Modifier
            .height(screenHeight / 4 + screenHeight / 20)
    ) {
        items(garments) { garment ->
            RecomendationBox(garment, screenWidth / 3, screenHeight)
            //ScreenHeight de momento no se utiliza
        }
    }
}