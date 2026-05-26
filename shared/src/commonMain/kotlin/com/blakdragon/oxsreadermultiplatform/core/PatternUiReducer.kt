package com.blakdragon.oxsreadermultiplatform.core

import com.blakdragon.oxsreadermultiplatform.core.PatternUiAction.PatternLoaded
import com.blakdragon.oxsreadermultiplatform.reader.OXSReader
import com.blakdragon.oxsreadermultiplatform.reader.models.Pattern
import com.blakdragon.oxsreadermultiplatform.ui.PatternUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.reduxkotlin.Dispatcher
import org.reduxkotlin.GetState
import org.reduxkotlin.TypedReducer
import org.reduxkotlin.thunk.Thunk

class PatternUiReducer : TypedReducer<PatternUiState, PatternUiAction> {

    override fun invoke(state: PatternUiState, action: PatternUiAction): PatternUiState {
        return when (action) {
            is PatternUiAction.LoadPattern -> PatternUiState(Pattern())
            is PatternUiAction.PatternLoaded -> PatternUiState(action.pattern)
        }
    }
}

sealed class PatternUiAction {
    data class PatternLoaded(val pattern: Pattern) : PatternUiAction()

    data class LoadPattern(
        val fileName: String,
        val scope: CoroutineScope
    ) : PatternUiAction(), Thunk<PatternUiState> {
        override fun invoke(dispatch: Dispatcher, getState: GetState<PatternUiState>, args: Any?) = loadPattern(dispatch, fileName, scope)
    }
}

private fun loadPattern(dispatch: Dispatcher, fileName: String, scope: CoroutineScope) {
    scope.launch(Dispatchers.IO) {
        val result = OXSReader.read(fileName)
        dispatch(PatternLoaded(result))
    }
}
