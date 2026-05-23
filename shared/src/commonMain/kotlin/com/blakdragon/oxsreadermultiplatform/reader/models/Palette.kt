package com.blakdragon.oxsreadermultiplatform.reader.models

data class Palette(val items: List<PaletteItem> = emptyList())

data class PaletteItem(
    val index: Int = 0,
    val number: String = "",
    val name: String = "",
    val color: String = "",
    val strands: Int = 0,
    val backStitchStrands: Int = 0,
    val symbol: String = "",
)
