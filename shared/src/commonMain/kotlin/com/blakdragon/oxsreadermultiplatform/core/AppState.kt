package com.blakdragon.oxsreadermultiplatform.core

data class AppState(
    val patternScreenState: PatternScreenState = PatternScreenState(),
    val patternUiState: PatternUiState = PatternUiState(),
)
