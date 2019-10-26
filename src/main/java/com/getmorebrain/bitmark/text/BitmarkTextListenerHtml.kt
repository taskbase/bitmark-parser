package com.getmorebrain.bitmark.text

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.TerminalNode

class BitmarkTextListenerHtml : MarkupExtractor {

    private var builder: StringBuilder = StringBuilder()

    override fun markup(): String {
        val markup = builder.toString()
        builder = StringBuilder()
        return markup
    }

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

    override fun enterMultiLinePlainText(ctx: BitmarktextParser.MultiLinePlainTextContext) {
        builder.append(treeToString(ctx))
    }

    override fun exitMultiLinePlainText(ctx: BitmarktextParser.MultiLinePlainTextContext) {
    }

    private fun treeToString(ctx: ParserRuleContext): String {
        val sb = StringBuilder()
        ctx.children.forEach {
            sb.append(it.text)
        }
        return sb.toString()
    }

    override fun exitPlainText(ctx: BitmarktextParser.PlainTextContext) {
    }

    override fun enterEmphasized(ctx: BitmarktextParser.EmphasizedContext) {
        builder.append("<i>")
    }

    override fun exitEmphasized(ctx: BitmarktextParser.EmphasizedContext) {
        builder.append("</i>")
    }

    override fun enterImportant(ctx: BitmarktextParser.ImportantContext) {
        builder.append("<b>")
    }

    override fun exitImportant(ctx: BitmarktextParser.ImportantContext) {
        builder.append("</b>")
    }

    override fun enterHighlighted(ctx: BitmarktextParser.HighlightedContext) {
        builder.append("<mark>")
    }

    override fun exitHighlighted(ctx: BitmarktextParser.HighlightedContext) {
        builder.append("</mark>")
    }

    override fun enterDeleted(ctx: BitmarktextParser.DeletedContext) {
        builder.append("<del>")
    }

    override fun exitDeleted(ctx: BitmarktextParser.DeletedContext) {
        builder.append("</del>")
    }

    override fun enterInserted(ctx: BitmarktextParser.InsertedContext) {
        builder.append("<u>")
    }

    override fun exitInserted(ctx: BitmarktextParser.InsertedContext) {
        builder.append("</u>")
    }

    override fun enterRemarked(ctx: BitmarktextParser.RemarkedContext) {
        builder.append("<aside>")
    }

    override fun exitRemarked(ctx: BitmarktextParser.RemarkedContext) {
        builder.append("</aside>")
    }

    override fun enterTitle(ctx: BitmarktextParser.TitleContext) {
        builder.append("<h1>")
    }

    override fun exitTitle(ctx: BitmarktextParser.TitleContext) {
        builder.append("</h1>")
    }

    override fun enterSubtitle(ctx: BitmarktextParser.SubtitleContext) {
        builder.append("<h2>")
    }

    override fun exitSubtitle(ctx: BitmarktextParser.SubtitleContext) {
        builder.append("</h2>")
    }

    override fun enterSecondLevelSubtitle(ctx: BitmarktextParser.SecondLevelSubtitleContext) {
        builder.append("<h3>")
    }

    override fun exitSecondLevelSubtitle(ctx: BitmarktextParser.SecondLevelSubtitleContext) {
        builder.append("/h3")
    }

    override fun enterBulletedList(ctx: BitmarktextParser.BulletedListContext) {
        builder.append("<ul>")
    }

    override fun exitBulletedList(ctx: BitmarktextParser.BulletedListContext) {
        builder.append("</ul>")
    }

    override fun enterBulletedListEntry(ctx: BitmarktextParser.BulletedListEntryContext) {
        builder.append("<li>")
    }

    override fun exitBulletedListEntry(ctx: BitmarktextParser.BulletedListEntryContext) {
        builder.append("</li>")
    }

    override fun enterNumberedList(ctx: BitmarktextParser.NumberedListContext) {
        builder.append("<ol>")
    }

    override fun exitNumberedList(ctx: BitmarktextParser.NumberedListContext) {
        builder.append("</ol>")
    }

    override fun enterNumberedListEntry(ctx: BitmarktextParser.NumberedListEntryContext) {
        builder.append("<li>")
    }

    override fun exitNumberedListEntry(ctx: BitmarktextParser.NumberedListEntryContext) {
        builder.append("</li>")
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
        builder.append("<aside>")
    }

    override fun exitAnnotation(ctx: BitmarktextParser.AnnotationContext) {
        builder.append("</aside>")
    }

    override fun enterFootnote(ctx: BitmarktextParser.FootnoteContext) {
        builder.append("<aside>")
    }

    override fun exitFootnote(ctx: BitmarktextParser.FootnoteContext) {
        builder.append("</aside>")
    }

    override fun enterGlossary(ctx: BitmarktextParser.GlossaryContext) {
    }

    override fun exitGlossary(ctx: BitmarktextParser.GlossaryContext) {
    }

    override fun enterComment(ctx: BitmarktextParser.CommentContext) {
        builder.append("<!-- ")
    }

    override fun exitComment(ctx: BitmarktextParser.CommentContext) {
        builder.append("-->")
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