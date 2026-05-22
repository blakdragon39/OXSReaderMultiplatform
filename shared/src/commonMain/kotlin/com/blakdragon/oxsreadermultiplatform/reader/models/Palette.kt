package com.blakdragon.oxsreadermultiplatform.reader.models

data class Palette(val items: List<PaletteItem>)

data class PaletteItem(
    val index: Int,
    val number: String,
    val name: String,
    val color: String,
    val strands: Int,
    val backStitchStrands: Int,
    val symbol: String,
)
