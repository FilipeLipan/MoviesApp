package com.github.filipelipan.moviesapp.infraestructure.ui

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieLoader(
    @RawRes
    rawResource: Int,
    modifier: Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(rawResource))
    val progress by animateLottieCompositionAsState(composition)
    LottieAnimation(
        contentScale = contentScale,
        modifier = modifier,
        composition = composition,
        progress = { progress },
    )
}
