package com.blakdragon.oxsreadermultiplatform.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color


data class OXSReaderColors(
    val mainBackground: Color = PastelPalette.lightPink,
    val secondaryBackground: Color = PastelPalette.blue,
    val separator: Color = PastelPalette.darkBlue,
    val unselectedIcon: Color = PastelPalette.lightBlue,
    val selectedIcon: Color = PastelPalette.purple,
)

val LocalColors = compositionLocalOf { OXSReaderColors() }


@Composable
fun OXSReaderTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = Typography,
        content = content
    )
}