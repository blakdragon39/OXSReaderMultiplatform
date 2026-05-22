package com.blakdragon.oxsreadermultiplatform.ui

import androidx.compose.runtime.Composable
import coil3.Image
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import oxsreadermultiplatform.shared.generated.resources.Res

/*
    SVGRepo.com
 */
object IconLibrary {

    val icons: List<String> = listOf(
        "arrow_circle_up.png",
        "checkmark_circle.png",
    ).map { "files/icons/$it" }
}