package com.migueldemollet.dressmeapp.screens.main

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
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
import com.migueldemollet.dressmeapp.screens.main.components.LeftMenu
import com.migueldemollet.dressmeapp.screens.main.components.filters.SearchView
import com.migueldemollet.dressmeapp.screens.main.components.filters.CardFilter
import com.migueldemollet.dressmeapp.screens.main.components.garments.BoxComponent
import com.migueldemollet.dressmeapp.ui.theme.DressMeAppTheme
import kotlinx.coroutines.launch

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
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "DressMeApp")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {

                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        },
                    ) {
                        Icon(
                            Icons.Rounded.Menu,
                            contentDescription = ""
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.primary)
        },


        drawerContent = { LeftMenu(navController = navController) },

    ) {
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(MaterialTheme.colors.primary)

        val textState = remember { mutableStateOf(TextFieldValue("")) }
        Column {
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
}

@Composable
fun FilterSection(textState: MutableState<TextFieldValue>) {
    Column() {
        SearchView(textState)
        //FilterComponent(filters = filters)
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