## Prechody

#### Prepis BNF do gramatických pravidiel s alternatívami

```
DTDOC -> DEC | DEC DTDOC
DEC -> ATTRDEC | ELEMDEC
ELEMDEC -> <!ELEMENT NAME EMPTY > |
           <!ELEMENT NAME ANY > | 
           <!ELEMENT NAME (#PCDATA) > |
           <!ELEMENT NAME ELEMCHILD >
ELEMCHILD -> ( CH ) | ( SEQ ) |
             ( CH ? ) | ( SEQ ? ) |
             ( CH * ) | ( SEQ * ) |
             ( CH + ) | ( SEQ + )
CH -> ( CP ) | ( CP '|' CP ) 
SEQ -> ( SEQ' )
SEQ' -> CP | CP , SEQ'
CP -> NAME | CH | SEQ |
      NAME ? | CH ? | SEQ ? |
      NAME * | CH * | SEQ * |
      NAME + | CH + | SEQ +
ATTRDEC -> <!ATTLIST ATTRDEC' >
ATTRDEC' -> NAME ATTRDEC''
ATTRDEC'' -> NAME ATTRTYPE DEFAULTDEC ATTRDEC'' | ε
ATTRTYPE -> CDATA | NMTOKEN | IDREF | ( WORD ) | ( WORD '|' WORD )
DEFAULTDEC -> #REQUIRED | #IMPLIED | #FIXED " DEFAULTDEC' " | " DEFAULTDEC' "
DEFAULTDEC' -> WORD | WORD DEFAULTDEC'
NAME -> LET NAME' | _ NAME' | : NAME'
NAME' -> NAMECHAR NAME' | ε
NAMECHAR -> LET | DIG | . | - | _ | :
LET -> A-Z | a-z
NUMBER -> DIG | DIG NUMBER
DIG -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
WORD -> CHAR | CHAR WORD
CHAR -> LET | DIG | $ | ~ | %   
```
##### Odstránenie ľavej faktorizácie -> DTDOC
```
DTDOC -> DEC DTDOC'
DTDOC' -> DTDOC | ε
DEC -> ATTRDEC | ELEMDEC
```
##### Odstránenie ľavej faktorizácie -> ELEMDEC
```
ELEMDEC -> <!ELEMENT NAME ELEMDEC'
ELEMDEC' -> EMPTY > | ANY > | (#PCDATA) > | ELEMCHILD >
```
##### Odstránenie dvojitej ľavej faktorizácie -> ELEMCHILD
```
ELEMCHILD -> ( ELEMCHILD'' 
ELEMCHILD' -> ? ) | * ) | + ) | )
ELEMCHILD'' -> CH ELEMCHILD' | SEQ ELEMCHILD'
```
##### Odstránenie ľavej faktorizácie -> CH
```
CH -> ( CP CH'
CH' -> '|' CP ) | )
```
##### Odstránenie ľavej faktorizácie -> SEQ'
```
SEQ' -> CP SEQ''
SEQ'' -> , SEQ' | ε
```
##### Odstránenie ľavej faktorizácie -> CP
```
CP -> NAME CP' | CH CP' | SEQ CP'
CP' -> ? | *  | + | ε
```
##### Substitucia CH a SEQ
```
ELEMCHILD'' -> ( CP CH' ELEMCHILD' | ( SEQ' ) ELEMCHILD'
CP -> NAME CP' | ( CP CH' CP' | ( SEQ' ) CP'
```
##### Odstránenie ľavej faktorizácie -> ELEMCHILD''
```
ELEMCHILD'' -> ( ELEMCHILD''' 
ELEMCHILD''' -> CP CH' ELEMCHILD' | SEQ' ) ELEMCHILD' 
```
##### Odstránenie ľavej faktorizácie -> CP
```
CP -> NAME CP' | ( CP''
CP' -> ? | * | + | ε
CP'' -> CP CH' CP' | SEQ' ) CP' 
```
##### Substitúcia SEQ'
```
ELEMCHILD''' -> CP CH' ELEMCHILD' | CP SEQ'' ) ELEMCHILD'
SEQ'' -> , CP SEQ'' | ε
CP'' -> CP CH' CP' | CP SEQ'' ) CP'
```
##### Odstránenie ľavej faktorizácie -> ELEMCHILD'''
```
ELEMCHILD''' -> CP ELEMCHILD''''
ELEMCHILD'''' -> CH' ELEMCHILD' | SEQ'' ) ELEMCHILD'
```
##### Odstránenie ľavej faktorizácie -> CP''
```
CP'' -> CP CP''' 
CP''' -> CH' CP' | SEQ'' ) CP' 
```
##### Odstránenie epsilon -> SEQ''
```
ELEMCHILD'''' -> CH' ELEMCHILD' | SEQ'' ) ELEMCHILD' | ) ELEMCHILD'
SEQ'' -> , CP SEQ'' | , CP 
CP''' -> CH' CP' | SEQ'' ) CP' | ) CP'
```
##### Substitúcia CH' v ELEMCHILD'''' a CP'''
```
ELEMCHILD'''' -> & CP ) ELEMCHILD' | SEQ'' ) ELEMCHILD' | ) ELEMCHILD'
CP''' -> '|' CP ) CP' | SEQ'' ) CP' | ) CP'
```
##### Odstránenie ľavej faktorizácie -> SEQ''
```
SEQ'' -> , CP SEQ'''
SEQ''' -> SEQ'' | ε
```
##### Odstránenie ľavej faktorizácie -> ATTRTYPE
```
ATTRTYPE -> CDATA | NMTOKEN | IDREF | ( WORD ATTRTYPE'
ATTRTYPE' -> ) | '|' WORD ) 
```
##### Odstránenie ľavej faktorizácie -> DEFAULTDEC'
```
DEFAULTDEC' -> WORD DEFAULTDEC''
DEFAULTDEC'' -> DEFAULTDEC' | ε
```
##### Odstránenie ľavej faktorizácie -> WORD
```
WORD -> CHAR WORD' 
WORD' -> WORD | ε
CHAR -> LET | DIG | $ | ~ | % 
```
##### Pridanie | do ATTRDEC' a Y, aby bola gramatika LL1
```
ATTRDEC' -> NAME '|' ATTRDEC''
DEFAULTDEC' -> WORD '|' DEFAULTDEC''
```
##### Vyhodenie nedostupného pravidla NUMBER
##### Výsledná gramatika
```
DTDOC -> DEC DTDOC'
DTDOC' -> DTDOC | ε
DEC -> ATTRDEC | ELEMDEC
ELEMDEC -> <!ELEMENT NAME ELEMDEC'
ELEMDEC' -> EMPTY > | ANY > | (#PCDATA) > | ELEMCHILD > 
ELEMCHILD -> ( ELEMCHILD'' 
ELEMCHILD' -> ? ) | * ) | + ) | ) 
ELEMCHILD'' -> ( ELEMCHILD''' 
ELEMCHILD''' -> CP ELEMCHILD'''' 
ELEMCHILD'''' -> '|' CP ) ELEMCHILD' | SEQ'' ) ELEMCHILD' | ) ELEMCHILD' 
SEQ'' -> , CP SEQ''' 
SEQ''' -> SEQ'' | ε
CP -> NAME CP' | ( CP'' 
CP' -> ? | * | + | ε
CP'' -> CP CP''' 
CP''' -> '|' CP ) CP' | SEQ'' ) CP'  | ) CP'
ATTRDEC -> <!ATTLIST ATTRDEC' > 
ATTRDEC' -> NAME '|' ATTRDEC'' 
ATTRDEC'' -> NAME ATTRTYPE DEFAULTDEC ATTRDEC'' | ε
ATTRTYPE -> CDATA | NMTOKEN | IDREF | ( WORD ATTRTYPE' 
ATTRTYPE' -> ) | '|' WORD ) 
DEFAULTDEC -> #REQUIRED | #IMPLIED | #FIXED " DEFAULTDEC' " | " DEFAULTDEC' " 
DEFAULTDEC' -> WORD '|' DEFAULTDEC'' 
DEFAULTDEC'' -> DEFAULTDEC' | ε
NAME -> LET NAME'  | _ NAME' | : NAME' 
NAME' -> NAMECHAR NAME' | ε
NAMECHAR -> LET | DIG | . | - | _ | : 
LET -> a | b | c | d | e | f | g | h | i | j | k | l | m | n | o | p | q | r | s | t | u | v | w | x | y | z  | A | B | C | D | E | F | G | H | I | J | K | L | M | N | O | P | Q | R | S | T | U | V | W | X | Y | Z
DIG -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 
WORD -> CHAR WORD' 
WORD' -> WORD | ε
CHAR -> LET | DIG | @ | ~ | % 
```
