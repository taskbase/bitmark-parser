package com.getmorebrain.bitmark.text

import org.antlr.v4.runtime.CharStreams
import org.junit.Test

class BitmarkTextListenerLatexTest {

    @Test
    fun testLaTeXconversion() {
        val stream = this.javaClass.classLoader.getResourceAsStream("examples/bitmarktext/bitmarkpp.txt")
        val laTeX = BitmarkTextConverter(bitmarkTextListener = BitmarkTextListenerLatex()).convert(
            CharStreams.fromStream(
                stream,
                Charsets.UTF_8
            )
        )
        println(laTeX)
    }
}