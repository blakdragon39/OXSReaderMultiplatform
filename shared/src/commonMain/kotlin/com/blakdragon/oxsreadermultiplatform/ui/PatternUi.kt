package com.blakdragon.oxsreadermultiplatform.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.blakdragon.oxsreadermultiplatform.core.PatternUiAction
import com.blakdragon.oxsreadermultiplatform.reader.models.Pattern
import org.jetbrains.compose.resources.imageResource
import org.reduxkotlin.TypedStore
import oxsreadermultiplatform.shared.generated.resources.Res
import oxsreadermultiplatform.shared.generated.resources.arrow_circle_up

val EDGE_OFFSET = 40.dp
const val SQUARE_SIZE = 30
const val LINE_WIDTH = 2
const val LINE_WIDTH_TENTH = 4
const val LINE_WIDTH_EDGE = 6
const val MIN_SCALE = 1f
const val ICON_PADDING = 3
const val ICON_SIZE = SQUARE_SIZE - ICON_PADDING * 2

data class PatternUiState(
    val pattern: Pattern = Pattern(),
)

@Composable
fun PatternUi(
    uiState: PatternUiState,
) {
    val chartWidth = uiState.pattern.overview.chartWidth
    val chartHeight = uiState.pattern.overview.chartHeight
    val edgeOffset = with (LocalDensity.current) { EDGE_OFFSET.toPx() }

    var scale by remember { mutableFloatStateOf(MIN_SCALE) }
    val minXOffset = minOffset(chartWidth * SQUARE_SIZE, scale, LocalWindowInfo.current.containerSize.width, edgeOffset)
    val minYOffset = minOffset(chartHeight * SQUARE_SIZE, scale, LocalWindowInfo.current.containerSize.height, edgeOffset)

    var panOffset by remember { mutableStateOf(Offset(edgeOffset, edgeOffset)) }

    val transformState = remember { TransformableState(onTransformation = { centroid, zoomChange, panChange, rotationChange ->
        scale = (scale * zoomChange).coerceAtLeast(MIN_SCALE) // todo max scale
        panOffset = Offset(
            x = (panOffset.x + panChange.x).coerceAtMost(edgeOffset).coerceAtLeast(minXOffset),
            y = (panOffset.y + panChange.y).coerceAtMost(edgeOffset).coerceAtLeast(minYOffset),
        )
    }) }

    Box(
        Modifier
            .fillMaxSize()
            .transformable(state = transformState)
    ) {
        PatternStitches(uiState.pattern, scale, panOffset)
        PatternGrid(chartWidth, chartHeight, scale, panOffset)
    }
}

@Composable
fun PatternStitches(
    pattern: Pattern,
    zoomScale: Float,
    offset: Offset,
) {
    val squareOffset = SQUARE_SIZE * zoomScale
    val iconSize = ICON_SIZE * zoomScale
    val scaledPadding = ICON_PADDING * zoomScale
    val temp = imageResource(Res.drawable.arrow_circle_up) // todo IconLibrary

    Canvas(Modifier) {
        pattern.fullStitches.forEach { stitch ->
            drawImage(
                image = temp,
                dstOffset = (Offset((stitch.x * squareOffset) + scaledPadding, (stitch.y * squareOffset) + scaledPadding) + offset).toIntOffset(),
                dstSize = Size(iconSize, iconSize).toIntSize(),
            )
        }
    }
}

@Composable
fun PatternGrid(
    chartWidth: Int,
    chartHeight: Int,
    zoomScale: Float,
    offset: Offset,
) {
    val squareOffset = SQUARE_SIZE * zoomScale

    Canvas(Modifier) {
        for (y in 0 until chartWidth) {
            for (x in 0 until chartHeight) {
                drawGridLine(
                    xStart = x * squareOffset,
                    yStart = 0f,
                    xEnd = x * squareOffset,
                    yEnd = chartWidth * squareOffset,
                    width = if (x % 10 == 0) LINE_WIDTH_TENTH else LINE_WIDTH,
                    offset = offset,
                )
            }

            drawGridLine(
                xStart = 0f,
                yStart = y * squareOffset,
                xEnd = chartHeight * squareOffset,
                yEnd = y * squareOffset,
                width = if (y % 10 == 0) LINE_WIDTH_TENTH else LINE_WIDTH,
                offset = offset,
            )
        }

        drawGridLine(
            xStart = chartWidth * squareOffset,
            yStart = 0f,
            xEnd = chartWidth * squareOffset,
            yEnd = chartHeight * squareOffset,
            width = LINE_WIDTH_EDGE,
            offset = offset,
        )

        drawGridLine(
            xStart = 0f,
            yStart = chartHeight * squareOffset,
            xEnd = chartWidth * squareOffset,
            yEnd = chartHeight * squareOffset,
            width = LINE_WIDTH_EDGE,
            offset = offset,
        )
    }
}

private fun minOffset(chartSize: Int, scale: Float, windowSize: Int, edgeOffset: Float): Float {
    return if ((chartSize * scale) < (windowSize - (edgeOffset * 2))) edgeOffset
        else -((chartSize * scale) - windowSize) - edgeOffset
}

private fun DrawScope.drawGridLine(xStart: Float, yStart: Float, xEnd: Float, yEnd: Float, width: Int, offset: Offset) {
    drawLine(
        start = Offset(xStart, yStart) + offset,
        end = Offset(xEnd, yEnd) + offset,
        strokeWidth = width.toFloat(),
        color = Color.Black,
    )
}

private fun Offset.toIntOffset() = IntOffset(x.toInt(), y.toInt())
private fun Size.toIntSize() = IntSize(width.toInt(), height.toInt())
