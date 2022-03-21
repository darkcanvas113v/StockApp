package com.sillyapps.stockapp.features.main_screen.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sillyapps.stockapp.domain.stock.model.Stock
import com.sillyapps.stockapp.common.ui.theme.AppTheme
import com.sillyapps.stockapp.common.ui.theme.Typography
import kotlin.math.abs
import kotlin.math.round

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
    Row(
      modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 20.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(
        modifier = Modifier
          .weight(1f)
          .padding(end = 8.dp)
      ) {
        Text(
          text = stock.name,
          style = MaterialTheme.typography.h5,
        )

        Text(
          text = stock.symbol,
          style = MaterialTheme.typography.body1,
          modifier = Modifier.padding(top = 4.dp)
        )
      }

      if (stock.price == null) {
        CircularProgressIndicator()
      }
      else {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          val changeValue = stock.percentChange ?: 0.0

          val changeIsPositive = (changeValue >= 0.0)
          val changeSign = if (changeIsPositive) "+" else "-"
          val absChangeValue = String.format("%.2f", abs(changeValue))

          Text(
            text = "$changeSign$absChangeValue %",
            style = MaterialTheme.typography.h5,
            color = if (changeIsPositive) Color.Green else Color.Red
          )
          Text(
            text = "${stock.price} $",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(top = 4.dp)
          )
        }

      }

    }

  }


}

@Preview
@Composable
fun StockItemPreview() {
  AppTheme {
    StockItem(
      stock = Stock("APPL", "Apple", 2.0, 421.0)
    )
  }
}