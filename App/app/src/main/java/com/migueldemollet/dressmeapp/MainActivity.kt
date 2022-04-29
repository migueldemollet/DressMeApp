package com.migueldemollet.dressmeapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migueldemollet.dressmeapp.ui.theme.DressMeAppTheme


private val filters: List<String> = listOf(
    "All",
    "Hats",
    "Shirts",
    "Pants",
    "Shoes",
    "Accessories"
)


class MainActivity : ComponentActivity() {
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
                    MainScreen(screenWidth, screenHeight)
                }
            }
        }
    }
}

data class Garment(val title: String, val description: String, val image: Painter, val image2: Painter)

@Composable
fun MainScreen(screenWidth: Dp, screenHeight: Dp) {
    Column() {
        FilterSection()

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
            Garment("Camiseta", "Camistea larga", img, dress_img),
            Garment("Camiseta", "Camistea larga", img, dress_img),
            Garment("Camiseta", "Camistea larga", img, dress_img),
            Garment("Camiseta", "Camistea larga", img, dress_img),
            Garment("Camiseta", "Camistea larga", img, dress_img),
            Garment("Camiseta", "Camistea larga", img, dress_img),
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
        BoxComponent(garments = garments, screenWidth = screenWidth, screenHeight = screenHeight)
    }
}

@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    Column() {
        OutlinedTextField(
            value = state.value,
            onValueChange = { value ->
                state.value = value
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 18.sp
            ),
            placeholder = {
                Text(
                    text = "What are you looking for?",
                    style = TextStyle(
                        color = MaterialTheme.colors.onSurface
                    )
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp),
                    tint = MaterialTheme.colors.onSurface
                )
            },
            trailingIcon = {
                if (state.value != TextFieldValue("")) {
                    IconButton(
                        onClick = {
                            state.value =
                                TextFieldValue("")
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(15.dp)
                                .size(24.dp),
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.White,
                cursorColor = Color.Black,
                leadingIconColor = Color.Black,
                trailingIconColor = Color.Black,
                backgroundColor = MaterialTheme.colors.background,
                focusedBorderColor = MaterialTheme.colors.onSurface,
                unfocusedBorderColor = MaterialTheme.colors.onSurface,
            )
        )
    }
}

@Composable
fun FilterComponent(filters: List<String>) {
    LazyRow() {
        items(filters) { filter ->
            CardFilter(filter)
        }
    }
}

@Composable
fun CardFilter(text: String) {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.padding(10.dp),
        colors = buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        ),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(text = text)
    }

}

@Composable
fun FilterSection(){
    Column(){
        val textState = remember { mutableStateOf(TextFieldValue("")) }
        SearchView(textState)
        FilterComponent(filters = filters)
    }
}

@Composable
fun BoxComponent(garments: List<Garment>, screenWidth: Dp, screenHeight: Dp) {
    val widthComponent = screenWidth / 3 - 10.dp
    LazyColumn() {
        items(garments.windowed(3,3, true)) { garment ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                garment.forEach {
                    CardBox(it, widthComponent, screenHeight)
                }
            }
        }
    }
}

@Composable
fun CardBox(garment: Garment, componentWidth: Dp, componentHeight: Dp){
    Card(
        modifier = Modifier
            .padding(end = 5.dp)
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

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    DressMeAppTheme {
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
        MainScreen(screenWidth, screenHeight)
    }
}