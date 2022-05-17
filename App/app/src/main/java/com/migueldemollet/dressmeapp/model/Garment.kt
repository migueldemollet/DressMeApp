package com.migueldemollet.dressmeapp.model

data class Garment(
    val id: String,
    val image: String,
    val description: String,
    val brand: String,
    val brand_name: String,
    val color: String,
    val type: String,
) {
    constructor() : this("", "", "", "", "", "", "")
}