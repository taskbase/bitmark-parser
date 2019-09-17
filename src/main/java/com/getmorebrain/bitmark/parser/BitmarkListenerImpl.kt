package com.getmorebrain.bitmark.parser

import com.getmorebrain.bitmark.BitmarkListener
import com.getmorebrain.bitmark.BitmarkParser
import com.getmorebrain.bitmark.model.Bit
import com.getmorebrain.bitmark.model.ClozeBit
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.TerminalNode
import java.util.logging.Logger

class BitmarkListenerImpl(
        private val log: Logger = Logger.getLogger(BitmarkListenerImpl::class.java.name)
) : BitmarkListener {

    private val bits: MutableList<Bit> = ArrayList()

    fun bits(): List<Bit> = bits

    override fun enterCloze(ctx: BitmarkParser.ClozeContext) {

        val defaultValues = ClozeBit()
        val format = ctx.clozeType().BITMARK_TYPE()?.text?.let { it.drop(1) } ?: defaultValues.format

        val attachment: String? = ctx.attachment()?.let { attachmentContext: BitmarkParser.AttachmentContext ->
            treeToString(attachmentContext.string())
        }

        val image: String? = if (ctx.clozeType().ATTACHMENT()?.text == "&image") {
            attachment
        } else {
            null
        }

        val audio: String? = if (ctx.clozeType().ATTACHMENT()?.text == "&audio") {
            attachment
        } else {
            null
        }

        val article = ClozeBit().article // TODO extend parser and test cases
        val instruction = ctx.instruction()?.string()?.let { treeToString(it) } ?: defaultValues.instruction
        val gaps: Map<String, ClozeBit.ClozeGap> = ctx.clozeText().flatMap { clozeText: BitmarkParser.ClozeTextContext ->
            clozeText.gapChain().mapIndexed { i: Int, gapChain: BitmarkParser.GapChainContext ->
                Pair("{$i}",
                        ClozeBit.ClozeGap(
                                solutions = gapChain.gap().map { gap: BitmarkParser.GapContext ->
                                    treeToString(gap.string())
                                },
                                hint = gapChain.gapHint().firstOrNull()?.string()?.let { treeToString(it) },
                                instruction = gapChain.gapInstruction().firstOrNull()?.string()?.let { treeToString(it) }
                        ))
            }
        }.toMap()

        val cloze = ClozeBit(
                format = format,
                image = image,
                audio = audio,
                article = article,
                item = "",
                instruction = instruction,
                hint = "",
                body = "",
                gaps = gaps
        )

        bits.add(cloze)
    }

    override fun exitCloze(ctx: BitmarkParser.ClozeContext?) {
    }

    override fun exitClozeType(ctx: BitmarkParser.ClozeTypeContext?) {
    }

    override fun exitClozeText(ctx: BitmarkParser.ClozeTextContext?) {
    }

    override fun enterGap(ctx: BitmarkParser.GapContext?) {
    }

    override fun enterGapInstruction(ctx: BitmarkParser.GapInstructionContext?) {
    }

    override fun exitGapInstruction(ctx: BitmarkParser.GapInstructionContext?) {
    }

    override fun enterMultipleChoice(ctx: BitmarkParser.MultipleChoiceContext?) {
    }

    override fun exitMultipleChoice(ctx: BitmarkParser.MultipleChoiceContext?) {
    }

    override fun enterCorrectOption(ctx: BitmarkParser.CorrectOptionContext?) {
    }

    override fun exitCorrectOption(ctx: BitmarkParser.CorrectOptionContext?) {
    }

    override fun enterWrongOption(ctx: BitmarkParser.WrongOptionContext?) {
    }

    override fun enterEveryRule(ctx: ParserRuleContext?) {
    }

    override fun exitEveryRule(ctx: ParserRuleContext?) {
    }

    override fun exitWrongOption(ctx: BitmarkParser.WrongOptionContext?) {
    }

    override fun enterString(ctx: BitmarkParser.StringContext?) {
    }

    override fun exitString(ctx: BitmarkParser.StringContext?) {
    }

    override fun enterInstruction(ctx: BitmarkParser.InstructionContext?) {
    }

    override fun exitInstruction(ctx: BitmarkParser.InstructionContext?) {
    }

    override fun exitAttachment(ctx: BitmarkParser.AttachmentContext?) {
    }

    override fun exitBitBook(ctx: BitmarkParser.BitBookContext?) {
    }

    override fun enterBit(ctx: BitmarkParser.BitContext?) {
    }

    override fun exitBit(ctx: BitmarkParser.BitContext?) {
    }

    override fun enterClozeType(ctx: BitmarkParser.ClozeTypeContext?) {
    }

    override fun enterClozeText(ctx: BitmarkParser.ClozeTextContext?) {
    }

    override fun enterGapChain(ctx: BitmarkParser.GapChainContext?) {
    }

    override fun exitGapChain(ctx: BitmarkParser.GapChainContext?) {
    }

    override fun exitGap(ctx: BitmarkParser.GapContext?) {
    }

    override fun enterGapHint(ctx: BitmarkParser.GapHintContext?) {
    }

    override fun exitGapHint(ctx: BitmarkParser.GapHintContext?) {
    }

    override fun visitErrorNode(node: ErrorNode) {
        log.severe(node.text)
    }

    override fun visitTerminal(node: TerminalNode?) {
    }

    override fun enterAttachment(ctx: BitmarkParser.AttachmentContext?) {
    }

    override fun enterBitBook(ctx: BitmarkParser.BitBookContext?) {
    }

    /*
     * Helper functions
     */
    fun treeToString(ctx: BitmarkParser.StringContext): String {
        val sb = StringBuilder()
        ctx.STRING_CHAR().forEach { char -> sb.append(char.text) }
        return sb.toString()
    }
}