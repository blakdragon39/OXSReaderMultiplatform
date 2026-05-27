package com.blakdragon.oxsreadermultiplatform.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import com.blakdragon.oxsreadermultiplatform.core.PatternScreenState


@Composable
fun PatternScreen(
    patternScreenState: PatternScreenState
) {
    Box {
        PatternUi(patternScreenState.patternUiState)
    }
}