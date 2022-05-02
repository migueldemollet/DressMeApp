package com.migueldemollet.dressmeapp.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.migueldemollet.dressmeapp.R
import com.migueldemollet.dressmeapp.model.Filter
import com.migueldemollet.dressmeapp.model.Garment
import com.migueldemollet.dressmeapp.navigation.AppScreens
import com.migueldemollet.dressmeapp.screenWidth

private val filters: List<Filter> = listOf(
    Filter(0,"All"),
    Filter(1,"Tops"),
    Filter(2,"Bottoms"),
    Filter(3,"Dresses"),
    Filter(4,"Shoes"),
    Filter(5,"Accessories")
)

@Composable
fun MainScreen(navController: NavController) {
    Column() {
        FilterSection()

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
            Garment(7, img,"Nombre del producto", dress_img),
            Garment(8, img,"Nombre del producto", dress_img),
            Garment(9, img,"Nombre del producto", dress_img),
            Garment(10, img,"Nombre del producto", dress_img),
            Garment(11, img,"Nombre del producto", dress_img),
            Garment(12, img,"Nombre del producto", dress_img),
            Garment(13, img,"Nombre del producto", dress_img),
            Garment(14, img,"Nombre del producto", dress_img),
            Garment(15, img,"Nombre del producto", dress_img),
            Garment(16, img,"Nombre del producto", dress_img),
            Garment(17, img,"Nombre del producto", dress_img),
            Garment(18, img,"Nombre del producto", dress_img),
            Garment(19, img,"Nombre del producto", dress_img),
            Garment(20, img,"Nombre del producto", dress_img),
            Garment(21, img,"Nombre del producto", dress_img),
            Garment(22, img,"Nombre del producto", dress_img),
            Garment(23, img,"Nombre del producto", dress_img),
            Garment(24, img,"Nombre del producto", dress_img),
            Garment(25, img,"Nombre del producto", dress_img),
            Garment(26, img,"Nombre del producto", dress_img),
        )
        BoxComponent(navController = navController, garments = garments)
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
                // if theme is dark, use white text
                color = if (isSystemInDarkTheme()) {
                    Color.White
                } else {
                    Color.Black
                },
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
fun FilterComponent(filters: List<Filter>) {
    LazyRow() {
        items(filters) { filter ->
            CardFilter(filter)
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun CardFilter(filter: Filter) {
    Button(
        onClick = {

        },
        modifier = Modifier.padding(10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        ),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(text = filter.text)
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
fun BoxComponent(navController: NavController, garments: List<Garment>) {
    val widthComponent = screenWidth / 3 - 10.dp
    LazyColumn() {
        items(garments.windowed(3,3, true)) { garment ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                garment.forEach {
                    CardBox(navController, it, widthComponent)
                }
            }
        }
    }
}

@Composable
fun CardBox(navController: NavController, garment: Garment, componentWidth: Dp){
    Card(
        modifier = Modifier
            .padding(end = 5.dp)
            .width(componentWidth)
            .clickable(onClick = {
                navController.navigate(route = AppScreens.ClotheScreen.route + "/" + garment.id)
            }),
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