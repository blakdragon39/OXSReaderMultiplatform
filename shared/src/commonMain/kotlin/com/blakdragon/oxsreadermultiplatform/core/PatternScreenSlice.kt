package com.blakdragon.oxsreadermultiplatform.core

import org.reduxkotlin.TypedReducer

data class PatternScreenState(
    val temp: Boolean = false,
)

class PatternScreenReducer : TypedReducer<AppState, Any> {

    override fun invoke(state: AppState, action: Any): AppState {
        return state // todo
    }
}

sealed class PatternScreenAction {

}