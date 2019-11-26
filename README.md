# BNF
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

# Grammar
```
DTDOC -> DEC DTDOC'
DTDOC' -> DTDOC  | ε
DEC -> ATTRDEC | ELEMDEC
ELEMDEC -> <!element NAME ELEMDEC'
ELEMDEC' -> empty > | any > | (pcdata) > | ELEMCHILD >
ELEMCHILD -> ( ELEMCHILD''
ELEMCHILD' -> ? ) | * ) | + ) | )
ELEMCHILD'' -> ( ELEMCHILD'''
ELEMCHILD''' -> CP ELEMCHILD''''
ELEMCHILD'''' -> & CP ) ELEMCHILD' | SEQ'' ) ELEMCHILD' | ) ELEMCHILD'
SEQ'' -> , CP SEQ'''
SEQ''' -> SEQ'' | ε
CP -> NAME CP' | CH CP' | SEQ CP'
CP' -> ? | * | + | ε
ATTRDEC -> <!attlist ATTRDEC' >
ATTRDEC' -> NAME & ATTRDEC''
ATTRDEC'' -> NAME ATTRTYPE DEFAULTDEC ATTRDEC''  | ε
ATTRTYPE -> cdata | nmtoken | idref | ( WORD ATTRTYPE'
ATTRTYPE' -> ) | & WORD )
DEFAULTDEC -> required | implied | fixed " DEFAULTDEC' "  | " DEFAULTDEC' "
DEFAULTDEC' -> WORD & Y'
Y' -> DEFAULTDEC'  | ε
NAME -> LET NAME'  | _ NAME' | : NAME'
NAME' -> NAMECHAR NAME' | ε
NAMECHAR -> LET | DIG | - | _ | :
LET -> a | b | c | d | e | f | g | h | i | j | k | l | m | n | o | p | q | r | s | t | u | v | x | y | z  | A | B | C | D | E | F | G | H | I | J | K | L | M | N | O | P | Q | R | S | T | U | V | X | Y | Z
DIG -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
WORD -> CHAR WORD'
WORD' -> WORD | ε
CHAR -> LET | $ | ~ | %
```