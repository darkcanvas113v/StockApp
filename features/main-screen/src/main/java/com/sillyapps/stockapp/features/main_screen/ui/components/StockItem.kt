package com.sillyapps.stockapp.features.main_screen.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sillyapps.stockapp.common.ui.R
import com.sillyapps.stockapp.common.ui.compose.components.RemoteCircleImage
import com.sillyapps.stockapp.domain.stock.model.Stock
import com.sillyapps.stockapp.common.ui.compose.theme.AppTheme
import com.sillyapps.stockapp.domain.stock.model.Company
import com.sillyapps.stockapp.domain.stock.model.Quote
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import kotlin.math.abs

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
      RemoteCircleImage(
        url = stock.company?.logoUrl,
        modifier = Modifier
          .padding(end = 16.dp)
          .size(44.dp)
      )

      CompanyInfo(stock = stock)

      StockQuoteInfo(stock = stock)

    }
  }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RowScope.CompanyInfo(stock: Stock) {
  Column(
    modifier = Modifier
      .weight(1f)
      .padding(end = 8.dp)
  ) {
    AnimatedContent(targetState = stock.name) { targetText ->
      Text(
        text = targetText,
        style = MaterialTheme.typography.h5,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
      )
    }

    AnimatedContent(targetState = stock.company?.industry) { targetText ->
      Text(
        text = targetText ?: "N/A",
        style = MaterialTheme.typography.body1,
        modifier = Modifier.padding(top = 4.dp)
      )
    }

  }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RowScope.StockQuoteInfo(stock: Stock) {
  AnimatedContent(stock.quote == null) { value ->
    if (value) {
      CircularProgressIndicator()
    }
    else {
      Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier.weight(0.35f)
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
        AnimatedContent(targetState = stock.quote!!.currentPrice) { targetText ->
          Text(
            text = "$${targetText}",
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
        company = Company(
          name = "Apple Inc",
          industry = "Technology",
          country = "US",
          currency = "USD",
          logoUrl = null,
          marketCapitalization = 10.0,
          weburl = ""
        ),
        quote = Quote(2.0, 421.0, 0)
      )
    )
  }
}