package com.migueldemollet.dressmeapp


import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.migueldemollet.dressmeapp.ui.theme.DressMeAppTheme

@ExperimentalMaterialApi
class ResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DressMeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val img = getImage()!!
                    val garmentId = intent.getIntExtra("garmentId", 0)
                    ResultScreen(image = img)
                }
            }
        }
    }

    @Composable
    private fun ResultScreen(image: Bitmap) {
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
            Image(
                bitmap = image.asImageBitmap(),
                contentDescription = "Image",
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.45f)
                    .padding(top = 10.dp),
                contentScale = ContentScale.Fit
            )
        }
    }


    private fun getImage(): Bitmap? {
        val img = intent.getParcelableExtra<Bitmap>("image")
        if (img != null) { return img }

        val url = intent.getParcelableExtra<Uri>("url")
        var img2: Bitmap? = null
        url?.let {
            img2 = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(this.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(this.contentResolver, it)
                ImageDecoder.decodeBitmap(source)
            }
        }
        return img2
    }

}