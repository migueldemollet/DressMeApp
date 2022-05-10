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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedShimmer(screenWidth: Dp) {
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
    val widthComponentCard = screenWidth / 3 - 10.dp
    val widthComponentFilter = screenWidth / 4 - 10.dp
    ShimmerMainScreen(
        brush = brush,
        widthComponentFilter = widthComponentFilter,
        widthComponentCard = widthComponentCard
    )
}

@Composable
fun ShimmerMainScreen(brush: Brush, widthComponentFilter: Dp, widthComponentCard: Dp) {
    Column(
        modifier = Modifier.fillMaxHeight(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(4) {
                Spacer(
                    modifier = Modifier
                        .width(widthComponentFilter)
                        .height(40.dp)
                        .padding(end = 5.dp)
                        .background(brush)
                )
            }

        }
        repeat(8) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.Center

            ) {
                repeat(3) {
                    Spacer(
                        modifier = Modifier
                            .width(widthComponentCard)
                            .height(130.dp)
                            .padding(end = 5.dp)
                            .background(brush)
                    )
                }
            }
        }
    }

}