package com.sillyapps.stockapp.common.ui.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.sillyapps.stockapp.common.ui.R
import com.sillyapps.stockapp.common.ui.compose.theme.AppTheme
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import com.valentinilk.shimmer.shimmer
import timber.log.Timber

@Composable
fun RemoteCircleImage(
  url: String?,
  modifier: Modifier = Modifier
) {
  if (url == null)
    RemoteImageLoading(modifier)
  else
    GlideImage(
      imageModel = url,
      contentScale = ContentScale.Crop,
      alignment = Alignment.Center,
      circularReveal = CircularReveal(duration = 750),
      success = { imageState ->
        val drawable = imageState.drawable
        if (drawable != null) {
          Image(
            drawable.toBitmap().asImageBitmap(),
            contentDescription = null,
            modifier = modifier
              .fillMaxSize()
              .clip(CircleShape)
              .background(MaterialTheme.colors.onBackground)
          )
        }
      },
      loading = {
        RemoteImageLoading(modifier)
      },
      failure = {
        Box(
          modifier = modifier
            .clip(CircleShape)
            .background(Color.LightGray)
        ) {
          Image(
            painter = painterResource(id = R.drawable.ic_baseline_broken_image_24),
            contentDescription = null,
            modifier = Modifier
              .padding(4.dp)
              .fillMaxSize(),
            colorFilter = ColorFilter.tint(Color.Gray)
          )
        }

      },
      previewPlaceholder = R.drawable.ic_baseline_android_24
    )
}

@Composable
private fun RemoteImageLoading(
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .shimmer()
      .clip(CircleShape)
      .background(Color.LightGray)
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