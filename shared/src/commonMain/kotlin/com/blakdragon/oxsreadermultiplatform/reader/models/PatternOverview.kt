package com.blakdragon.oxsreadermultiplatform.reader.models

data class PatternOverview(
    val title: String = "",
    val author: String = "",
    val copyright: String = "",
    val instructions: String = "",
    val chartHeight: Int = 0,
    val chartWidth: Int = 0,
    val stitchesPerInch: Int = 0,
    val paletteCount: Int = 0,
    val currentColour: Int = 0,
)
