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
END_TAG                 : ']';
EMPHASIZE               : '__';
IMPORTANT               : '**';
COMMENT                 : '||';
HIGHLIGHT               : '==';
DELETE                  : '--';
INSERT                  : '++';
REMARK_START            : '>>';
REMARK_END              : '<<';
NEW_LINE                : '\\n';
STRING_CHAR             : .+?; // Matches every character separately into a single token!

/*
 * Parser Rules
 */

// Structures
bitmarkPlusPlus : bitmarkPlusPlusElement+;
bitmarkMinimalElement : emphasized | important | plainText;
bitmarkPlusPlusElement : bitmarkMinimalElement | title | subtitle | secondLevelSubtitle | deleted | inserted | remarked | highlighted | textRange | annotation | footnote | glossary| bulletedList | numberedList;
plainText : (NEW_LINE | STRING_CHAR | ESCAPED)+;

// Titles
title : TITLE_START bitmarkMinimalElement+ END_TAG;
subtitle : SUBTITLE_START plainText END_TAG;
secondLevelSubtitle : SECOND_SUBTITLE_START plainText END_TAG;

// Text ranges
textRange : TEXT_RANGE_START bitmarkMinimalElement+ END_TAG;
annotation : textRange ANNOTATION_START bitmarkMinimalElement+ END_TAG;
footnote : textRange FOOTNOTE_START bitmarkMinimalElement+ END_TAG;
glossary : textRange GLOSSARY_START bitmarkMinimalElement+ END_TAG;

// Bitmark Minimal Markup
emphasized : EMPHASIZE plainText EMPHASIZE;
important : IMPORTANT plainText IMPORTANT;

// Bitmark Plus Markup
comment : COMMENT plainText COMMENT;
highlighted : HIGHLIGHT plainText HIGHLIGHT;
deleted : DELETE plainText DELETE;
inserted : INSERT plainText INSERT;
remarked : REMARK_START plainText REMARK_END;

// Lists
bulletedList : NEW_LINE bulletedListEntry+;
bulletedListEntry : '* ' bitmarkMinimalElement+ NEW_LINE;
numberedList : NEW_LINE numberedListEntry+;
numberedListEntry : '1. ' bitmarkMinimalElement+ NEW_LINE;
