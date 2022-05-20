package com.migueldemollet.dressmeapp.screens.garmentTryOn

import android.content.Context
import android.net.Uri
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.migueldemollet.dressmeapp.BuildConfig
import com.migueldemollet.dressmeapp.screens.garmentTryOn.components.SheetLayout
import com.migueldemollet.dressmeapp.screens.main.GarmentListState
import java.io.File

@Composable
fun GarmentTryOnScreen(
    state: GarmentTryOnState,
    state2: GarmentListState,
    navController: NavHostController,
    isRefreshing: Boolean,
    refreshData: (String) -> Unit,
    refreshData2: (String, String, String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }) {
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
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(MaterialTheme.colors.primary)
        val garment = state.garment
        SheetLayout(garment, state2, navController)

    }
}

fun createTmpFile(context: Context): Uri {
    val imageFile = File(context.cacheDir, "tmp_image.jpg")
    imageFile.createNewFile()
    return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", imageFile)
}