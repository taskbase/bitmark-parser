package com.getmorebrain.bitmark

import com.getmorebrain.bitmark.model.Bit
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import java.io.File
import java.io.FileInputStream
import java.nio.charset.StandardCharsets


class BitmarkConverter(
    private val bitmarkListener: BitExtractor = BitmarkListenerImpl()
) {

    fun parse(bitmarkString: String): List<Bit> = parse(CharStreams.fromString(bitmarkString))

    fun parse(file: File): List<Bit> = parse(CharStreams.fromStream(FileInputStream(file), StandardCharsets.UTF_8))

    fun parse(charStream: CharStream): List<Bit> {
        val lexer = BitmarkLexer(charStream)
        val tokens = CommonTokenStream(lexer)
        val parser = BitmarkParser(tokens)
        val walker = ParseTreeWalker()
        walker.walk(bitmarkListener, parser.bitBook())
        return bitmarkListener.bits()
    }
}
