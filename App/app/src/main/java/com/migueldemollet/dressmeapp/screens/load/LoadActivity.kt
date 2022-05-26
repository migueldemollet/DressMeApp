package com.migueldemollet.dressmeapp.screens.load

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.migueldemollet.dressmeapp.AnimatedShimmer
import com.migueldemollet.dressmeapp.imgResult
import com.migueldemollet.dressmeapp.screenWidth
import com.migueldemollet.dressmeapp.screens.result.ResultActivity
import com.migueldemollet.dressmeapp.ui.theme.DressMeAppTheme
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class LoadActivity : ComponentActivity() {
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
                    systemUiController.setStatusBarColor(MaterialTheme.colors.background)
                    val img = getImage()!!

                    val imgResized = getScaledDownBitmap(img, 1000, false)
                    sendPostRequest(imgResized, intent.getStringExtra("garmentId")!!)
                    AnimatedShimmer(screenWidth = screenWidth, screen = 2)
                }
            }
        }
    }

    private fun getScaledDownBitmap(
        bitmap: Bitmap,
        threshold: Int,
        isNecessaryToKeepOrig: Boolean
    ): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        var newWidth = width
        var newHeight = height
        if (width > height && width > threshold) {
            newWidth = threshold
            newHeight = (height * newWidth.toFloat() / width).toInt()
        }
        if (width > height && width <= threshold) {
            return bitmap
        }
        if (width < height && height > threshold) {
            newHeight = threshold
            newWidth = (width * newHeight.toFloat() / height).toInt()
        }
        if (width < height && height <= threshold) {
            return bitmap
        }
        if (width == height && width > threshold) {
            newWidth = threshold
            newHeight = newWidth
        }
        return if (width == height && width <= threshold) {
            bitmap
        } else getResizedBitmap(bitmap, newWidth, newHeight, isNecessaryToKeepOrig)
    }

    private fun getResizedBitmap(
        bm: Bitmap,
        newWidth: Int,
        newHeight: Int,
        isNecessaryToKeepOrig: Boolean
    ): Bitmap {
        val width = bm.width
        val height = bm.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)

        val resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false)
        if (!isNecessaryToKeepOrig) {
            bm.recycle()
        }
        return resizedBitmap
    }

    private fun getImage(): Bitmap? {
        val uri = intent.getParcelableExtra<Uri>("uri")
        var img: Bitmap? = null
        uri?.let {
            img = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(this.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(this.contentResolver, it)
                ImageDecoder.decodeBitmap(source)
            }
        }
        return img
    }

    @Composable
    @OptIn(ExperimentalMaterialApi::class)
    fun sendPostRequest(image: Bitmap, garmentId: String) {
        val img = encodeImage(image)
        val aux = """{ "image" : "${img.toString()}","id_prenda" : "${garmentId}"}"""
        val bodyJson=aux.replace("\n","")
        val httpAsync = "http://34.125.195.32:8000/upload"
            .httpPost()
            .body(bodyJson, Charsets.UTF_8)
            .header("Content-Type", "application/json")
            .timeoutRead(180_000)

            .responseString { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        println(ex)
                    }
                    is Result.Success -> {
                        val data = result.get()
                        imgResult = data
                        val Intent = Intent(this, ResultActivity::class.java)
                        startActivity(Intent)
                        finish()
                    }
                }
            }
        AnimatedShimmer(screenWidth = screenWidth, screen = 2)
        httpAsync.join()
    }

    private fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }


    private suspend fun getBitmap(url: String): Bitmap? {
        val loading = ImageLoader(this)
        val request = ImageRequest.Builder(this)
            .data(url)
            .build()
        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }
}