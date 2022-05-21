package com.migueldemollet.dressmeapp.screens.main

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.migueldemollet.dressmeapp.R
import com.migueldemollet.dressmeapp.model.Filter
import com.migueldemollet.dressmeapp.navigation.AppScreens
import com.migueldemollet.dressmeapp.screenHeight
import com.migueldemollet.dressmeapp.screenWidth
import com.migueldemollet.dressmeapp.screens.main.components.filters.SearchView
import com.migueldemollet.dressmeapp.screens.main.components.filters.CardFilter
import com.migueldemollet.dressmeapp.screens.main.components.garments.BoxComponent
import com.migueldemollet.dressmeapp.ui.theme.DressMeAppTheme

private val filters: List<Filter> = listOf(
    Filter(0, "All"),
    Filter(1, "Tops"),
    Filter(2, "Bottoms"),
    Filter(3, "Dresses"),
    Filter(4, "Shoes"),
    Filter(5, "Accessories")
)

@Composable
fun MainScreen(
    state: GarmentListState,
    navController: NavHostController,
    isRefreshing: Boolean,
    refreshData: () -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(MaterialTheme.colors.background)

    val textState = remember { mutableStateOf(TextFieldValue("")) }
    Column {
        Logo()
        FilterSection(textState)
        BoxComponent(
            state = state,
            textState = textState,
            isRefreshing = isRefreshing,
            refreshData = refreshData,
            onItemClick = { garment ->
                navController.navigate(
                    AppScreens.GarmentTryOnScreen.route +
                            "/${garment.id}/${garment.color}/${garment.type}"
                )
            }
        )
    }
}

@Composable
fun Logo() {
    val logo = if (isSystemInDarkTheme()) {
        painterResource(id = R.drawable.dress_me_app_white)
    } else {
        painterResource(id = R.drawable.dress_me_app_black)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,

        ) {
        Image(
            painter = logo,
            contentDescription = "logo_image",
            modifier = Modifier
                .width(screenWidth / 4)
                .clickable(onClick = { TODO() })
        )
    }

}

@Composable
fun FilterSection(textState: MutableState<TextFieldValue>) {
    Column() {
        SearchView(textState)
        FilterComponent(filters = filters)
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

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreview() {
    DressMeAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            screenWidth = LocalConfiguration.current.screenWidthDp.dp
            screenHeight = LocalConfiguration.current.screenHeightDp.dp
            //MainScreen(navController)
        }
    }
}