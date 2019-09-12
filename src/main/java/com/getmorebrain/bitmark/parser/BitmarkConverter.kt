package com.getmorebrain.bitmark.parser

import com.getmorebrain.bitmark.BitmarkLexer
import com.getmorebrain.bitmark.BitmarkListener
import com.getmorebrain.bitmark.BitmarkParser
import com.getmorebrain.bitmark.parser.model.Bit
import com.getmorebrain.bitmark.parser.model.ClozeBit
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.TerminalNode
import java.io.File
import java.io.FileInputStream
import java.nio.charset.StandardCharsets
import org.antlr.v4.runtime.tree.ParseTreeWalker
import java.util.logging.Logger


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

class BitmarkListenerImpl(
        private val log: Logger = Logger.getLogger(BitmarkListenerImpl::class.java.name)
) : BitmarkListener {

    private val bits: MutableList<Bit> = ArrayList()
    private var cloze: ClozeBit = ClozeBit()
    private var clozeGap: Pair<String, ClozeBit.ClozeGap> = Pair("{0}", ClozeBit.ClozeGap())

    fun bits(): List<Bit> = bits

    override fun enterBitBook(ctx: BitmarkParser.BitBookContext) {
    }

    override fun exitBitBook(ctx: BitmarkParser.BitBookContext) {
    }

    override fun enterBit(ctx: BitmarkParser.BitContext) {
    }

    override fun exitBit(ctx: BitmarkParser.BitContext) {
    }

    override fun exitMultipleChoice(ctx: BitmarkParser.MultipleChoiceContext) {
    }

    override fun visitErrorNode(node: ErrorNode) {
        log.severe(node.text)
    }

    override fun enterMultipleChoice(ctx: BitmarkParser.MultipleChoiceContext) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun enterCorrectOption(ctx: BitmarkParser.CorrectOptionContext) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun exitCorrectOption(ctx: BitmarkParser.CorrectOptionContext) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun enterWrongOption(ctx: BitmarkParser.WrongOptionContext) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun exitWrongOption(ctx: BitmarkParser.WrongOptionContext) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun enterMultipleChoiceInstruction(ctx: BitmarkParser.MultipleChoiceInstructionContext?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun exitMultipleChoiceInstruction(ctx: BitmarkParser.MultipleChoiceInstructionContext?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun enterCloze(ctx: BitmarkParser.ClozeContext) {
        cloze = ClozeBit()
    }

    override fun exitCloze(ctx: BitmarkParser.ClozeContext) {
        bits.add(cloze)
    }

    override fun enterClozeInstruction(ctx: BitmarkParser.ClozeInstructionContext) {
        cloze = cloze.copy(instruction = ctx.STRING().symbol.text)
    }

    override fun exitClozeInstruction(ctx: BitmarkParser.ClozeInstructionContext) {
    }

    override fun enterClozeText(ctx: BitmarkParser.ClozeTextContext) {
    }

    override fun enterGapHint(ctx: BitmarkParser.GapHintContext) {
        clozeGap = clozeGap.copy(second = clozeGap.second.copy(hint = ctx.STRING().symbol.text))
    }

    override fun exitGapHint(ctx: BitmarkParser.GapHintContext) {
    }

    override fun enterGapChain(ctx: BitmarkParser.GapChainContext?) {
        clozeGap = Pair("{${cloze.gaps.size}}", ClozeBit.ClozeGap())
    }

    override fun exitGapChain(ctx: BitmarkParser.GapChainContext?) {
        cloze = cloze.copy(gaps = cloze.gaps + clozeGap)
    }

    override fun enterGap(ctx: BitmarkParser.GapContext) {
        clozeGap = clozeGap.copy(second = clozeGap.second.copy(solutions = clozeGap.second.solutions + ctx.STRING().symbol.text))
    }

    override fun exitGap(ctx: BitmarkParser.GapContext) {
    }

    override fun enterGapInstruction(ctx: BitmarkParser.GapInstructionContext) {
        clozeGap = clozeGap.copy(second = clozeGap.second.copy(instruction = ctx.STRING().symbol.text))
    }

    override fun exitGapInstruction(ctx: BitmarkParser.GapInstructionContext) {
    }

    override fun visitTerminal(node: TerminalNode) {
    }

    override fun exitClozeText(ctx: BitmarkParser.ClozeTextContext) {
    }

    override fun enterEveryRule(ctx: ParserRuleContext) {
    }

    override fun exitEveryRule(ctx: ParserRuleContext) {
    }
}

