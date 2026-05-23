package com.blakdragon.oxsreadermultiplatform

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.blakdragon.oxsreadermultiplatform.core.PatternUiAction
import com.blakdragon.oxsreadermultiplatform.core.PatternUiReducer
import com.blakdragon.oxsreadermultiplatform.ui.PatternUi
import com.blakdragon.oxsreadermultiplatform.ui.PatternUiState
import com.blakdragon.oxsreadermultiplatform.ui.theme.OXSReaderTheme
import org.koin.compose.koinInject
import org.koin.core.context.startKoin
import org.reduxkotlin.createTypedStore

@Composable
@Preview
fun App() {
    startKoin {
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
    val reducer: PatternUiReducer = koinInject()
    var uiState by remember { mutableStateOf(PatternUiState()) }
    val store = createTypedStore(reducer, uiState)
    val subscription = store.subscribe { uiState = store.state }

    store.dispatch(PatternUiAction.LoadPattern("files/oxs_sample.oxs"))

    Box(modifier) {
        PatternUi(uiState, store)
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    OXSReaderTheme {
        Scaffold(Modifier.fillMaxSize()) { innerPadding ->
            MainScreen(
                Modifier.padding(innerPadding),
            )
        }
    }
}