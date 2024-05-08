package com.plus.simplefylist

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable

fun AnimatedSplashScreen(
    navigateToNextScreen: () -> Unit
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        scope.launch {
            // Simula um tempo de espera de 2 segundos
            delay(2000)
            navigateToNextScreen()
        }
    }
    Splash()
}

@SuppressLint("RememberReturnType")
@Composable
fun Splash() {
    val logoAlpha = remember { Animatable(0f) }
    val progressRotation = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        logoAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )
        progressRotation.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Box(
        modifier =
        Modifier
            .background(if (isSystemInDarkTheme()) Color.DarkGray else Color.White)
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.simplefy_logo),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .graphicsLayer(alpha = logoAlpha.value)
            )
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(48.dp)
                    .graphicsLayer(rotationZ = progressRotation.value * 360)
            )
        }
    }
}


@Preview(
    device = "id:pixel_3",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.NONE, backgroundColor = 0xFFFFFFFF
)
@Composable
fun PreviewAnimatedSplashScreen() {
    AnimatedSplashScreen{}
}