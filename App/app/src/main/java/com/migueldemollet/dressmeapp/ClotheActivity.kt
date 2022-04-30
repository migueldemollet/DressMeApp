package com.migueldemollet.dressmeapp

import android.content.res.Configuration
import android.graphics.Color
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        horizontalAlignment = Alignment.CenterHorizontally,

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
        Spacer(modifier = Modifier.height(5.dp))
        ProductRecomendations(screenWidth = screenWidth, screenHeight = screenHeight, garments)
        TrySection(screenWidth,screenHeight)
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
                painter = garment.image,
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            Image(
                painter = garment.image2,
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
fun ProductRecomendations(screenWidth: Dp, screenHeight: Dp,garments: List<Garment>) {
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
fun ProductScreen(screenWidth: Dp, screenHeight: Dp, garment: Garment) {
    ImageSection(screenWidth,screenHeight,garment)

}


@Composable
fun TrySection(screenWidth: Dp,screenHeight: Dp) {
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
fun ProductBox(screenWidth: Dp, screenHeight: Dp, garment: Garment) {
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
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview2() {
    DressMeAppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
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
        ){
            val screenWidth = LocalConfiguration.current.screenWidthDp.dp
            val screenHeight = LocalConfiguration.current.screenHeightDp.dp
            //Voy a suponer que cuando clickemos en un producto y lo despleguemos, le pasemos la
            val garment = Garment("Nombre del producto","Producto muy chulo",painterResource(id = R.drawable.ic_launcher_background), painterResource(id = R.drawable.ic_launcher_background))
            ProductScreen(screenWidth = screenWidth, screenHeight = screenHeight,garment)
        }

    }
}