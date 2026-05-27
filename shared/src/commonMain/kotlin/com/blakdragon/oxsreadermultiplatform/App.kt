package com.blakdragon.oxsreadermultiplatform

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.rememberLifecycleOwner
import com.blakdragon.oxsreadermultiplatform.core.AppState
import com.blakdragon.oxsreadermultiplatform.core.PatternUiAction
import com.blakdragon.oxsreadermultiplatform.ui.PatternUi
import com.blakdragon.oxsreadermultiplatform.ui.theme.OXSReaderTheme
import org.koin.compose.koinInject
import org.reduxkotlin.TypedStore

@Composable
@Preview
fun App() {
    KoinPouch.init()

    OXSReaderTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MainScreen(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            )
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier) {
    val store: TypedStore<AppState, Any> = koinInject()
    val scope = rememberCoroutineScope()
    val lifecycle = rememberLifecycleOwner()

    var appState: AppState by remember { mutableStateOf(store.state) }
    val subscription = store.subscribe { appState = store.state }
    lifecycle.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            subscription.invoke()
        }
    })

    store.dispatch(PatternUiAction.LoadPattern("files/oxs_sample.oxs", scope))

    Box(modifier) {
        PatternUi(appState.patternUiState)
    }
}
