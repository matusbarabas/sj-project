# Gramatika
```
DTDOC -> DEC FDEC
FDEC -> DTDOC | ε
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
ATTRDEC' -> NAME & X
X -> NAME ATTRTYPE DEFAULTDEC X  | ε
ATTRTYPE -> cdata | nmtoken | idref | ( WORD ATTRTYPE'
ATTRTYPE' -> ) | & WORD )
DEFAULTDEC -> required | implied | fixed " Y "  | " Y "
Y -> WORD & Y'
Y' -> Y  | ε
NAME -> LET NAME'  | _ NAME' | : NAME'
NAME' -> NAMECHAR NAME' | ε
NAMECHAR -> LET | DIG | - | _ | :
LET -> a | b | c | d | e | f | g | h | i | j | k | l | m | n | o | p | q | r | s | t | u | v | w | x | y | z | A | B | C | D | E | F | G | H | I | J | K | L | M | N | O | P | Q | R | S | T | U | V | W | X | Y | Z
DIG -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
WORD -> CHAR WORD'
WORD' -> WORD | ε
CHAR -> LET | $ | ~ | %
```