package com.getmorebrain.bitmark.parser

import com.getmorebrain.bitmark.BitmarkLexer
import com.getmorebrain.bitmark.BitmarkParser
import com.getmorebrain.bitmark.model.Bit
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import java.io.File
import java.io.FileInputStream
import java.nio.charset.StandardCharsets


class BitmarkConverter {

    fun parse(bitmarkString: String) = parse(CharStreams.fromString(bitmarkString))

    fun parse(file: File) = parse(CharStreams.fromStream(FileInputStream(file), StandardCharsets.UTF_8))

    fun parse(charStream: CharStream): List<Bit> {
        val lexer = BitmarkLexer(charStream)
        val tokens = CommonTokenStream(lexer)
        val parser = BitmarkParser(tokens)
        val walker = ParseTreeWalker()
        val listener = BitmarkListenerImpl()
        walker.walk(listener, parser.bitBook())
        return listener.bits()
    }
}
