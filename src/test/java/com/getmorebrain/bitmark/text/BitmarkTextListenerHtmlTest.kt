package com.getmorebrain.bitmark.text

import org.junit.Assert
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.streams.asSequence

class BitmarkTextListenerHtmlTest {

    @Test
    fun testHtmlConversion() {
        val stream = this.javaClass.classLoader.getResourceAsStream("examples/bitmarktext/bitmarkpp.txt")
        val html = AntlrBitmarkTextConverter(bitmarkTextListener = BitmarkTextListenerHtml()).convert(
            BufferedReader(InputStreamReader(stream)).lines().asSequence().fold("") { l, r -> "$l\n$r" }
        )
        assertNotNull(html)
        println(html)
    }

    @Test
    fun testHtmlListConversion_SingleItems() {
        val input = """This is a list with a single item
1. This"""
        val html = AntlrBitmarkTextConverter(bitmarkTextListener = BitmarkTextListenerHtml()).convert(input)
        Assert.assertEquals(html, "This is a list with a single item<ol><li>This</li></ol>")
    }

    @Test
    fun testHtmlListConversion_MultipleItems() {
        val input = """This is a list
1. This
1. Is
1. A
1. List"""
        val html = AntlrBitmarkTextConverter(bitmarkTextListener = BitmarkTextListenerHtml()).convert(input)
        Assert.assertEquals(
            "This is a list<ol><li>This</li><li>Is</li><li>A</li><li>List</li></ol>", html
        )
    }

    @Test
    fun testHtmlListConversionWithListOnly_MultipleItems() {
        val input = """1. This is a list with only a single element"""
        val html = AntlrBitmarkTextConverter(bitmarkTextListener = BitmarkTextListenerHtml()).convert(input)
        Assert.assertEquals("<ol><li>This is a list with only a single element</li></ol>", html)
    }

    @Test
    fun testListNotAtEnd() {
        val input = """List:
1. W[_o]rld.
1. Goodbye

Frank."""
        val html = AntlrBitmarkTextConverter(bitmarkTextListener = BitmarkTextListenerHtml()).convert(input)
        Assert.assertEquals(
            """List:<ol><li>W[_o]rld.</li><li>Goodbye</li></ol>

Frank.""", html
        )
    }
}
