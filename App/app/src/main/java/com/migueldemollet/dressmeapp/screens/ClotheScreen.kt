package com.migueldemollet.dressmeapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.migueldemollet.dressmeapp.R
import com.migueldemollet.dressmeapp.model.Garment
import com.migueldemollet.dressmeapp.screenHeight
import com.migueldemollet.dressmeapp.screenWidth


@Composable
fun ClotheScreen(navController: NavController, garmentId: Int) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = { Text("DressMeApp") },
                backgroundColor = MaterialTheme.colors.primary

            )
        }
    ) {
        val img = painterResource(id = R.drawable.ic_launcher_background)
        val dress_img = painterResource(id = R.drawable.dress_me_app)
        val garment = Garment(0, img,"Nombre del producto", dress_img)
        ImageSection(garment)
    }
}

@Composable
fun ImageSection(garment: Garment) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        ProductBox(garment)
        val img = painterResource(id = R.drawable.ic_launcher_background)
        val dress_img = painterResource(id = R.drawable.dress_me_app)
        val garments: List<Garment> = listOf(
            Garment(0, img,"Nombre del producto", dress_img),
            Garment(1, img,"Nombre del producto", dress_img),
            Garment(2, img,"Nombre del producto", dress_img),
            Garment(3, img,"Nombre del producto", dress_img),
            Garment(4, img,"Nombre del producto", dress_img),
            Garment(5, img,"Nombre del producto", dress_img),
            Garment(6, img,"Nombre del producto", dress_img),
            Garment(7, img,"Nombre del producto", dress_img)
        )
        Spacer(modifier = Modifier.height(5.dp))
        ProductRecomendations(garments)
        TrySection()
    }
}
@Composable
fun CardBox2(garment: Garment, componentWidth: Dp, componentHeight: Dp){
    Card(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .width(componentWidth)
            .clickable(onClick = { /*TODO*/ }),
        shape = RoundedCornerShape(15.dp),
        elevation = 10.dp,
        backgroundColor = MaterialTheme.colors.primary

    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
        ) {
            Image(
                painter = garment.img,
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            Image(
                painter = garment.brand,
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
@Composable
fun ProductRecomendations(garments: List<Garment>) {
    LazyRow(
        modifier = Modifier
            .height(screenHeight/4 + screenHeight/20)
    ) {
        items(garments) { garment ->
            CardBox2(garment,screenWidth/3,screenHeight)
            //ScreenHeight de momento no se utiliza
        }
    }
}

@Composable
fun TrySection() {
    Row(
        modifier = Modifier.width(screenWidth)
            .fillMaxHeight()
            .padding(start =10.dp ,end = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                .fillMaxHeight()
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            ),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text("PROBAR")
        }
    }
}

@Composable
fun ProductBox(garment: Garment) {
    Card(
        modifier = Modifier
            .width(screenWidth)
            .padding(10.dp)
            .height(screenHeight/2),
        shape = RoundedCornerShape(15.dp),
        elevation = 10.dp,
        backgroundColor = MaterialTheme.colors.primary

    ) {
        Column(
            modifier = Modifier
                .height(screenHeight/2)
                .width(screenWidth)
        ) {
            Image(
                painter = garment.img,
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        }
    }
}