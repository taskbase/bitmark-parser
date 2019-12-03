package com.getmorebrain.bitmark

import com.getmorebrain.bitmark.model.BitmarkBit
import com.getmorebrain.bitmark.model.ClozeBit
import com.getmorebrain.bitmark.model.MarkBit
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.misc.Interval
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.TerminalNode
import java.util.logging.Logger

class BitmarkService(private val log: Logger = Logger.getLogger(BitmarkService::class.java.name)) : BitExtractor {

    private val bits: MutableList<BitmarkBit> = ArrayList()

    override fun bits(): List<BitmarkBit> = bits

    override fun enterCloze(ctx: BitmarkParser.ClozeContext) {
        val defaultValues = ClozeBit()
        val format = ctx.clozeType().BITMARK_TYPE()?.text?.let { it.drop(1) } ?: defaultValues.format

        val attachment: String? =
            ctx.clozeBody()?.attachment()?.let { attachmentContext: BitmarkParser.AttachmentContext ->
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

        val body = StringBuilder()

        val instruction = ctx.instruction()?.string()?.let { treeToString(it) } ?: defaultValues.instruction
        var gapCount = 0
        val gaps: MutableMap<String, ClozeBit.ClozeGap> = HashMap()
        val uuid = "4cb8950b-c7b4-492c-b54d-69711c0fd607" // to avoid collisions with user-generated content
        ctx.clozeBody()?.clozeText()?.forEach { clozeText: BitmarkParser.ClozeTextContext ->
            clozeText.children.forEach { stringOrGapChain ->
                when (stringOrGapChain) {
                    is BitmarkParser.StringContext -> {
                        body.append(treeToString(stringOrGapChain))
                    }
                    is BitmarkParser.GapChainContext -> {
                        val gapPlaceHolder = "$uuid-${gapCount++}"
                        body.append(gapPlaceHolder)
                        gaps[gapPlaceHolder] = ClozeBit.ClozeGap(
                            solutions = stringOrGapChain.gap().map { gap: BitmarkParser.GapContext ->
                                treeToString(gap.string())
                            },
                            hint = stringOrGapChain.gapHint().firstOrNull()?.string()?.let { treeToString(it) },
                            instruction = stringOrGapChain.gapInstruction().firstOrNull()?.string()?.let {
                                treeToString(
                                    it
                                )
                            }
                        )
                    }
                    else -> throw IllegalStateException("Cloze text can only contain strings and gap chains")
                }
            }
        }

        val bodyString = body.toString()

        val cloze = ClozeBit(
            format = format,
            image = image,
            audio = audio,
            article = null,
            item = null,
            instruction = instruction,
            hint = null,
            body = if (bodyString.isNotBlank()) bodyString else null,
            gaps = gaps
        )

        bits.add(
            BitmarkBit(
                bitmark = bitmarkOfContext(ctx),
                bodyBitmark = ctx.clozeBody()?.let { bitmarkOfContext(it) },
                instructionBitmark = ctx.instruction()?.let { bitmarkOfContext(it) },
                bit = cloze
            )
        )
    }

    override fun enterMark(ctx: BitmarkParser.MarkContext) {
        val defaultValues = MarkBit()
        val format = ctx.markType().BITMARK_TYPE()?.text?.let { it.drop(1) } ?: defaultValues.format
        val attachment: String? =
            ctx.markBody()?.attachment()?.let { attachmentContext: BitmarkParser.AttachmentContext ->
                treeToString(attachmentContext.string())
            }
        val image: String? = if (ctx.markType().ATTACHMENT()?.text == "&image") {
            attachment
        } else {
            null
        }
        val audio: String? = if (ctx.markType().ATTACHMENT()?.text == "&audio") {
            attachment
        } else {
            null
        }
        val body = StringBuilder()
        val marks = mutableMapOf<String, MarkBit.Mark>()
        var markCount = 1
        ctx.markBody()?.markText()?.forEach { markText: BitmarkParser.MarkTextContext ->
            markText.children.forEach { child ->
                when (child) {
                    is BitmarkParser.StringContext -> body.append(child.text)
                    is BitmarkParser.TextRangeContext -> {
                        val key = "{${markCount++}}"
                        marks[key] = MarkBit.Mark(
                            text = child.string().text,
                            mark = child.marker().markerColor()?.string()?.text
                        )
                    }
                    else -> throw IllegalStateException("Mark text only contains strings and text ranges and not '${child.javaClass}'")
                }
            }
            markText.textRange().forEach { range ->
                range.string()
                range.marker().markerColor().string()
            }
        }
        
        val bodyString = body.toString()
        val instruction = ctx.instruction()?.string()?.let { treeToString(it) } ?: defaultValues.instruction
        val mark = MarkBit(
            format = format,
            image = image,
            audio = audio,
            article = null,
            instruction = instruction,
            hint = null,
            body = if (bodyString.isNotBlank()) bodyString else null,
            marks = marks
        )

        bits.add(
            BitmarkBit(
                bitmark = bitmarkOfContext(ctx),
                bodyBitmark = ctx.markBody()?.let { bitmarkOfContext(it) },
                instructionBitmark = ctx.instruction()?.let { bitmarkOfContext(it) },
                bit = mark
            )
        )
    }

    override fun exitMark(ctx: BitmarkParser.MarkContext?) {
    }

    override fun enterMarkType(ctx: BitmarkParser.MarkTypeContext?) {
    }

    override fun exitMarkType(ctx: BitmarkParser.MarkTypeContext?) {
    }

    override fun enterMarkBody(ctx: BitmarkParser.MarkBodyContext?) {
    }

    override fun exitMarkBody(ctx: BitmarkParser.MarkBodyContext?) {
    }

    override fun enterMarkText(ctx: BitmarkParser.MarkTextContext?) {
    }

    override fun exitMarkText(ctx: BitmarkParser.MarkTextContext?) {
    }

    override fun enterMarker(ctx: BitmarkParser.MarkerContext?) {
    }

    override fun exitMarker(ctx: BitmarkParser.MarkerContext?) {
    }

    override fun enterTextRange(ctx: BitmarkParser.TextRangeContext?) {
    }

    override fun exitTextRange(ctx: BitmarkParser.TextRangeContext?) {
    }

    override fun enterMarkerColor(ctx: BitmarkParser.MarkerColorContext?) {
    }

    override fun exitMarkerColor(ctx: BitmarkParser.MarkerColorContext?) {
    }

    override fun exitCloze(ctx: BitmarkParser.ClozeContext?) {
    }

    override fun exitClozeType(ctx: BitmarkParser.ClozeTypeContext?) {
    }

    override fun exitClozeText(ctx: BitmarkParser.ClozeTextContext?) {
    }

    override fun enterClozeBody(ctx: BitmarkParser.ClozeBodyContext?) {
    }

    override fun exitClozeBody(ctx: BitmarkParser.ClozeBodyContext?) {
    }

    override fun enterGap(ctx: BitmarkParser.GapContext?) {
    }

    override fun enterGapInstruction(ctx: BitmarkParser.GapInstructionContext?) {
    }

    override fun exitGapInstruction(ctx: BitmarkParser.GapInstructionContext?) {
    }

    override fun enterEveryRule(ctx: ParserRuleContext?) {
    }

    override fun exitEveryRule(ctx: ParserRuleContext?) {
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

    /**
     * Extract the underlying Bitmark which was parsed by a certain rule.
     */
    private fun bitmarkOfContext(context: ParserRuleContext): String {
        return context.start.inputStream.getText(
            Interval(
                context.start.startIndex,
                context.stop.stopIndex
            )
        )
    }

    /**
     * Extract the content as string
     */
    private fun treeToString(ctx: BitmarkParser.StringContext): String {
        val sb = StringBuilder()
        ctx.children.forEach {
            sb.append(it.text)
        }
        return sb.toString()
    }
}
