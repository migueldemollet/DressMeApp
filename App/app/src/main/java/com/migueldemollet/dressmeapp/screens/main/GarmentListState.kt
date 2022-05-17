package com.migueldemollet.dressmeapp.screens.main

import com.migueldemollet.dressmeapp.model.Garment

data class GarmentListState(
    val isLoading: Boolean = false,
    val garments: List<Garment> = emptyList(),
    val error: String? = null
)
