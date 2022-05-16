package com.migueldemollet.dressmeapp

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.migueldemollet.dressmeapp.model.Garment
import com.migueldemollet.dressmeapp.ui.theme.DressMeAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class ClotheActivity : ComponentActivity() {

    private var isCameraSelected = false
    private var imageUri: Uri? = null
    private var garmentId: Int = 0

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
                    garmentId = intent.getIntExtra("garmentId", 0)
                    ClotheScreen()
                }
            }
        }
    }

    @Composable
    fun ClotheScreen() {
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
            val img = painterResource(id = R.drawable.ic_launcher_background)
            val dress_img = painterResource(id = R.drawable.dress_me_app)
            //val garment = Garment(0, img, "Nombre del producto", dress_img, "texto", "color", "type")
            //ImageSection(garment)
        }
    }

    @Composable
    fun CardBox2(garment: Garment, componentWidth: Dp, componentHeight: Dp) {
        Card(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .width(componentWidth)
                .clickable(onClick = { /*TODO*/ }),
            shape = RoundedCornerShape(15.dp),
            elevation = 10.dp,
            backgroundColor = MaterialTheme.colors.primary

        ) {
            Box(
                contentAlignment = Alignment.TopEnd,
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = garment.img,
                        builder = {
                            crossfade(false)
                        }
                    ),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
                Image(
                    painter = rememberImagePainter(
                        data = garment.brand,
                        builder = {
                            crossfade(false)
                        }
                    ),
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

    @Composable
    fun ProductRecomendations(garments: List<Garment>) {
        LazyRow(
            modifier = Modifier
                .height(screenHeight / 4 + screenHeight / 20)
        ) {
            items(garments) { garment ->
                CardBox2(garment, screenWidth / 3, screenHeight)
                //ScreenHeight de momento no se utiliza
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun TrySection(coroutineScope: CoroutineScope, bottomSheetModalState: ModalBottomSheetState) {
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
                    coroutineScope.launch { bottomSheetModalState.show() }
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
                Text("PROBAR")
            }
        }
    }

    @Composable
    fun ProductBox(garment: Garment) {
        Card(
            modifier = Modifier
                .width(screenWidth)
                .padding(10.dp)
                .height(screenHeight / 2),
            shape = RoundedCornerShape(15.dp),
            elevation = 10.dp,
            backgroundColor = MaterialTheme.colors.primary

        ) {
            Column(
                modifier = Modifier
                    .height(screenHeight / 2)
                    .width(screenWidth)
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = garment.img,
                        builder = {
                            crossfade(false)
                        }
                    ),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun ImageSection(garment: Garment) {
        val context = LocalContext.current
        val bottomSheetModalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        val coroutineScope = rememberCoroutineScope()
        val uritmp = createTmpFile()

        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            this.imageUri = uri
        }

        val cameraLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture()
        ) { saved: Boolean ->
            if (saved) {
                this.imageUri = uritmp
            }
        }

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                if (isCameraSelected) {
                    cameraLauncher.launch(uritmp)
                } else {
                    galleryLauncher.launch("image/*")
                }
                coroutineScope.launch {
                    bottomSheetModalState.hide()
                }
            } else {
                Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }

        ModalBottomSheetLayout(
            sheetContent = {
                Box(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "How are you going to do that?",
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colors.onBackground,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.h5
                            )
                        }
                        Divider(
                            modifier = Modifier
                                .height(3.dp)
                                .background(MaterialTheme.colors.primary)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    when (PackageManager.PERMISSION_GRANTED) {
                                        ContextCompat.checkSelfPermission(
                                            context, Manifest.permission.CAMERA
                                        ) -> {
                                            cameraLauncher.launch(uritmp)
                                            coroutineScope.launch {
                                                bottomSheetModalState.hide()
                                            }
                                        }
                                        else -> {
                                            isCameraSelected = true
                                            permissionLauncher.launch(Manifest.permission.CAMERA)
                                        }
                                    }
                                },
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.camara),
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(10.dp)
                                    .size(25.dp),
                                tint = MaterialTheme.colors.onSurface
                            )
                            Text(
                                text = "Take Photo",
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    .fillMaxWidth(),
                                color = MaterialTheme.colors.onBackground,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.body1
                            )
                        }

                        Divider(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth()
                                .background(Color.LightGray)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    when (PackageManager.PERMISSION_GRANTED) {
                                        ContextCompat.checkSelfPermission(
                                            context, Manifest.permission.READ_EXTERNAL_STORAGE
                                        ) -> {
                                            galleryLauncher.launch("image/*")
                                            coroutineScope.launch { bottomSheetModalState.hide() }
                                        }
                                        else -> {
                                            isCameraSelected = false
                                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                        }
                                    }
                                },
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.gallery),
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(10.dp)
                                    .size(25.dp),
                                tint = MaterialTheme.colors.onSurface
                            )
                            Text(
                                text = "Choose from Gallery",
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    .fillMaxWidth(),
                                color = MaterialTheme.colors.onBackground,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.body1
                            )
                        }

                        Divider(
                            modifier = Modifier
                                .height(0.5.dp)
                                .fillMaxWidth()
                                .background(Color.LightGray)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    coroutineScope.launch {
                                        bottomSheetModalState.hide()
                                    }
                                },
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(10.dp)
                                    .size(25.dp),
                                tint = MaterialTheme.colors.onSurface
                            )
                            Text(
                                text = "Cancel",
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    .fillMaxWidth(),
                                color = MaterialTheme.colors.onBackground,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
            },
            sheetState = bottomSheetModalState,
            sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
            sheetBackgroundColor = MaterialTheme.colors.background,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                ProductBox(garment)
                val img = painterResource(id = R.drawable.ic_launcher_background)
                val dress_img = painterResource(id = R.drawable.dress_me_app)
                /*val garments: List<Garment> = listOf(
                    Garment(0, img, "Nombre del producto", dress_img, "texto", "color", "type"),
                    Garment(1, img, "Nombre del producto", dress_img, "texto", "color", "type"),
                    Garment(2, img, "Nombre del producto", dress_img, "texto", "color", "type"),
                    Garment(3, img, "Nombre del producto", dress_img, "texto", "color", "type"),
                    Garment(4, img, "Nombre del producto", dress_img, "texto", "color", "type"),
                    Garment(5, img, "Nombre del producto", dress_img, "texto", "color", "type"),
                    Garment(6, img, "Nombre del producto", dress_img, "texto", "color", "type"),
                    Garment(7, img, "Nombre del producto", dress_img, "texto", "color", "type")
                )*/
                Spacer(modifier = Modifier.height(5.dp))
                //ProductRecomendations(garments)
                TrySection(coroutineScope = coroutineScope, bottomSheetModalState = bottomSheetModalState)
            }
        }
    }

    private fun createTmpFile(): Uri {
        val imageFile = File(this.cacheDir, "tmp_image.jpg")
        imageFile.createNewFile()
        return FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", imageFile)
    }

    @OptIn(ExperimentalMaterialApi::class)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("uri", imageUri)
            intent.putExtra("garmentId", garmentId)
            this.startActivity(intent)
        }
    }

    @Preview(showSystemUi = true)
    @Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    fun ClotheScreenPreview() {
        DressMeAppTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                screenWidth = LocalConfiguration.current.screenWidthDp.dp
                screenHeight = LocalConfiguration.current.screenHeightDp.dp
                ClotheScreen()
            }
        }
    }
}