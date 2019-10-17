package com.getmorebrain.bitmark.text

import org.junit.Assert.assertNotNull
import org.junit.Test
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.streams.asSequence

class BitmarkTextListenerHtmlTest {

    @Test
    fun testHtmlConversion() {
        val stream = this.javaClass.classLoader.getResourceAsStream("examples/bitmarktext/bitmarkpp.txt")
        val laTeX = AntlrBitmarkTextConverter(bitmarkTextListener = BitmarkTextListenerHtml()).convert(
            BufferedReader(InputStreamReader(stream)).lines().asSequence().fold("") { l, r -> "$l\n$r" }
        )
        assertNotNull(laTeX)
        println(laTeX)
    }
}