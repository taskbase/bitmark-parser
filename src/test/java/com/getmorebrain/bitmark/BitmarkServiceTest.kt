package com.getmorebrain.bitmark

import com.getmorebrain.bitmark.model.ClozeBit
import com.getmorebrain.bitmark.model.MarkBit
import com.google.gson.GsonBuilder
import org.antlr.v4.runtime.CharStreams
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class BitmarkServiceTest {

    val uuid = "4cb8950b-c7b4-492c-b54d-69711c0fd607"

    @Test
    fun testParse() {
        val bitmark = "[.cloze][!Fill in the gaps!]This sentence is a [_cloze][_gap text][!noun] with [_2][?1 or 2] " +
                "gaps including an instruction for the first and a hint for the second gap."
        val bits = BitmarkService().parse(bitmark)
        val clozeBit = bits.first().bit as ClozeBit
        assertNotNull(clozeBit)
        assertEquals("cloze", clozeBit.type)
        assertEquals(2, clozeBit.gaps.size)
        assertTrue(clozeBit.gaps.keys.contains("$uuid-0"))
        assertTrue(clozeBit.gaps.keys.contains("$uuid-1"))
        assertEquals(listOf("cloze", "gap text"), clozeBit.gaps["$uuid-0"]?.solutions)
        assertEquals("noun", clozeBit.gaps["$uuid-0"]?.instruction)
        assertEquals("2", clozeBit.gaps["$uuid-1"]?.solutions?.first())
        assertEquals("1 or 2", clozeBit.gaps["$uuid-1"]?.hint)
        assertEquals(
            "This sentence is a $uuid-0 with $uuid-1 gaps including an instruction for the first and a hint for the second gap.",
            clozeBit.body
        )
        assertEquals(bitmark, bits.first().bitmark)
        assertEquals(null, clozeBit.hint)
        assertEquals(
            "This sentence is a [_cloze][_gap text][!noun] with [_2][?1 or 2] gaps including " +
                    "an instruction for the first and a hint for the second gap.", bits.first().bodyBitmark
        )
        assertEquals("[!Fill in the gaps!]", bits.first().instructionBitmark)
        println(GsonBuilder().setPrettyPrinting().create().toJson(clozeBit))
    }

    @Test
    fun testParseEmoticons() {
        val stream = this.javaClass.classLoader.getResourceAsStream("examples/cloze_emoticons.bit")
        val bits = BitmarkService().parse(CharStreams.fromStream(stream, Charsets.UTF_8))
        assertNotNull(bits.firstOrNull())
        println(GsonBuilder().setPrettyPrinting().create().toJson(bits.first()))
    }

    @Test
    fun testParseAttachment() {
        val stream = this.javaClass.classLoader.getResourceAsStream("examples/cloze_attachment.bit")
        val bits = BitmarkService().parse(CharStreams.fromStream(stream, Charsets.UTF_8))
        val clozeBit = bits.first().bit as ClozeBit
        assertNotNull(clozeBit.image)
        assertNull(clozeBit.audio)
        println(GsonBuilder().setPrettyPrinting().create().toJson(bits.first()))
    }

    @Test
    fun testParseBitmarkFormat() {
        val stream = this.javaClass.classLoader.getResourceAsStream("examples/cloze_bitmark--.bit")
        val bits = BitmarkService().parse(CharStreams.fromStream(stream, Charsets.UTF_8))
        val clozeBit = bits.first().bit as ClozeBit
        assertEquals("bitmark--", clozeBit.format)
        println(GsonBuilder().setPrettyPrinting().create().toJson(bits.first()))
    }

    @Test
    fun testParseInstruction() {
        val bitmark = "[.cloze]\n[!some instruction] hello bitmark [_gap 1][_gap 2] with two gaps."
        val bits = BitmarkService().parse(bitmark)
        val bit = bits.first().bit as ClozeBit
        assertEquals("some instruction", bit.instruction)
        assertEquals(bitmark, bits.first().bitmark)
    }

    @Test
    fun testParseClozeWithoutCloze() {
        val bitmark = "[.cloze]\nhello bitmark."
        val bits = BitmarkService().parse(bitmark)
        val bit = bits.first().bit as ClozeBit
        assertEquals("\nhello bitmark.", bit.body)
    }

    @Test
    fun testClozeWithList() {
        val body = "one\n" +
                "1. test\n" +
                "1. two"
        val bitmark = "[.cloze][!hello]$body"
        val bits = BitmarkService().parse(bitmark)
        val bit = bits.first().bit as ClozeBit
        assertEquals(body, bit.body)
    }

    @Test
    fun testClozeWithNewlines() {
        val body = "a\n" +
                "b\n" +
                "c"
        val bitmark = "[.cloze][!hello]$body"
        val bits = BitmarkService().parse(bitmark)
        val bit = bits.first().bit as ClozeBit
        assertEquals(body, bit.body)
    }

    @Test
    fun testPreserveNewlines() {
        val bitmark = """[.cloze][!Hello]
Line 1
Line 2"""
        val bit = BitmarkService().parse(bitmark).first()
        assertEquals(
            """
Line 1
Line 2""", bit.bit.body
        )
    }

    @Test
    fun testClozeAtStart() {
        val bit = BitmarkService().parse("[.cloze][_Hallo] du.").first()
        assertEquals("$uuid-0 du.", bit.bit.body)
    }

    @Test
    fun testClozeAtEnd() {
        val bit = BitmarkService().parse("[.cloze]test [_test]").first()
        assertEquals("test $uuid-0", bit.bit.body)
    }

    @Test
    fun testEmptyCloze() {
        val bit = BitmarkService().parse("[.cloze]").first()
        assertNull(bit.instructionBitmark)
        assertNull(bit.bodyBitmark)
    }

    @Test
    fun testMark() {
        val bitmark = """[.mark]
[!Please mark all ==verbs==(blue)== and ==nouns==(red)==.]

When ['Diaz][@mark:red] ['returned][@mark:blue] and ['seated][@mark:blue] himself to ['play][@mark:blue] the 
['Berceuse][@mark:red], I ['saw][@mark:blue] that he ['could][@mark:blue] ['look][@mark:blue] at me without 
['turning][@mark:blue] his ['head][@mark:red]."""

        val bit = BitmarkService().parse(bitmark).first().bit as MarkBit
        assertEquals("Diaz", bit.marks["{1}"]?.text)
        assertEquals("returned", bit.marks["{2}"]?.text)
        assertEquals("seated", bit.marks["{3}"]?.text)
        assertEquals("blue", bit.marks["{3}"]?.mark)

    }
}
