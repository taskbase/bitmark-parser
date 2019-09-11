grammar Bitmark;

@header {
package com.getmorebrain.bitmark;
}

// Tokens
COMMENT             : '||' .*? '||' -> skip;
NEWLINE             : '\n' -> skip;
OPEN_TYPE           : '[.';
CORRECT_OPTION_OPEN : '[+';
WRONG_OPTION_OPEN   : '[-';
OPEN_INSTRUCTION    : '[!';
OPEN_HINT           : '[?';
OPEN_GAP            : '[_';
CLOSE               : ']';
TEXT                : ( '0'..'9' | 'a'..'z' | 'A'..'Z' | '.' | '-' | '?' | ' ' )+;

// BitBooks
bitBook : bit+;
bit: multipleChoice | cloze;

// Multiple Choice
multipleChoice : multipleChoiceType multipleChoiceInstruction? (correctOption | wrongOption)+;
multipleChoiceInstruction : OPEN_INSTRUCTION TEXT CLOSE;
multipleChoiceType : '[.multiple-choice]';

// Cloze Text
correctOption: CORRECT_OPTION_OPEN TEXT CLOSE;
wrongOption: WRONG_OPTION_OPEN TEXT CLOSE;
cloze: clozeType clozeInstruction? clozeText;
clozeInstruction : OPEN_INSTRUCTION TEXT CLOSE;
clozeType: '[.cloze]';
clozeText: (TEXT* gapChain TEXT*)*;
gapChain: gap+ (gapInstruction | gapHint)*;
gap: OPEN_GAP TEXT CLOSE;
gapInstruction : OPEN_INSTRUCTION TEXT CLOSE;
gapHint: OPEN_HINT TEXT CLOSE;

