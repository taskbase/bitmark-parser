package com.getmorebrain.bitmark.parser

import com.getmorebrain.bitmark.model.ClozeBit
import com.google.gson.GsonBuilder
import org.antlr.v4.runtime.CharStreams
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
        assertEquals("noun", clozeBit.gaps["{0}"]?.instruction)
        assertEquals("2", clozeBit.gaps["{1}"]?.solutions?.first())
        assertEquals("1 or 2", clozeBit.gaps["{1}"]?.hint)
        println(GsonBuilder().setPrettyPrinting().create().toJson(clozeBit))
    }

    @Test
    fun testParseEmoticons() {
        val stream = this.javaClass.classLoader.getResourceAsStream("examples/cloze_emoticons.bit")
        val bits = BitmarkConverter().parse(CharStreams.fromStream(stream, Charsets.UTF_8))
        assertNotNull(bits.firstOrNull())
        println(GsonBuilder().setPrettyPrinting().create().toJson(bits.first()))
    }

    @Test
    fun testParseAttachment() {
        val stream = this.javaClass.classLoader.getResourceAsStream("examples/cloze_attachment.bit")
        val bits = BitmarkConverter().parse(CharStreams.fromStream(stream, Charsets.UTF_8))
        val clozeBit = bits.first() as ClozeBit
        assertNotNull(clozeBit.image)
        assertNull(clozeBit.audio)
        println(GsonBuilder().setPrettyPrinting().create().toJson(bits.first()))
    }

    @Test
    fun testParseBitmarkFormat() {
        val stream = this.javaClass.classLoader.getResourceAsStream("examples/cloze_bitmark--.bit")
        val bits = BitmarkConverter().parse(CharStreams.fromStream(stream, Charsets.UTF_8))
        val clozeBit = bits.first() as ClozeBit
        assertEquals("bitmark--", clozeBit.format)
        println(GsonBuilder().setPrettyPrinting().create().toJson(bits.first()))
    }

}