package com.getmorebrain.bitmark.text

import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import java.io.File
import java.io.FileInputStream
import java.nio.charset.StandardCharsets

class BitmarkTextConverter(private val bitmarkTextListener: MarkupExtractor) {

    fun convert(bitmarkString: String): String = convert(CharStreams.fromString(bitmarkString))

    fun convert(file: File): String = convert(CharStreams.fromStream(FileInputStream(file), StandardCharsets.UTF_8))

    fun convert(charStream: CharStream): String {
        val lexer = BitmarktextLexer(charStream)
        val tokens = CommonTokenStream(lexer)
        val parser = BitmarktextParser(tokens)
        val walker = ParseTreeWalker()
        walker.walk(bitmarkTextListener, parser.bitmarkPlusPlus())
        return bitmarkTextListener.markup()
    }
}