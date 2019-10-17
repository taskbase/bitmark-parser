package com.getmorebrain.bitmark.text

import org.junit.Assert
import org.junit.Test
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.streams.asSequence

class BitmarkTextListenerLatexTest {

    @Test
    fun testLatexConversion() {
        val stream = this.javaClass.classLoader.getResourceAsStream("examples/bitmarktext/bitmarkpp.txt")
        val laTeX = AntlrBitmarkTextConverter(bitmarkTextListener = BitmarkTextListenerLatex()).convert(
            BufferedReader(InputStreamReader(stream)).lines().asSequence().fold("") { l, r -> "$l\n$r" }
        )
        Assert.assertNotNull(laTeX)
        println(laTeX)
    }
}