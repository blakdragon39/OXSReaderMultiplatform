package com.blakdragon.oxsreadermultiplatform

import com.blakdragon.oxsreadermultiplatform.core.AppState
import com.blakdragon.oxsreadermultiplatform.core.PatternScreenReducer
import com.blakdragon.oxsreadermultiplatform.core.PatternUiReducer
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.reduxkotlin.TypedStore
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.combineReducers
import org.reduxkotlin.createStore
import org.reduxkotlin.thunk.createThunkMiddleware

object KoinPouch {

    private var isInitialized = false

    fun init() {
        if (isInitialized) return

        startKoin {
            modules(appModule)
        }

        isInitialized = true
    }
}

val appModule = module {
    single<TypedStore<AppState, Any>> { createStore(
        reducer = combineReducers(
            PatternUiReducer(),
            PatternScreenReducer(),
        ),
        preloadedState = AppState(),
        enhancer = applyMiddleware(createThunkMiddleware())
    )}
}