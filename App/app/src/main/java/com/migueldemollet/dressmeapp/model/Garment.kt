package com.migueldemollet.dressmeapp.model

import android.graphics.Bitmap
import androidx.compose.ui.graphics.painter.Painter
import com.google.firebase.storage.StorageReference

data class Garment(
    val id: String,
    val img: String,
    val description: String,
    val brand: String,
    val brand_name: String,
    val color: String,
    val type: String,
) {
    constructor() : this("", "", "", "", "", "", "")
}