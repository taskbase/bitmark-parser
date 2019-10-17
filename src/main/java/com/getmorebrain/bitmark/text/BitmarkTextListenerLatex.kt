package com.getmorebrain.bitmark.text

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.TerminalNode

class BitmarkTextListenerLatex(
    private val builder: StringBuilder = StringBuilder()
) : MarkupExtractor {

    override fun markup(): String = builder.toString()


    override fun enterBitmarkPlusPlus(ctx: BitmarktextParser.BitmarkPlusPlusContext) {
    }

    override fun exitBitmarkPlusPlus(ctx: BitmarktextParser.BitmarkPlusPlusContext) {
    }

    override fun enterBitmarkPlusPlusElement(ctx: BitmarktextParser.BitmarkPlusPlusElementContext) {
    }

    override fun exitBitmarkPlusPlusElement(ctx: BitmarktextParser.BitmarkPlusPlusElementContext) {
    }

    override fun enterPlainText(ctx: BitmarktextParser.PlainTextContext) {
        builder.append(treeToString(ctx))
    }

    private fun treeToString(ctx: BitmarktextParser.PlainTextContext): String {
        val sb = StringBuilder()
        ctx.STRING_CHAR().forEach { char -> sb.append(char.text) }
        return sb.toString()
    }

    override fun exitPlainText(ctx: BitmarktextParser.PlainTextContext) {
    }

    override fun enterEmphasized(ctx: BitmarktextParser.EmphasizedContext) {
        builder.append("\\textit{")
    }

    override fun exitEmphasized(ctx: BitmarktextParser.EmphasizedContext) {
        builder.append("}")
    }

    override fun enterImportant(ctx: BitmarktextParser.ImportantContext) {
        builder.append("\\textbf{")
    }

    override fun exitImportant(ctx: BitmarktextParser.ImportantContext) {
        builder.append("}")
    }

    override fun enterHighlighted(ctx: BitmarktextParser.HighlightedContext) {
        builder.append("{\\color{yellow} ")
    }

    override fun exitHighlighted(ctx: BitmarktextParser.HighlightedContext) {
        builder.append("}")
    }

    override fun enterDeleted(ctx: BitmarktextParser.DeletedContext) {
        builder.append("\\st{")
    }

    override fun exitDeleted(ctx: BitmarktextParser.DeletedContext) {
        builder.append("}")
    }

    override fun enterInserted(ctx: BitmarktextParser.InsertedContext) {
        builder.append("\\underline{")
    }

    override fun exitInserted(ctx: BitmarktextParser.InsertedContext) {
        builder.append("}")
    }

    override fun enterRemarked(ctx: BitmarktextParser.RemarkedContext) {
        builder.append("\\begin{remark}")
    }

    override fun exitRemarked(ctx: BitmarktextParser.RemarkedContext) {
        builder.append("\\end{remark}")
    }

    override fun enterTitle(ctx: BitmarktextParser.TitleContext) {
        builder.append("\\section{")
    }

    override fun exitTitle(ctx: BitmarktextParser.TitleContext) {
        builder.append("}\n")
    }

    override fun enterSubtitle(ctx: BitmarktextParser.SubtitleContext) {
        builder.append("\\subsection{")
    }

    override fun exitSubtitle(ctx: BitmarktextParser.SubtitleContext) {
        builder.append("}\n")
    }

    override fun enterSecondLevelSubtitle(ctx: BitmarktextParser.SecondLevelSubtitleContext) {
        builder.append("\\subsubsection{")
    }

    override fun exitSecondLevelSubtitle(ctx: BitmarktextParser.SecondLevelSubtitleContext) {
        builder.append("}\n")
    }

    override fun enterBulletedList(ctx: BitmarktextParser.BulletedListContext) {
        builder.append("\\begin itemize{itemize>")
    }

    override fun exitBulletedList(ctx: BitmarktextParser.BulletedListContext) {
        builder.append("\\end itemize{itemize>")
    }

    override fun enterBulletedListEntry(ctx: BitmarktextParser.BulletedListEntryContext) {
        builder.append("\\item ")
    }

    override fun exitBulletedListEntry(ctx: BitmarktextParser.BulletedListEntryContext) {
        builder.append("\n")
    }

    override fun enterNumberedList(ctx: BitmarktextParser.NumberedListContext) {
        builder.append("\\begin{enumerate}\n")
    }

    override fun exitNumberedList(ctx: BitmarktextParser.NumberedListContext) {
        builder.append("\\end{enumerate}\n")
    }

    override fun enterNumberedListEntry(ctx: BitmarktextParser.NumberedListEntryContext) {
        builder.append("\\item")
    }

    override fun exitNumberedListEntry(ctx: BitmarktextParser.NumberedListEntryContext) {
        builder.append("\n")
    }

    override fun enterBitmarkMinimalElement(ctx: BitmarktextParser.BitmarkMinimalElementContext) {
    }

    override fun exitBitmarkMinimalElement(ctx: BitmarktextParser.BitmarkMinimalElementContext) {
    }

    override fun enterTextRange(ctx: BitmarktextParser.TextRangeContext) {
    }

    override fun exitTextRange(ctx: BitmarktextParser.TextRangeContext) {
    }

    override fun enterAnnotation(ctx: BitmarktextParser.AnnotationContext) {
        builder.append("\\footnote{")
    }

    override fun exitAnnotation(ctx: BitmarktextParser.AnnotationContext) {
        builder.append("}")
    }

    override fun enterFootnote(ctx: BitmarktextParser.FootnoteContext) {
        builder.append("\\footnote{")
    }

    override fun exitFootnote(ctx: BitmarktextParser.FootnoteContext) {
        builder.append("}")
    }

    override fun enterGlossary(ctx: BitmarktextParser.GlossaryContext) {
    }

    override fun exitGlossary(ctx: BitmarktextParser.GlossaryContext) {
    }

    override fun enterComment(ctx: BitmarktextParser.CommentContext) {
        builder.append("\\begin{comment}")
    }

    override fun exitComment(ctx: BitmarktextParser.CommentContext) {
        builder.append("\\end{comment}")
    }

    override fun enterEveryRule(ctx: ParserRuleContext) {
    }

    override fun exitEveryRule(ctx: ParserRuleContext) {
    }

    override fun visitTerminal(node: TerminalNode) {
    }

    override fun visitErrorNode(node: ErrorNode) {

    }
}