package com.migueldemollet.dressmeapp

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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migueldemollet.dressmeapp.ui.theme.DressMeAppTheme
import java.util.logging.Filter


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
                    MainScreen()
                }
            }
        }
    }
}

data class Garment(val title: String, val description: String, val image: Painter)

@Composable
fun MainScreen() {
    Column() {
        FilterSection()

        val img = painterResource(id = R.drawable.ic_launcher_background)
        val garments: List<Garment> = listOf(
            Garment("Camiseta", "Camistea larga", img),
            Garment("Camiseta", "Camistea larga", img),
            Garment("Camiseta", "Camistea larga", img),
            Garment("Camiseta", "Camistea larga", img),
            Garment("Camiseta", "Camistea larga", img),
            Garment("Camiseta", "Camistea larga", img),
            Garment("Camiseta", "Camistea larga", img),
            Garment("Camiseta", "Camistea larga", img),
            Garment("Camiseta", "Camistea larga", img),
            Garment("Camiseta", "Camistea larga", img),
            Garment("Camiseta", "Camistea larga", img),
            Garment("Camiseta", "Camistea larga", img),
        )
        BoxComponent(garments = garments)
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
fun BoxComponent(garments: List<Garment>) {
    LazyColumn(modifier = Modifier.padding(10.dp)) {
        items(garments.windowed(2,2, true)) { garment ->
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                garment.forEach {
                    CardBox(it)
                }
            }
        }
    }
}

@Composable
fun CardBox(garment: Garment){
    Card(
        modifier = Modifier
            .padding(15.dp)
            .width(150.dp)
            .clickable(onClick = { /*TODO*/ }),
        shape = RoundedCornerShape(15.dp),
        elevation = 10.dp,
        backgroundColor = MaterialTheme.colors.primary

    ) {
        Column() {
            Image(
                painter = garment.image,
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            Text(
                text = garment.title,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DressMeAppTheme {
        MainScreen()
    }
}