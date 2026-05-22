package com.blakdragon.oxsreadermultiplatform.ui

import com.blakdragon.oxsreadermultiplatform.reader.models.FullStitch
import com.blakdragon.oxsreadermultiplatform.reader.models.Palette
import com.blakdragon.oxsreadermultiplatform.reader.models.Pattern
import com.blakdragon.oxsreadermultiplatform.reader.models.PatternOverview

val patternPreview = Pattern(
    overview = PatternOverview(
        title = "Title",
        author = "",
        copyright = "",
        instructions = "",
        chartHeight = 10,
        chartWidth = 10,
        stitchesPerInch = 14,
        paletteCount = 0,
        currentColour = 0,
    ),
    palette = Palette(
        items = listOf(),
    ),
    fullStitches = listOf(
        FullStitch(0f, 0f, 1),
        FullStitch(1f, 1f, 1),
        FullStitch(2f, 2f, 1),
        FullStitch(1f, 2f, 1),
        FullStitch(2f, 1f, 1),
    ),
    partialStitches = listOf(),
    backStitches = listOf(),
    ornaments = listOf(),
)