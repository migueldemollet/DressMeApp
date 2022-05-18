package com.migueldemollet.dressmeapp.screens.garmentTryOn.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.migueldemollet.dressmeapp.screenWidth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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