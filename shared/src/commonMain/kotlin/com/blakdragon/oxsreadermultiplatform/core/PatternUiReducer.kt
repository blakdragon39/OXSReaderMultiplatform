package com.blakdragon.oxsreadermultiplatform.core

import com.blakdragon.oxsreadermultiplatform.reader.OXSReader
import com.blakdragon.oxsreadermultiplatform.ui.PatternUiState
import com.blakdragon.oxsreadermultiplatform.ui.patternPreview
import org.reduxkotlin.TypedReducer

class PatternUiReducer : TypedReducer<PatternUiState, PatternUiAction> {

    override fun invoke(state: PatternUiState, action: PatternUiAction): PatternUiState {
        return when (action) {
            is PatternUiAction.LoadPattern -> // PatternUiState(pattern = OXSReader.read(action.fileName))
                PatternUiState(patternPreview)
        }
    }
}

sealed class PatternUiAction {
    data class LoadPattern(val fileName: String) : PatternUiAction()
}