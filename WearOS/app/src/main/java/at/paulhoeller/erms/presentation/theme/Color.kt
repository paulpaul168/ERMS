package at.paulhoeller.erms.presentation.theme

import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors

val Teal400 = Color(0xFF26A69A)
val Teal600 = Color(0xFF00897B)
val Pink400 = Color(0xFFEC407A)
val Pink600 = Color(0xFFD81B60)
val Red400 = Color(0xFFCF6679)

internal val wearColorPalette: Colors = Colors(
    primary = Color.Black,//Teal400,
    primaryVariant = Teal600,
    secondary = Pink400,
    secondaryVariant = Pink600,
    error = Red400,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onError = Color.Black,
    background = Color.Black,
)