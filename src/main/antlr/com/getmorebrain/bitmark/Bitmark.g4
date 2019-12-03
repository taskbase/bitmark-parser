grammar Bitmark;

@header {
package com.getmorebrain.bitmark;
}

/*
 * Tokens
 */

COMMENT              : ('||' .*? '||' | '[.' | '[=') -> skip;
NEW_LINE             : '\n';
OPEN_TYPE            : '[.';
OPEN_INSTRUCTION     : NEW_LINE* '[!';
OPEN_HINT            : '[?';
OPEN_GAP             : '[_';
CLOSE                : ']';
CLOZE_TYPE           : '[.cloze';
MARK_TYPE            : '[.mark';
ATTACHMENT           : '&image' | '&audio' | '&article';
BITMARK_TYPE         : ':bitmark++' | ':bitmark--';
STRING_CHAR          : .+?; // Matches every character separately into a single token!


/*
 * Parser Rules
 */

// Strings are everything that is not otherwise matched
string  : (NEW_LINE | STRING_CHAR)+;

// Constructs
instruction : OPEN_INSTRUCTION string CLOSE;
attachment : ('[&image::'|'[&audio::'|'[&article::') string CLOSE;

// BitBooks
bitBook : bit+;
bit: cloze | mark;

// Cloze Text
cloze: clozeType instruction? clozeBody?;
clozeType: CLOZE_TYPE ATTACHMENT? BITMARK_TYPE? CLOSE;
clozeBody: (clozeText? attachment clozeText?) | clozeText;
clozeText: string | (string* gapChain string*)+;
gapChain: gap+ (gapInstruction | gapHint)*;
gap: OPEN_GAP string CLOSE;
gapInstruction : OPEN_INSTRUCTION string CLOSE;
gapHint: OPEN_HINT string CLOSE;

// Mark
mark: markType instruction? markBody?;
markType: MARK_TYPE ATTACHMENT? BITMARK_TYPE? CLOSE;
markBody: (markText? attachment markText?) | markText;
markText: string | (string? textRange string?)+;
textRange: '[\'' string CLOSE marker;
marker: ('[@mark:' string CLOSE) | '[@mark]';
