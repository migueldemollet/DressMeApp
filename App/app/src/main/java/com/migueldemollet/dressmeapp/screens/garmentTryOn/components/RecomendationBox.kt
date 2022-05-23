package com.migueldemollet.dressmeapp.screens.garmentTryOn.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.migueldemollet.dressmeapp.R
import com.migueldemollet.dressmeapp.model.Garment

@Composable
fun RecommendationBox(
    garment: Garment,
    componentWidth: Dp,
    componentHeight: Dp,
    onItemClick: (Garment) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .width(componentWidth)
            .clickable(onClick = { onItemClick(garment) }),
        shape = RoundedCornerShape(15.dp),
        elevation = 10.dp,
        backgroundColor = MaterialTheme.colors.primary

    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = garment.image,
                    placeholder = painterResource(id = R.drawable.load_image)
                ),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            Image(
                painter = rememberAsyncImagePainter(garment.brand),
                contentDescription = "",
                modifier = Modifier
                    .padding(4.dp)
                    .width(componentWidth / 2)
                    .clip(RoundedCornerShape(15.dp)),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                alpha = 0.5f
            )
        }
    }
}