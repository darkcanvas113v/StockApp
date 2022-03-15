package com.sillyapps.core_ui.compose.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

@Composable
fun StateButton(
  text: String,
  state: Boolean,
  setState: (Boolean) -> Unit,
  shape: Shape,
  alternativeColor: Color,
  alternativeContentColor: Color,
  modifier: Modifier = Modifier,
  defaultColor: Color = MaterialTheme.colors.primary,
  defaultContentColor: Color = MaterialTheme.colors.onPrimary,
) {

  val backgroundColor by animateColorAsState(
    targetValue = if (state) defaultColor else alternativeColor)

  val contentColor by animateColorAsState(
    targetValue = if (state) defaultContentColor else alternativeContentColor
  )

  Button(
    onClick = { setState(!state) },
    modifier = modifier,
    shape = shape,
    colors = ButtonDefaults.buttonColors(
      backgroundColor = backgroundColor,
      contentColor = contentColor
    ),
    content = { Text(text = text) }
  )
}