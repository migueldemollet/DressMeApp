package com.migueldemollet.dressmeapp.screens.result

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@Composable
fun ResultScreen() {

}

private fun getScaledDownBitmap(bitmap: Bitmap, threshold: Int, isNecessaryToKeepOrig: Boolean): Bitmap {
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

private fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int, isNecessaryToKeepOrig: Boolean): Bitmap {
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

/*
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
private fun SaveImage(bitmap: Bitmap) {
    val context = LocalContext.current
    val filename = "IMG_${System.currentTimeMillis()}.jpg"
    var fos: OutputStream? = null

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context.contentResolver.also { resolver ->
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
        Toast.makeText(context, "Saved Image", Toast.LENGTH_SHORT).show()
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
*/