package com.migueldemollet.dressmeapp.screens.main.components

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
//import com.migueldemollet.dressmeapp.ClotheActivity
import com.migueldemollet.dressmeapp.model.Garment

@Composable
fun CardBox(
    garment: Garment,
    componentWidth: Dp,
    omponentHeight: Dp,
    navController: NavHostController
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(end = 5.dp)
            .width(componentWidth)
            .height(omponentHeight)
            .clickable(onClick = {
                /*val intent = Intent(context, ClotheActivity::class.java)
                intent.putExtra("garmentId", garment.id)
                context.startActivity(intent)*/
                navController.navigate("garmetnTryOn/${garment.id}")
            }),
        shape = RoundedCornerShape(15.dp),
        elevation = 10.dp,
        backgroundColor = MaterialTheme.colors.primary

    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
        ) {
            Image(
                painter = rememberAsyncImagePainter(garment.image),
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
                    .height(omponentHeight / 2)
                    .clip(RoundedCornerShape(15.dp)),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                alpha = 0.5f
            )
        }
    }
}