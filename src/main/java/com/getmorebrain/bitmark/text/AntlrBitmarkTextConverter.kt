package com.getmorebrain.bitmark.text

import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker

open class AntlrBitmarkTextConverter(private val bitmarkTextListener: MarkupExtractor) : BitmarkTextConverter {
    override fun convert(bitmarkText: String): String {
        return convert(bitmarkTextListener, CharStreams.fromString(bitmarkText))
    }
}

fun convert(bitmarkTextListener: MarkupExtractor, charStream: CharStream): String {
    val lexer = BitmarktextLexer(charStream)
    val tokens = CommonTokenStream(lexer)
    val parser = BitmarktextParser(tokens)
    val walker = ParseTreeWalker()
    walker.walk(bitmarkTextListener, parser.bitmarkPlusPlus())
    return bitmarkTextListener.markup()
}