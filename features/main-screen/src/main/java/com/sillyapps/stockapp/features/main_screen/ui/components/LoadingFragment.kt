package com.sillyapps.stockapp.features.main_screen.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sillyapps.stockapp.common.ui.compose.theme.AppTheme
import com.sillyapps.stockapp.features.main_screen.R

@Composable
fun LoadingFragment() {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Text(
      text = stringResource(
        id = R.string.loading,),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.h5,
      modifier = Modifier.padding(16.dp)
    )
    CircularProgressIndicator()
  }

}

@Preview
@Composable
fun LoadingFragmentPreview() {
  AppTheme {
    Surface(modifier = Modifier.fillMaxSize()) {
      Box(contentAlignment = Alignment.Center) {
        LoadingFragment()
      }
    }
  }
}