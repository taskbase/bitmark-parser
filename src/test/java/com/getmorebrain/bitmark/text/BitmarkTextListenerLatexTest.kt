package com.getmorebrain.bitmark.text

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
        assertNotNull(laTeX)
        println(laTeX)
    }

    @Test
    fun testLatexListConversion() {
        val input = """This is a list
1. This
1. Is
1. A
1. List
"""
        val laTeX = AntlrBitmarkTextConverter(bitmarkTextListener = BitmarkTextListenerLatex()).convert(input)

        assertEquals(
            """This is a list
\begin{enumerate}
\item This
\item Is
\item A
\item List
\end{enumerate}
""", laTeX
        )

    }
}
