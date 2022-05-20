package com.migueldemollet.dressmeapp.screens.garmentTryOn.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.migueldemollet.dressmeapp.R
import com.migueldemollet.dressmeapp.model.Garment
import com.migueldemollet.dressmeapp.screenHeight
import com.migueldemollet.dressmeapp.screenWidth

@Composable
fun ProductBox(garment: Garment?) {
    Card(
        modifier = Modifier
            .width(screenWidth)
            .padding(10.dp)
            .height(screenHeight / 2),
        shape = RoundedCornerShape(15.dp),
        elevation = 10.dp,
        backgroundColor = MaterialTheme.colors.primary

    ) {
        Column(
            modifier = Modifier
                .height(screenHeight / 2)
                .width(screenWidth)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = garment?.image,
                    placeholder = painterResource(id = R.drawable.load_image)
                ),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        }
    }
}