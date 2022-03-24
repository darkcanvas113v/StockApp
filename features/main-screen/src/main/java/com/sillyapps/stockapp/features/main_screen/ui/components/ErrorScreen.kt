package com.sillyapps.stockapp.features.main_screen.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sillyapps.core_network.Resource
import com.sillyapps.stockapp.domain.stock.model.Stock
import com.sillyapps.stockapp.features.main_screen.R

@Composable
fun ErrorScreen(
  error: Resource.Error<List<Stock>>,
  onReload: () -> Unit
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = stringResource(id = R.string.no_internet),
      modifier = Modifier
        .padding(horizontal = 32.dp),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.h5
    )

    TextButton(
      onClick = onReload,
      modifier = Modifier.padding(top = 16.dp)
    ) {
      Text(
        text = stringResource(id = R.string.try_again)
      )
    }
  }
}