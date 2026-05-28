package com.blakdragon.oxsreadermultiplatform.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blakdragon.oxsreadermultiplatform.core.AppState
import com.blakdragon.oxsreadermultiplatform.core.PatternScreenAction
import com.blakdragon.oxsreadermultiplatform.core.PatternScreenState
import com.blakdragon.oxsreadermultiplatform.core.PatternUiState
import com.blakdragon.oxsreadermultiplatform.core.Tool
import com.blakdragon.oxsreadermultiplatform.core.ToolBarState
import com.blakdragon.oxsreadermultiplatform.ui.previews.mockStore
import com.blakdragon.oxsreadermultiplatform.ui.previews.patternPreview
import com.blakdragon.oxsreadermultiplatform.ui.theme.LocalColors
import com.blakdragon.oxsreadermultiplatform.ui.theme.OXSReaderColors
import com.blakdragon.oxsreadermultiplatform.ui.theme.OXSReaderTheme
import org.jetbrains.compose.resources.imageResource
import org.reduxkotlin.Store
import oxsreadermultiplatform.shared.generated.resources.Res
import oxsreadermultiplatform.shared.generated.resources.ic_back
import oxsreadermultiplatform.shared.generated.resources.ic_erase
import oxsreadermultiplatform.shared.generated.resources.ic_mark
import oxsreadermultiplatform.shared.generated.resources.ic_minus
import oxsreadermultiplatform.shared.generated.resources.ic_redo
import oxsreadermultiplatform.shared.generated.resources.ic_search
import oxsreadermultiplatform.shared.generated.resources.ic_undo

private val toolIconSize = 28.dp

@Composable
fun PatternScreen(
    store: Store<AppState>,
    patternUiState: PatternUiState,
    screenState: PatternScreenState,
    onBackPressed: () -> Unit,
) {
    // todo store this value so its remembered on relaunch. probably with a delay so its not being fired for every single pixel
    var toolbarOffset by remember { mutableStateOf(Offset(0f, 0f)) }

    Column(
        modifier = Modifier
            .background(LocalColors.current.mainBackground)
    ) {
        PatternToolbar(
            onBackPressed = onBackPressed,
        )

        Spacer(modifier = Modifier
            .height(1.dp).
            fillMaxWidth()
            .background(LocalColors.current.separator)
        )

        Box(modifier = Modifier.fillMaxSize()) {
            PatternUi(patternUiState)
            ToolSelector(
                state = screenState.toolBarState,
                onSearchSelected = { store.dispatch(PatternScreenAction.ToolSelected(Tool.SEARCH)) },
                onEraseSelected = { store.dispatch(PatternScreenAction.ToolSelected(Tool.ERASE)) },
                onFillSelected = { store.dispatch(PatternScreenAction.ToolSelected(Tool.FILL)) },
                onDrag = { dragOffset -> toolbarOffset += dragOffset },
                modifier = Modifier
                    .offset(
                        x = (toolbarOffset.x / LocalDensity.current.density).dp,
                        y = (toolbarOffset.y / LocalDensity.current.density).dp,
                    )
            )
        }
    }
}

@Composable
private fun PatternToolbar(
    onBackPressed: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LocalColors.current.secondaryBackground)
            .padding(12.dp)
    ) {
        Image(
            bitmap = imageResource(Res.drawable.ic_back),
            contentDescription = "Back", // todo strings, omg
            colorFilter = ColorFilter.tint(LocalColors.current.selectedIcon),
            modifier = Modifier
                .size(32.dp)
                .clickable { onBackPressed() }
        )
    }
}

@Composable
private fun ToolSelector(
    state: ToolBarState,
    onSearchSelected: () -> Unit,
    onEraseSelected: () -> Unit,
    onFillSelected: () -> Unit,
    onDrag: (Offset) -> Unit,
    modifier: Modifier,
) {
    val selectedTint = ColorFilter.tint(LocalColors.current.selectedIcon)
    val unselectedTint = ColorFilter.tint(LocalColors.current.unselectedIcon)
    Card(
        shape = RoundedCornerShape(32.dp),
        border = BorderStroke(1.dp, LocalColors.current.separator),
        colors = CardDefaults.cardColors(containerColor = LocalColors.current.secondaryBackground),
        modifier = modifier.padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                bitmap = imageResource(Res.drawable.ic_undo),
                contentDescription = "Undo",
                colorFilter = selectedTint, // todo only if there are actions to undo
                modifier = Modifier
                    .size(toolIconSize)
                    .clickable { /* todo undo action, only if there are actions to undo */ }
            )

            Spacer(modifier = Modifier.size(8.dp))

            Image(
                bitmap = imageResource(Res.drawable.ic_redo),
                contentDescription = "Redo",
                colorFilter = selectedTint, // todo only if there are actions to redo
                modifier = Modifier
                    .size(toolIconSize)
                    .clickable { /* todo redo action, only if there are actions to redo */ }
            )

            Spacer(modifier = Modifier.size(8.dp))

            Image(
                bitmap = imageResource(Res.drawable.ic_search),
                contentDescription = "Search",
                colorFilter = if (state.selectedTool == Tool.SEARCH) selectedTint else unselectedTint,
                modifier = Modifier
                    .size(toolIconSize)
                    .clickable { onSearchSelected() }
            )

            Spacer(modifier = Modifier.size(8.dp))

            Image(
                bitmap = imageResource(Res.drawable.ic_erase),
                contentDescription = "Erase",
                colorFilter = if (state.selectedTool == Tool.ERASE) selectedTint else unselectedTint,
                modifier = Modifier
                    .size(toolIconSize)
                    .clickable { onEraseSelected() }
            )

            Spacer(modifier = Modifier.size(8.dp))

            Image(
                bitmap = imageResource(Res.drawable.ic_mark),
                contentDescription = "Fill",
                colorFilter = if (state.selectedTool == Tool.FILL) selectedTint else unselectedTint,
                modifier = Modifier
                    .size(toolIconSize)
                    .clickable { onFillSelected() }
            )

            Spacer(modifier = Modifier.size(8.dp))

            Image(
                bitmap = imageResource(Res.drawable.ic_minus),
                contentDescription = "Collapse tray/drag handle",
                colorFilter = ColorFilter.tint(LocalColors.current.selectedIcon),
                modifier = Modifier
                    .size(toolIconSize)
                    .clickable { /* todo open/close state */ }
                    .pointerInput(Unit) {
                        detectDragGestures { _, dragAmount ->
                            onDrag(dragAmount)
                        }
                    }
            )
        }
    }
}

@Preview
@Composable
private fun PatternScreenPreview() {
    OXSReaderTheme {
        CompositionLocalProvider(
            LocalColors provides OXSReaderColors(),
        ) {

            PatternScreen(
                store = mockStore,
                patternUiState = PatternUiState(pattern = patternPreview,),
                screenState = PatternScreenState(
                    toolBarState = ToolBarState(),
                ),
                onBackPressed = { },
            )
        }
    }
}