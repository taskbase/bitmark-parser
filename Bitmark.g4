grammar Bitmark;

COMMENT             : '||' .*? '||' -> skip;
NEWLINE             : '\n' -> skip;
OPEN_TYPE           : '[.';
CORRECT_OPTION_OPEN : '[+';
WRONG_OPTION_OPEN   : '[-';
OPEN_DESCRIPTION    : '[!';
OPEN_GAP            : '[_';
CLOSE               : ']';
TEXT                : ( 'a'..'z' | 'A'..'Z' | '.' | '-' | '?' | ' ' )+;

bitBook : bit+;
bit: multipleChoice | cloze;
multipleChoice : multipleChoiceType description? (correctOption | wrongOption)+;
multipleChoiceType : OPEN_TYPE 'multiple-choice' CLOSE;
description : OPEN_DESCRIPTION TEXT CLOSE;
correctOption: CORRECT_OPTION_OPEN TEXT CLOSE;
wrongOption: WRONG_OPTION_OPEN TEXT CLOSE;
cloze: clozeType description? clozeText;
clozeType: OPEN_TYPE 'cloze' CLOSE;
clozeText: (TEXT* gap TEXT*)*;
gap: OPEN_GAP TEXT CLOSE;
