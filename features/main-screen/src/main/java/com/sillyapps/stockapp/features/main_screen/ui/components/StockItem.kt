package com.sillyapps.stockapp.features.main_screen.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sillyapps.stockapp.domain.stock.model.Stock
import com.sillyapps.stockapp.common.ui.theme.AppTheme
import com.sillyapps.stockapp.common.ui.theme.Typography
import com.sillyapps.stockapp.domain.stock.model.Quote
import com.sillyapps.stockapp.features.main_screen.R
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
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
        .padding(horizontal = 16.dp, vertical = 20.dp)
        .height(80.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      GlideImage(
        imageModel = stock.company?.logoUrl,
        contentScale = ContentScale.Crop,
        circularReveal = CircularReveal(250),
        alignment = Alignment.Center,
        modifier = Modifier
          .padding(end = 16.dp)
          .size(44.dp)
          .clip(CircleShape)
          .background(MaterialTheme.colors.onBackground)
      )

      Column(
        modifier = Modifier
          .weight(1f)
          .padding(end = 8.dp)
      ) {
        Text(
          text = if (stock.company?.name == null)
            stock.name else
            stock.company!!.name ?: "N\\A",
          style = MaterialTheme.typography.h5,
          overflow = TextOverflow.Ellipsis,
          maxLines = 1
        )

        Text(
          text = stock.company?.industry ?: "",
          style = MaterialTheme.typography.body1,
          modifier = Modifier.padding(top = 4.dp)
        )
      }

      if (stock.quote == null) {
        CircularProgressIndicator()
      } else {
        Column(
          horizontalAlignment = Alignment.End,
          modifier = Modifier.weight(0.3f)
        ) {
          val changeValue = stock.quote!!.percentChange ?: 0.0

          val changeIsPositive = (changeValue >= 0.0)
          val changeSign = if (changeIsPositive) "+" else "-"
          val absChangeValue = String.format("%.2f", abs(changeValue))

          Text(
            text = "$changeSign$absChangeValue %",
            style = MaterialTheme.typography.h5,
            color = if (changeIsPositive) Color.Green else Color.Red
          )
          Text(
            text = "$${stock.quote!!.currentPrice}",
            style = MaterialTheme.typography.h6,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
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
      stock = Stock(
        symbol = "APPL",
        name = "Apple",
        company = null,
        quote = Quote(2.0, 421.0)
      )
    )
  }
}