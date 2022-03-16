package com.sillyapps.stockapp.features.main_screen.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sillyapps.stockapp.domain.stock.model.Stock
import com.sillyapps.stockapp.common.ui.theme.AppTheme
import com.sillyapps.stockapp.common.ui.theme.Typography

@Composable
fun StockItem(
  stock: Stock
) {

  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 8.dp, end = 8.dp, bottom = 16.dp),
    elevation = 4.dp
  ) {
    Column(
      modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
      Text(
        text = stock.name,
        style = Typography.h5
      )

      Row(
        modifier = Modifier.padding(top = 8.dp)
      ) {
        Text(
          text = "Price",
          modifier = Modifier.weight(1f)
        )
        Text(
          text = "${stock.price}$"
        )
      }

    }
  }

}

@Preview
@Composable
fun StockItemPreview() {
  AppTheme {
    StockItem(
      stock = Stock("APPL", "Apple", 421.0)
    )
  }
}