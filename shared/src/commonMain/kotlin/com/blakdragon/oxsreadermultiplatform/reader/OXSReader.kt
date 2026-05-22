package com.blakdragon.oxsreadermultiplatform.reader

import com.blakdragon.oxsreadermultiplatform.reader.models.BackStitch
import com.blakdragon.oxsreadermultiplatform.reader.models.FullStitch
import com.blakdragon.oxsreadermultiplatform.reader.models.Ornament
import com.blakdragon.oxsreadermultiplatform.reader.models.Palette
import com.blakdragon.oxsreadermultiplatform.reader.models.PaletteItem
import com.blakdragon.oxsreadermultiplatform.reader.models.PartialStitch
import com.blakdragon.oxsreadermultiplatform.reader.models.Pattern
import com.blakdragon.oxsreadermultiplatform.reader.models.PatternOverview
import com.fleeksoft.ksoup.Ksoup
import oxsreadermultiplatform.shared.generated.resources.Res

object OXSReader {

    suspend fun read(fileName: String): Pattern {
        val xml = Res.readBytes(fileName)
        val doc = Ksoup.parseXml(xml.decodeToString())

        val propsElement = doc.select("properties")

        val patternOverview = PatternOverview(
            title = propsElement.attr("charttitle"),
            author = propsElement.attr("author"),
            copyright = propsElement.attr("copyright"),
            instructions = propsElement.attr("instructions"),
            chartHeight = propsElement.attr("chartheight").toInt(),
            chartWidth = propsElement.attr("chartWidth").toInt(),
            stitchesPerInch = propsElement.attr("stitchesperinch").toInt(),
            paletteCount = propsElement.attr("palettecount").toInt(),
            currentColour = propsElement.attr("currentcolor").toInt(),
        )

        val paletteElements = doc.select("palette_item")

        val palette = Palette(items = paletteElements.map {
            PaletteItem(
                index = it.attr("index").toInt(),
                number = it.attr("number"),
                name = it.attr("name"),
                color = it.attr("color"),
                strands = it.attr("strands").toInt(),
                backStitchStrands = it.attr("bsstrands").toInt(),
                symbol = it.attr("symbol"),
            )
        })

        val fullStitchesElements = doc.select("stitch")
        val partialStitchesElements = doc.select("partstitch")
        val backStitchesElements = doc.select("backstitch")
        val ornamentsElements = doc.select("object")

        val pattern = Pattern(
            overview = patternOverview,
            palette = palette,
            fullStitches = fullStitchesElements.map {
                FullStitch(
                    x = it.attr("x").toFloat(),
                    y = it.attr("y").toFloat(),
                    paletteIndex = it.attr("palindex").toInt(),
                )
            },
            partialStitches = partialStitchesElements.map {
                PartialStitch(
                    x = it.attr("x").toFloat(),
                    y = it.attr("y").toFloat(),
                    paletteIndex1 = it.attr("palindex1").toInt(),
                    paletteIndex2 = it.attr("palindex2").toInt(),
                    direction = it.attr("direction").toInt(),
                )
            },
            backStitches = backStitchesElements.map {
                BackStitch(
                    x1 = it.attr("x1").toFloat(),
                    x2 = it.attr("x2").toFloat(),
                    y1 = it.attr("y1").toFloat(),
                    y2 = it.attr("y2").toFloat(),
                    paletteIndex = it.attr("palindex").toInt(),
                )
            },
            ornaments = ornamentsElements.map {
                Ornament(
                    x = it.attr("x1").toFloat(),
                    y = it.attr("y1").toFloat(),
                    paletteIndex = it.attr("palindex").toInt(),
                )
            },
        )

        return pattern
    }
}
