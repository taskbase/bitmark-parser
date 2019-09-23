grammar Bitmark;

@header {
package com.getmorebrain.bitmark;
}

/*
 * Tokens
 */

COMMENT              : ('||' .*? '||' | '[.' | '[=') -> skip;
OPEN_TYPE            : '[.';
CORRECT_OPTION_OPEN  : '[+';
WRONG_OPTION_OPEN    : '[-';
OPEN_INSTRUCTION     : '[!';
OPEN_HINT            : '[?';
OPEN_GAP             : '[_';
CLOSE                : ']';
MULTIPLE_CHOICE_TYPE : '[.multiple-choice]';
CLOZE_TYPE           : '[.cloze';
ATTACHMENT           : '&image' | '&audio' | '&article';
BITMARK_TYPE         : ':bitmark++' | ':bitmark--';
STRING_CHAR          : .+?; // Matches every character separately into a single token!


/*
 * Parser Rules
 */

// Strings are everything that is not otherwise matched
string  : STRING_CHAR+ ']'? STRING_CHAR* ;

// Constructs
instruction : OPEN_INSTRUCTION string CLOSE;
attachment : ('[&image::'|'[&audio::'|'[&article::') string CLOSE;

// BitBooks
bitBook : bit+;
bit: cloze | multipleChoice;

// Cloze Text
cloze: clozeType instruction? ((clozeText? attachment clozeText?) | clozeText);
clozeType: CLOZE_TYPE ATTACHMENT? BITMARK_TYPE? CLOSE;
clozeText: string | (string* gapChain string*)+;
gapChain: gap+ (gapInstruction | gapHint)*;
gap: OPEN_GAP string CLOSE;
gapInstruction : OPEN_INSTRUCTION string CLOSE;
gapHint: OPEN_HINT string CLOSE;

// Multiple Choice
multipleChoice : MULTIPLE_CHOICE_TYPE instruction? (correctOption | wrongOption)+;
correctOption: CORRECT_OPTION_OPEN string CLOSE;
wrongOption: WRONG_OPTION_OPEN string CLOSE;