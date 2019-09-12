grammar Bitmark;

@header {
package com.getmorebrain.bitmark;
}

// Tokens
COMMENT              : '||' .*? '||' -> skip;
NEWLINE              : '\n' -> skip;
OPEN_TYPE            : '[.';
CORRECT_OPTION_OPEN  : '[+';
WRONG_OPTION_OPEN    : '[-';
OPEN_INSTRUCTION     : '[!';
OPEN_HINT            : '[?';
OPEN_GAP             : '[_';
CLOSE                : ']';
MULTIPLE_CHOICE_TYPE : '[.multiple-choice]';
CLOZE_TYPE           : '[.cloze]';
STRING                 : ~('[' | ']')+;

// BitBooks
bitBook : bit+;
bit: multipleChoice | cloze;

// Multiple Choice
multipleChoice : MULTIPLE_CHOICE_TYPE multipleChoiceInstruction? (correctOption | wrongOption)+;
multipleChoiceInstruction : OPEN_INSTRUCTION STRING CLOSE;

// Cloze Text
correctOption: CORRECT_OPTION_OPEN STRING CLOSE;
wrongOption: WRONG_OPTION_OPEN STRING CLOSE;
cloze: CLOZE_TYPE clozeInstruction? clozeText;
clozeInstruction : OPEN_INSTRUCTION STRING CLOSE;
clozeText: (STRING* gapChain STRING*)*;
gapChain: gap+ (gapInstruction | gapHint)*;
gap: OPEN_GAP STRING CLOSE;
gapInstruction : OPEN_INSTRUCTION STRING CLOSE;
gapHint: OPEN_HINT STRING CLOSE;

