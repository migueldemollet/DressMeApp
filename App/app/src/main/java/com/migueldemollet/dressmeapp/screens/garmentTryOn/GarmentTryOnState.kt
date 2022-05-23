package com.migueldemollet.dressmeapp.screens.garmentTryOn

import com.migueldemollet.dressmeapp.model.Garment

data class GarmentTryOnState(
    val isLoading: Boolean = false,
    val garment: Garment? = null,
    val error: String = ""
)
