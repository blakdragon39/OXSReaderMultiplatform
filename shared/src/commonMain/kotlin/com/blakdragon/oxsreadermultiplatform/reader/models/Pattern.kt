package com.blakdragon.oxsreadermultiplatform.reader.models

data class Pattern(
    val overview: PatternOverview = PatternOverview(),
    val palette: Palette = Palette(),
    val fullStitches: List<FullStitch> = listOf(),
    val partialStitches: List<PartialStitch> = listOf(),
    val backStitches: List<BackStitch> = listOf(),
    val ornaments: List<Ornament> = listOf(),
)

data class FullStitch(
    val x: Float,
    val y: Float,
    val paletteIndex: Int,
)

data class PartialStitch(
    val x: Float,
    val y: Float,
    val paletteIndex1: Int,
    val paletteIndex2: Int,
    val direction: Int,
)

data class BackStitch(
    val x1: Float,
    val x2: Float,
    val y1: Float,
    val y2: Float,
    val paletteIndex: Int,
)

data class Ornament(
    val x: Float,
    val y: Float,
    val paletteIndex: Int,
)

