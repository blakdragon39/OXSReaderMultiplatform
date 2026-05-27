package com.blakdragon.oxsreadermultiplatform

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.rememberLifecycleOwner
import com.blakdragon.oxsreadermultiplatform.core.PatternUiAction
import com.blakdragon.oxsreadermultiplatform.ui.PatternUi
import com.blakdragon.oxsreadermultiplatform.ui.PatternUiState
import com.blakdragon.oxsreadermultiplatform.ui.theme.OXSReaderTheme
import org.koin.compose.koinInject
import org.koin.core.context.startKoin
import org.reduxkotlin.TypedStore

@Composable
@Preview
fun App() {
    startKoin { // todo this needs to move out of the composable
        modules(appModule)
    }

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
    val store: TypedStore<PatternUiState, PatternUiAction> = koinInject()
    val scope = rememberCoroutineScope()
    val lifecycle = rememberLifecycleOwner()

    var uiState by remember { mutableStateOf(store.state) }
    val subscription = store.subscribe { uiState = store.state }
    lifecycle.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            subscription.invoke()
        }
    })

    store.dispatch(PatternUiAction.LoadPattern("files/oxs_sample.oxs", scope))

    Box(modifier) {
        PatternUi(uiState)
    }
}
