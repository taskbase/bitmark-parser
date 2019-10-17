package com.getmorebrain.bitmark

import com.getmorebrain.bitmark.model.Bit
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker

interface BitExtractor : BitmarkListener {

    fun bits(): List<Bit>

    fun parse(bitmarkString: String): List<Bit> = parse(CharStreams.fromString(bitmarkString))

    fun parse(charStream: CharStream): List<Bit> {
        val lexer = BitmarkLexer(charStream)
        val tokens = CommonTokenStream(lexer)
        val parser = BitmarkParser(tokens)
        val walker = ParseTreeWalker()
        walker.walk(this, parser.bitBook())
        return this.bits()
    }
}