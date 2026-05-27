package com.blakdragon.oxsreadermultiplatform.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource
import oxsreadermultiplatform.shared.generated.resources.Res
import oxsreadermultiplatform.shared.generated.resources.glyph_arrow_circle_up
import oxsreadermultiplatform.shared.generated.resources.glyph_checkmark_circle


/*
    SVGRepo.com
 */
object IconLibrary {

    val icons: List<DrawableResource> = listOf(
        Res.drawable.glyph_arrow_circle_up,
        Res.drawable.glyph_checkmark_circle,
    )

    // todo figure out how to cache this so it doesn't recompose?
    @Composable
    fun bitmaps(): List<ImageBitmap> = icons.map {
        imageResource(it)
    }
}
