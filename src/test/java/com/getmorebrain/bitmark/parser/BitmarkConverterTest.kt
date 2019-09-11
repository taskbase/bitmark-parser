package com.getmorebrain.bitmark.parser

import com.getmorebrain.bitmark.parser.model.ClozeBit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BitmarkConverterTest {

    @Test
    fun testParse() {
        val bits = BitmarkConverter().parse("[.cloze] This sentence is a [_cloze][_gap text][!noun] with [_2][?1 or 2] gaps including an instruction for the first and a hint for the second gap.")
        val clozeBit = bits.first() as ClozeBit
        assertNotNull(clozeBit)
        assertEquals("cloze", clozeBit.type)
        assertEquals(2, clozeBit.gaps.size)
        assertTrue(clozeBit.gaps.keys.contains("{0}"))
        assertTrue(clozeBit.gaps.keys.contains("{1}"))
        assertEquals(listOf("cloze", "gap text"), clozeBit.gaps.values.first().solutions)
        assertEquals("noun", clozeBit.gaps["{0}"]!!.instruction)
        assertEquals("2", clozeBit.gaps["{1}"]!!.solutions.first())
        assertEquals("1 or 2", clozeBit.gaps["{1}"]!!.hint)

        println(GsonBuilder().setPrettyPrinting().create().toJson(clozeBit))
    }

}