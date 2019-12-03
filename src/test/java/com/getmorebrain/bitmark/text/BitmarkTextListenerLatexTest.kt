package com.getmorebrain.bitmark.text

import org.junit.Assert
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
    }

    @Test
    fun testEscaping() {
        val input = """Those characters are escaped: # ${'$'} % ^ & _ { } ~ \"""
        val laTeX = AntlrBitmarkTextConverter(bitmarkTextListener = BitmarkTextListenerLatex()).convert(input)
        assertEquals(
            """Those characters are escaped: \# \${'$'} \% \^{} \& \_ \{ \} \~{} \textbackslash{}""", laTeX
        )
    }


    @Test
    fun testLatexListConversion() {
        val input = """This is a list
1. This
1. Is
1. A
1. List"""
        val laTeX = AntlrBitmarkTextConverter(bitmarkTextListener = BitmarkTextListenerLatex()).convert(input)
        assertEquals(
            """This is a list
\begin{enumerate}[label={\arabic*.}]
\item This
\item Is
\item A
\item List
\end{enumerate}
""", laTeX
        )

    }

    @Test
    fun testHighlightPenName() {
        val input = """This is a text with a ==highlighted==(green)== part that is colored in green."""
        val html = AntlrBitmarkTextConverter(bitmarkTextListener = BitmarkTextListenerLatex()).convert(input)
        assertEquals(
            html,
            "This is a text with a {\\color{green} highlighted} part that is colored in green."
        )
    }
}
