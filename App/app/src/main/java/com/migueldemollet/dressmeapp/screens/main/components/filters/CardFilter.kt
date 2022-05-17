package com.migueldemollet.dressmeapp.screens.main.components.filters

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.migueldemollet.dressmeapp.model.Filter

@Composable
fun CardFilter(filter: Filter) {
    Button(
        onClick = {

        },
        modifier = Modifier.padding(10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        ),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(text = filter.text)
    }
}