package com.blakdragon.oxsreadermultiplatform.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blakdragon.oxsreadermultiplatform.core.PatternScreenState
import com.blakdragon.oxsreadermultiplatform.core.PatternUiState
import com.blakdragon.oxsreadermultiplatform.ui.previews.patternPreview
import com.blakdragon.oxsreadermultiplatform.ui.theme.LocalColors
import com.blakdragon.oxsreadermultiplatform.ui.theme.OXSReaderColors
import com.blakdragon.oxsreadermultiplatform.ui.theme.OXSReaderTheme
import org.jetbrains.compose.resources.imageResource
import oxsreadermultiplatform.shared.generated.resources.Res
import oxsreadermultiplatform.shared.generated.resources.ic_back


@Composable
fun PatternScreen(
    patternUiState: PatternUiState,
    patternScreenState: PatternScreenState,
    onBackPressed: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(LocalColors.current.mainBackground)
    ) {
        PatternToolbar(
            onBackPressed = onBackPressed,
        )

        Spacer(modifier = Modifier
            .height(1.dp).
            fillMaxWidth()
            .background(LocalColors.current.separator)
        )

        Box {
            PatternUi(patternUiState)
            // todo movable tool selection
        }
    }
}

@Composable
private fun PatternToolbar(
    onBackPressed: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LocalColors.current.secondaryBackground)
            .padding(12.dp)
    ) {
        Image(
            bitmap = imageResource(Res.drawable.ic_back),
            contentDescription = "Back", // todo strings, omg
            colorFilter = ColorFilter.tint(LocalColors.current.selectedIcon),
            modifier = Modifier
                .size(32.dp)
                .clickable { onBackPressed() }
        )
    }
}

@Preview
@Composable
private fun PatternScreenPreview() {
    OXSReaderTheme {
        CompositionLocalProvider(
            LocalColors provides OXSReaderColors(),
        ) {

            PatternScreen(
                patternUiState = PatternUiState(pattern = patternPreview),
                patternScreenState = PatternScreenState(),
                onBackPressed = { },
            )
        }
    }
}