package com.getmorebrain.bitmark.text

import org.antlr.v4.runtime.CharStreams
import org.junit.Test
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.streams.asSequence

class BitmarkTextListenerLatexTest {

    @Test
    fun testLaTeXconversion() {
        val stream = this.javaClass.classLoader.getResourceAsStream("examples/bitmarktext/bitmarkpp.txt")
        val laTeX = AntlrBitmarkTextConverter(bitmarkTextListener = BitmarkTextListenerLatex()).convert(
            BufferedReader(InputStreamReader(stream)).lines().asSequence().fold("") { l, r -> "$l\n$r" }
        )
        println(laTeX)
    }
}