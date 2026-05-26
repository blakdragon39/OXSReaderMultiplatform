package com.blakdragon.oxsreadermultiplatform

import com.blakdragon.oxsreadermultiplatform.core.PatternUiAction
import com.blakdragon.oxsreadermultiplatform.core.PatternUiReducer
import com.blakdragon.oxsreadermultiplatform.ui.PatternUiState
import org.koin.dsl.module
import org.reduxkotlin.TypedStore
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.createTypedStore
import org.reduxkotlin.thunk.createThunkMiddleware

val appModule = module {
    factory<TypedStore<PatternUiState, PatternUiAction>> {
        createTypedStore(PatternUiReducer(), PatternUiState(), applyMiddleware(createThunkMiddleware()))
    }
}