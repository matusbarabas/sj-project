# BNF
```
dtddocument ::= declaration {declaration} .
declaration ::= attrdecl | elemdecl .
elemdecl ::= '<!ELEMENT' name ('EMPTY' | 'ANY' | '(#PCDATA)' | elemchild) '>' .
elemchild ::= '('(choice | seq)['?' | '*' | '+'] ')' .
choice ::= '(' cp ['|' cp] ')' .
seq ::= '(' cp {',' cp} ')' .
cp ::= (name | choice | seq) ['?' | '*' | '+'].
attrdecl ::= '<!ATTLIST' name { '|' name attrtype defaultdecl} '>' .
attrtype::= 'CDATA' | 'NMTOKEN' | 'IDREF' | '(' word ['|' word] ')' .
defaultdecl ::= '#REQUIRED' | '#IMPLIED' | (['#FIXED'] '"' word { '|' word} '"' ) .
name ::= (letter| '_' | ':') {namechar} .
namechar ::= letter| digit| '.' | '-' | '_' | ':' .
letter::= 'A' | .. | 'Z' | 'a' | .. | 'z' .
number::= digit {digit} .
digit::= '0' | .. | '9' .
word ::= char {char} .
char::= letter | digit | $ | % | ~
```

# Grammar
```
DTDOC -> DEC DTDOC' .
DTDOC' -> DTDOC | .
DEC -> ATTRDEC | ELEMDEC .
ELEMDEC -> <!ELEMENT NAME ELEMDEC' .
ELEMDEC' -> EMPTY > | ANY > | (#PCDATA) > | ELEMCHILD > .
ELEMCHILD -> ( ELEMCHILD'' .
ELEMCHILD' -> ? ) | * ) | + ) | ) .
ELEMCHILD'' -> ( ELEMCHILD''' .
ELEMCHILD''' -> CP ELEMCHILD'''' .
ELEMCHILD'''' -> & CP ) ELEMCHILD' | SEQ'' ) ELEMCHILD' | ) ELEMCHILD' .
SEQ'' -> , CP SEQ''' .
SEQ''' -> SEQ'' | .
CP -> NAME CP' | ( CP'' .
CP' -> ? | * | + | .
CP'' -> CP CP''' .
CP''' -> & CP ) CP' | SEQ'' ) CP'  | ) CP'.
ATTRDEC -> <!attlist ATTRDEC' > .
ATTRDEC' -> NAME & ATTRDEC'' .
ATTRDEC'' -> NAME ATTRTYPE DEFAULTDEC ATTRDEC'' | .
ATTRTYPE -> CDATA | NMTOKEN | IDREF | ( WORD ATTRTYPE' .
ATTRTYPE' -> ) | & WORD ) .
DEFAULTDEC -> #REQUIRED | #IMPLIED | #FIXED " DEFAULTDEC' " | " DEFAULTDEC' " .
DEFAULTDEC' -> WORD & DEFAULTDEC'' .
DEFAULTDEC'' -> DEFAULTDEC' | .
NAME -> LET NAME'  | _ NAME' | : NAME' .
NAME' -> NAMECHAR NAME' | .
NAMECHAR -> LET | DIG | . | - | _ | : .
LET -> a | b | c | d | e | f | g | h | i | j | k | l | m | n | o | p | q | r | s | t | u | v | x | y | z  | A | B | C | D | E | F | G | H | I | J | K | L | M | N | O | P | Q | R | S | T | U | V | X | Y | Z .
DIG -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 .
WORD -> CHAR WORD' .
WORD' -> WORD | .
CHAR -> LET | DIG | @ | ~ | % .
```

