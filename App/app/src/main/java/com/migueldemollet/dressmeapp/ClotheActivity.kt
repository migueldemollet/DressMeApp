package com.migueldemollet.dressmeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.migueldemollet.dressmeapp.ui.theme.DressMeAppTheme


class ClotheActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DressMeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
                    val garment = Garment("Nombre del producto","Producto muy chulo",painterResource(id = R.drawable.ic_launcher_background), painterResource(id = R.drawable.ic_launcher_background))
                    ProductScreen(screenWidth,screenHeight,garment)
                }
            }
        }
    }
}

@Composable
fun ImageSection(screenWidth: Dp, screenHeight: Dp,garment: Garment) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProductBox(screenWidth = screenWidth, screenHeight = screenHeight, garment)
        val img = painterResource(id = R.drawable.ic_launcher_background)
        val dress_img = painterResource(id = R.drawable.dress_me_app)
        val garments: List<Garment> = listOf(
            Garment("Camiseta", "Camistea larga", img, dress_img),
            Garment("Camiseta", "Camistea larga", img, dress_img),
            Garment("Camiseta", "Camistea larga", img, dress_img),
            Garment("Camiseta", "Camistea larga", img, dress_img),
            Garment("Camiseta", "Camistea larga", img, dress_img),
            Garment("Camiseta", "Camistea larga", img, dress_img),
            Garment("Camiseta", "Camistea larga", img, dress_img),
            Garment("Camiseta", "Camistea larga", img, dress_img),
            Garment("Camiseta", "Camistea larga", img, dress_img),
        )
        ProductRecomendations(screenWidth = screenWidth, screenHeight = screenHeight, garments)
        TrySection(screenWidth,screenHeight)
    }
}

@Composable
fun ProductRecomendations(screenWidth: Dp, screenHeight: Dp,garments: List<Garment>) {
    LazyRow(
        modifier = Modifier
            .height(200.dp)
    ) {
        items(garments) { garment ->
            CardBox(garment,screenWidth/3,screenHeight)
            //ScreenHeight de momento no se utiliza
        }
    }
}
@Composable
fun ProductScreen(screenWidth: Dp, screenHeight: Dp, garment: Garment) {
    ImageSection(screenWidth,screenHeight,garment)

}
@Composable
fun TryButton(text: String) {


}

@Composable
fun TrySection(screenWidth: Dp,screenHeight: Dp) {
    Row(
        modifier = Modifier.width(screenWidth),
        horizontalArrangement = Arrangement.Center
    )
    {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(10.dp)
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
fun ProductBox(screenWidth: Dp, screenHeight: Dp, garment: Garment) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(screenWidth)
            .height(screenHeight/2)
            .clickable(onClick = { /*TODO*/ }),
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
                painter = garment.image,
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        }
    }
}



@Preview(showSystemUi = true)
@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    DressMeAppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("DressMeApp") }
                )
            }
        ){
            val screenWidth = LocalConfiguration.current.screenWidthDp.dp
            val screenHeight = LocalConfiguration.current.screenHeightDp.dp
            //Voy a suponer que cuando clickemos en un producto y lo despleguemos, le pasemos la
            val garment = Garment("Nombre del producto","Producto muy chulo",painterResource(id = R.drawable.ic_launcher_background), painterResource(id = R.drawable.ic_launcher_background))
            ProductScreen(screenWidth = screenWidth, screenHeight = screenHeight,garment)
        }

    }
}