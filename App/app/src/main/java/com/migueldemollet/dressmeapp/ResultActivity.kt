package com.migueldemollet.dressmeapp


import android.content.ContentValues
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.migueldemollet.dressmeapp.ui.theme.DressMeAppTheme
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

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
                    val systemUiController = rememberSystemUiController()
                    systemUiController.setStatusBarColor(MaterialTheme.colors.primary)
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
                                contentDescription = "Go back"
                            )
                        }
                    },
                    title = { Text("DressMeApp") },
                    backgroundColor = MaterialTheme.colors.primary,
                    actions = {
                        IconButton(onClick = { SaveImage(image) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.download),
                                contentDescription = "Save",
                                tint = Color.White
                            )
                        }
                    }
                )
            }
        ) {
            Column() {
                Image(
                    bitmap = image.asImageBitmap(),
                    contentDescription = "Image",
                    alignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.90f),
                    contentScale = ContentScale.Fit
                )
                Row(
                    modifier = Modifier
                        .width(screenWidth)
                        .fillMaxHeight()
                        .padding(start = 10.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Button(
                        onClick = {
                            ShareImage(image)
                        },
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp)
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = MaterialTheme.colors.onPrimary
                        ),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Text("Share")
                    }
                }
            }
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

    private fun SaveImage(bitmap: Bitmap) {
        val filename = "IMG_${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.contentResolver.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/DressMeApp")
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory("/Pictures/DressMeApp")
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this, "Saved Image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun ShareImage(bitmap: Bitmap) {
        val path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "IMG_${System.currentTimeMillis()}", null)
        val uri = Uri.parse (path)

        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("image/jpeg")
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(intent, "Share images to.."))
    }

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ResultScreenPreview() {
    DressMeAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            screenWidth = LocalConfiguration.current.screenWidthDp.dp
            screenHeight = LocalConfiguration.current.screenHeightDp.dp
            ResultScreen(image = getImage()!!)
        }
    }
}
}