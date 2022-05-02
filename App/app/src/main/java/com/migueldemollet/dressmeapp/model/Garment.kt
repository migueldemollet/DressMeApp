package com.migueldemollet.dressmeapp.model

import androidx.compose.ui.graphics.painter.Painter

data class Garment(
    val id: Int,
    val img: Painter,
    val title: String,
    val brand: Painter
)