// https://docs.bitbook.education/bitmark_text/

grammar Bitmarktext;

@header {
package com.getmorebrain.bitmark.text;
}

/*
 * Tokens
 */
ESCAPED                 : '\\_'|'\\*'|'\\|'|'\\='|'\\-'|'\\+'|'\\>'|'\\<';
TITLE_START             : '[#';
SUBTITLE_START          : '[##';
SECOND_SUBTITLE_START   : '[###';
TEXT_RANGE_START        : '[\'';
ANNOTATION_START        : '[!';
FOOTNOTE_START          : '[*';
GLOSSARY_START          : '[?';
EMPHASIZE               : '__';
IMPORTANT               : '**';
COMMENT                 : '||';
HIGHLIGHT               : '==';
DELETE                  : '--';
INSERT                  : '++';
REMARK_START            : '>>';
REMARK_END              : '<<';
NEW_LINE                : '\n';
BULLETED_LIST_START     : ({getCharPositionInLine() == 0}? | NEW_LINE) '* ';
NUMBERED_LIST_START     : ({getCharPositionInLine() == 0}? | NEW_LINE) '1. ';
STRING_CHAR             : .+?; // Matches every character separately into a single token!
END_TAG                 : ']';

/*
 * Parser Rules
 */

// Structures
bitmarkPlusPlus : bitmarkPlusPlusElement+;
bitmarkMinimalElement : emphasized | important | multiLinePlainText;
bitmarkPlusPlusElement : bitmarkMinimalElement | title | subtitle | secondLevelSubtitle | deleted | inserted | remarked | highlighted | textRange | annotation | footnote | glossary| bulletedList | numberedList;
multiLinePlainText : (NEW_LINE | STRING_CHAR | ESCAPED)+;
plainText : ( STRING_CHAR | ESCAPED)+;

// Titles
title : TITLE_START bitmarkMinimalElement+ END_TAG;
subtitle : SUBTITLE_START multiLinePlainText END_TAG;
secondLevelSubtitle : SECOND_SUBTITLE_START multiLinePlainText END_TAG;

// Text ranges
textRange : TEXT_RANGE_START bitmarkMinimalElement+ END_TAG;
annotation : textRange ANNOTATION_START bitmarkMinimalElement+ END_TAG;
footnote : textRange FOOTNOTE_START bitmarkMinimalElement+ END_TAG;
glossary : textRange GLOSSARY_START bitmarkMinimalElement+ END_TAG;

// Bitmark Minimal Markup
emphasized : EMPHASIZE multiLinePlainText EMPHASIZE;
important : IMPORTANT multiLinePlainText IMPORTANT;

// Bitmark Plus Markup
comment : COMMENT multiLinePlainText COMMENT;
highlighted : HIGHLIGHT multiLinePlainText HIGHLIGHT penNameDeclaration?;
penNameDeclaration : '(' penName ')' HIGHLIGHT;
penName : STRING_CHAR+;
deleted : DELETE multiLinePlainText DELETE;
inserted : INSERT multiLinePlainText INSERT;
remarked : REMARK_START multiLinePlainText REMARK_END;

// Lists
bulletedList : bulletedListEntry+;
bulletedListEntry : BULLETED_LIST_START (emphasized | important | plainText)+;
numberedList : numberedListEntry+;
numberedListEntry : NUMBERED_LIST_START (emphasized | important | plainText)+;
