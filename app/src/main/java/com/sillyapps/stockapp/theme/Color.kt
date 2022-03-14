/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sillyapps.stockapp.theme

import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color

val Blue = Color(0xFF2F80ED)
val Green500 = Color(0xFF1EB980)
val DarkBlue900 = Color(0xFF26282F)
val Gray = Color(0xFF7C8086)
val Red = Color(0xFFEB5757)

// Rally is always dark themed.
val ColorPalette = darkColors(
    primary = Green500,
    surface = DarkBlue900,
    onSurface = Color.White,
    background = DarkBlue900,
    onBackground = Color.White,
    error = Gray,
    onError = Color.Black
)
