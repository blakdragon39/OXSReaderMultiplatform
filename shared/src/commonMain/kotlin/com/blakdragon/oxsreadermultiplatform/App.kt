package com.blakdragon.oxsreadermultiplatform

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.compose.LocalPlatformContext
import coil3.svg.SvgDecoder
import com.blakdragon.oxsreadermultiplatform.reader.OXSReader
import com.blakdragon.oxsreadermultiplatform.reader.models.Pattern
import com.blakdragon.oxsreadermultiplatform.ui.IconLibrary
import com.blakdragon.oxsreadermultiplatform.ui.PatternUi
import com.blakdragon.oxsreadermultiplatform.ui.patternPreview
import com.blakdragon.oxsreadermultiplatform.ui.theme.OXSReaderTheme

@Composable
@Preview
fun App() {
    LoadIcons()

    OXSReaderTheme {
        var pattern by remember { mutableStateOf<Pattern?>(null) }

        LaunchedEffect(true) {
            pattern = OXSReader.read("files/oxs_sample.oxs")
        }

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            pattern?.let {
                MainScreen(
                    it,
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                )
            }
        }
    }
}

@Composable
fun LoadIcons() {
    val imageLoader = ImageLoader.Builder(LocalPlatformContext.current)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()

    SingletonImageLoader.setSafe { imageLoader }

    // todo some sort of loading effect if this is a heavy operation
//    IconLibrary.LoadImages()
}

@Composable
fun MainScreen(pattern: Pattern, modifier: Modifier) {
    Box(modifier) {
        PatternUi(pattern)
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    OXSReaderTheme {
        Scaffold(Modifier.fillMaxSize()) { innerPadding ->
            MainScreen(
                patternPreview,
                Modifier.padding(innerPadding),
            )
        }
    }
}