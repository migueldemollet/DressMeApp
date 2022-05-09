package com.migueldemollet.dressmeapp

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedShimmer() {
    val ShimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition()
    val translitioAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    val brush = Brush.linearGradient(
        colors = ShimmerColors,
        start = Offset.Zero,
        end = Offset(
            x = translitioAnim.value,
            y = translitioAnim.value
        ),
    )
    
    ShimmerMainScreen(brush = brush)
}

@Composable
fun ShimmerMainScreen(brush: Brush) {
    Column(
        modifier = Modifier.fillMaxHeight(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .background(brush)
                    .clip(RoundedCornerShape(15.dp))
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .background(brush)
                    .clip(RoundedCornerShape(15.dp))
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()

        ) {
            repeat(5) {
                Spacer(
                    modifier = Modifier
                        .width(100.dp)
                        .height(60.dp)
                        .padding(15.dp)
                        .background(brush)
                )
            }

        }
        repeat(8) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Spacer(
                    modifier = Modifier
                        .width(130.dp)
                        .height(130.dp)
                        .padding(15.dp)
                        .background(brush)
                )
                Spacer(
                    modifier = Modifier
                        .width(130.dp)
                        .height(130.dp)
                        .padding(15.dp)
                        .background(brush)
                )
                Spacer(
                    modifier = Modifier
                        .width(130.dp)
                        .height(130.dp)
                        .padding(15.dp)
                        .background(brush)
                )
            }
        }
    }

}