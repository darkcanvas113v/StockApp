package com.sillyapps.stockapp.features.main_screen.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sillyapps.core_network.Resource
import com.sillyapps.stockapp.domain.stock.model.Stock

@Composable
fun ErrorScreen(
  error: Resource.Error<List<Stock>>,
  onReload: () -> Unit
) {
  when (error.type) {
    Resource.Error.Type.BAD_CONNECTION -> {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = "Couldn't reach server, please check your internet connection.",
          modifier = Modifier
            .padding(horizontal = 32.dp),
          textAlign = TextAlign.Center
        )

        TextButton(
          onClick = onReload,
          modifier = Modifier.padding(top = 16.dp)
        ) {
          Text(
            text = "Try again"
          )
        }
      }
    }
    Resource.Error.Type.UNKNOWN -> {
      Text(
        text = "Unknown error.",
        modifier = Modifier
          .padding(horizontal = 32.dp),
        textAlign = TextAlign.Center
      )
    }
  }

}