package com.sillyapps.stockapp.common.ui.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sillyapps.stockapp.common.ui.R
import com.sillyapps.stockapp.common.ui.compose.theme.AppTheme
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun RemoteCircleImage(
  url: String?,
  modifier: Modifier = Modifier
) {
  var backgroundColor by remember {
    mutableStateOf(Color.LightGray)
  }

  GlideImage(
    imageModel = url,
    contentScale = ContentScale.Crop,
    alignment = Alignment.Center,
    modifier = modifier
      .clip(CircleShape)
      .background(backgroundColor),
    shimmerParams = ShimmerParams(
      baseColor = Color.LightGray,
      highlightColor = Color.White,
      durationMillis = 1000,
      dropOff = 0.65f,
      tilt = 20f
    ),
    success = { backgroundColor = MaterialTheme.colors.onBackground },
    previewPlaceholder = R.drawable.ic_baseline_android_24
  )
}

@Preview
@Composable
fun RemoteImagePreview() {
  AppTheme {
    RemoteCircleImage(
      url = null,
      modifier = Modifier.size(44.dp)
    )
  }
}