package com.migueldemollet.dressmeapp


import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.asImageBitmap
import com.migueldemollet.dressmeapp.ui.theme.DressMeAppTheme

@ExperimentalMaterialApi
class ResultActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DressMeAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                navigationIcon = {
                                    IconButton(onClick = {
                                        this.finish()
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
                        val img = intent.getParcelableExtra<Bitmap>("image")
                        if (img != null) {
                            Image(
                                bitmap = img.asImageBitmap(),
                                contentDescription = "Result",
                            )
                        }
                    }
                }
            }
        }
    }

}