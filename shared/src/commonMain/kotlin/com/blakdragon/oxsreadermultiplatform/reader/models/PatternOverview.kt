package com.blakdragon.oxsreadermultiplatform.reader.models

data class PatternOverview(
    val title: String,
    val author: String,
    val copyright: String,
    val instructions: String,
    val chartHeight: Int,
    val chartWidth: Int,
    val stitchesPerInch: Int,
    val paletteCount: Int,
    val currentColour: Int,
)
