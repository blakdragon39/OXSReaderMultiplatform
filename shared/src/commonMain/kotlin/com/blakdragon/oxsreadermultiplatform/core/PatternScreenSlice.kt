package com.blakdragon.oxsreadermultiplatform.core

import org.reduxkotlin.TypedReducer

data class PatternScreenState(
    val toolBarState: ToolBarState = ToolBarState(),
)

enum class Tool { SEARCH, ERASE, FILL }

data class ToolBarState(
    val selectedTool: Tool? = null,
)

class PatternScreenReducer : TypedReducer<AppState, Any> {

    override fun invoke(state: AppState, action: Any): AppState {
        return when (action) {
            is PatternScreenAction.ToolSelected -> {
                val newTool = if (state.patternScreenState.toolBarState.selectedTool != action.tool) action.tool else null
                state.copy(patternScreenState = state.patternScreenState.copy(
                    toolBarState = ToolBarState(selectedTool = newTool))
                )
            }
            else -> state
        }
    }
}

sealed class PatternScreenAction {
    data class ToolSelected(val tool: Tool) : PatternScreenAction()
}