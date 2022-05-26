package com.migueldemollet.dressmeapp.screens.main.components.garments

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.migueldemollet.dressmeapp.model.Garment
import com.migueldemollet.dressmeapp.R

@Composable
fun CardBox(
    garment: Garment,
    componentWidth: Dp,
    componentHeight: Dp,
    onItemClick: (Garment) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(end = 5.dp)
            .width(componentWidth)
            .height(componentHeight)
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
                    model = garment.image_low,
                    placeholder = painterResource(id =R.drawable.load_image),
                ),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            Image(
                painter = rememberAsyncImagePainter(
                    model = garment.brand,
                ),
                contentDescription = "",
                modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(15.dp)),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                alpha = 0.5f
            )
        }
    }
}