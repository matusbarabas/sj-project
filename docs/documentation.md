# SimpleDTD

Autori: Matúš Barabás, David Tran Duc

Podiel práce: 50%/50%

## BNF
```
dtddocument ::= declaration {declaration} .
declaration ::= attrdecl | elemdecl .
elemdecl ::= '<!ELEMENT' name ('EMPTY' | 'ANY' | '(#PCDATA)' | elemchild) '>' .
elemchild ::= '('(choice | seq)['?' | '*' | '+'] ')' .
choice ::= '(' cp ['|' cp] ')' .
seq ::= '(' cp {',' cp} ')' .
cp ::= (name | choice | seq) ['?' | '*' | '+'].
attrdecl ::= '<!ATTLIST' name {name attrtype defaultdecl} '>' .
attrtype::= 'CDATA' | 'NMTOKEN' | 'IDREF' | '(' word ['|' word] ')' .
defaultdecl ::= '#REQUIRED' | '#IMPLIED' | (['#FIXED'] '"' word {word} '"' ) .
name ::= (letter| '_' | ':') {namechar} .
namechar ::= letter| digit| '.' | '-' | '_' | ':' .
letter::= 'A' | .. | 'Z' | 'a' | .. | 'z' .
number::= digit {digit} .
digit::= '0' | .. | '9' .
word ::= char {char} .
char::= letter | digit | $ | % | ~
```

Zo zadanej gramatiky nie je možné zostrojiť LL(1) automat. Ak nastane prípad, že budú za sebou nasledovať 2 "name" alebo "word",  tak by sme nevedeli zistiť, či sa jedná len o jedno slovo, alebo dve slová. Potrebujeme mať indikátor toho, kedy slovo začína alebo končí:
```
attrdecl ::= '<!ATTLIST' name {name attrtype defaultdecl} '>' .
defaultdecl ::= '#REQUIRED' | '#IMPLIED' | (['#FIXED'] '"' word {word} '"' ) .
```

Vykonali sme nasledovnú zmenu, pridali sme "|" medzi slová "name" a "word":
```
attrdecl ::= '<!ATTLIST' name {'|' name attrtype defaultdecl} '>' .
defaultdecl ::= '#REQUIRED' | '#IMPLIED' | (['#FIXED'] '"' word { '|' word} '"' ) .
```

## BNF po úpgrave
```
dtddocument ::= declaration {declaration} .
declaration ::= attrdecl | elemdecl .
elemdecl ::= '<!ELEMENT' name ('EMPTY' | 'ANY' | '(#PCDATA)' | elemchild) '>' .
elemchild ::= '('(choice | seq)['?' | '*' | '+'] ')' .
choice ::= '(' cp ['|' cp] ')' .
seq ::= '(' cp {',' cp} ')' .
cp ::= (name | choice | seq) ['?' | '*' | '+'].
attrdecl ::= '<!ATTLIST' name {name attrtype defaultdecl} '>' .
attrtype::= 'CDATA' | 'NMTOKEN' | 'IDREF' | '(' word ['|' word] ')' .
defaultdecl ::= '#REQUIRED' | '#IMPLIED' | (['#FIXED'] '"' word {word} '"' ) .
name ::= (letter| '_' | ':') {namechar} .
namechar ::= letter| digit| '.' | '-' | '_' | ':' .
letter::= 'A' | .. | 'Z' | 'a' | .. | 'z' .
number::= digit {digit} .
digit::= '0' | .. | '9' .
word ::= char {char} .
char::= letter | digit | $ | % | ~
```

## Príklady viet z jazyka
```
<!ELEMENT note ((to,from,heading,body))>
<!ELEMENT to (#PCDATA)>
<!ELEMENT from (#PCDATA)>
<!ELEMENT heading (#PCDATA)>
<!ELEMENT body (#PCDATA)>
```
```
<!ATTLIST sipvs | FiiT IDREF #REQUIRED>
```
```
<!ELEMENT :w.i-n.t-e.r.._..i-s.._..h.e-r.e: (((or* | not*)?))>
```

Ďalšie testovacie vstupy a ich očakávané výstupy sú v kapitole nižšie.

## Prepis pravidiel z BNF do pravidiel bezkontextovej gramatiky
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

## Overenie LL(1)
### First
```
F1(DTDOC) = F1(DEC) = {<!ATTLIST, <!ELEMENT}
F1(DTDOC') = F1(DTDOC) U {ε} = {<!ATTLIST, <!ELEMENT, ε}
F1(DEC) = F1(ATTRDEC) U F1(ELEMDEC) = {<!ATTLIST, <!ELEMENT}
F1(ELEMDEC) = {<!ELEMENT}
F1(ELEMDEC') = {EMPTY, ANY, (#PCDATA)} U F1(ELEMCHILD) = {EMPTY, ANY, (#PCDATA), (}
F1(ELEMCHILD) = {(}
F1(ELEMCHILD') = {?, *, +, )}
F1(ELEMCHILD'') = F1(( ELEMCHILD''') = {(}
F1(ELEMCHILD''') = F1(CP ELEMCHILD'''') = F1(CP) = {_, :, a-z, A-Z, (}
F1(ELEMCHILD'''') = {|, )} U F1(SEQ'') = {|, ), ','}
F1(SEQ'') = {','}
F1(SEQ''') = F1(SEQ'') U {ε} = {',', ε}
F1(CP) = F1(NAME) U {(} = {_, :, a-z, A-Z, (}
F1(CP') = F1(?) U F1(*) U F1(+) U {ε} = {?, *, +, ε}
F1(CP'') = F1(CP) = {_, :, a-z, A-Z, (}
F1(CP''') = {|, )} U F1(SEQ'') = {|, ), ,}
F1(ATTRDEC) = {<!ATTLIST}
F1(ATTRDEC') = F1(NAME) = {_, :, a-z, A-Z}
F1(ATTRDEC'') = F1(NAME) U {ε} = {_, :, a-z, A-Z, ε}
F1(ATTRTYPE) = {CDATA, NMTOKEN, IDREF, (}
F1(ATTRTYPE') = {), |}
F1(DEFAULTDEC) = {#REQUIRED, #IMPLIED, #FIXED, "}
F1(DEFAULTDEC') = F1(WORD) = {a-z, A-Z, 0-9, @, ~, %}
F1(DEFAULTDEC'') = F1(DEFAULTDEC') U {ε} = {a-z, A-Z, 0-9, @, ~, %, ε}
F1(NAME) = F1(LET) U {_, :} = {_, :, a-z, A-Z}
F1(NAME') = F1(NAMECHAR) U {ε} = {., -, _, :, a-z, A-Z, 0-9, ε}
F1(NAMECHAR) = F1(LET) U F1(DIG) U {., -, _, :} = {., -, _, :, a-z, A-Z, 0-9}
F1(LET) = {a-z, A-Z}
F1(DIG) = {0-9}
F1(WORD) = F1(CHAR) = {a-z, A-Z, 0-9, @, ~, %}
F1(WORD') = F1(WORD) U {ε} = {a-z, A-Z, 0-9, @, ~, %, ε}
F1(CHAR) = F1(LET) U F1(DIG) U {@, ~, %} = {a-z, A-Z, 0-9, @, ~, %}
```

### Follow
```
FO1(DTDOC) = FO1(DTDOC') = FO1(DTDOC) = {$}
FO1(DTDOC') = FO1(DTDOC) = {$}
FO1(DEC) = F1(DTDOC') \ {ε} U FO1(DTDOC) = {<!ATTLIST, <!ELEMENT, $}
FO1(ELEMDEC) = FO1(DEC) = {<!ATTLIST, <!ELEMENT, $}
FO1(ELEMDEC') = FO1(ELEMDEC) = FO1(DEC) = {<!ATTLIST, <!ELEMENT, $}
FO1(ELEMCHILD) = {>}
FO1(ELEMCHILD') = FO1(ELEMCHILD'''') = {>}
FO1(ELEMCHILD'') = FO1(ELEMCHILD) = {>}
FO1(ELEMCHILD''') = FO1(ELEMCHILD'') = {>}
FO1(ELEMCHILD'''') = FO1(ELEMCHILD''') = FO1(ELEMCHILD'') = {>}
FO1(SEQ'') = {)} U FO1(SEQ''') = {)}
FO1(SEQ''') = FO1(SEQ'') = {)} U FO1(SEQ''') = {)}
FO1(CP) = F1(ELEMCHILD'''') U {)} U F1(SEQ''')\ {ε} U FO1(SEQ'') U F1(CP''') = {), ',', |}
FO1(CP') = FO1(CP) U FO1(CP''') = {), ',', |}
FO1(CP'') = FO1(CP) = {), ',', |}
FO1(CP''') = FO1(CP'') = {), ',', |}
FO1(ATTRDEC) = FO1(DEC) = {<!ATTLIST, <!ELEMENT, $}
FO1(ATTRDEC') = {>}
FO1(ATTRDEC'') = FO1(ATTRDEC') = {>}
FO1(ATTRTYPE) = F1(DEFAULTDEC) = {#REQUIRED, #IMPLIED, #FIXED, "}
FO1(ATTRTYPE') = FO1(ATTRTYPE) = {#REQUIRED, #IMPLIED, #FIXED, "}
FO1(DEFAULTDEC) = F1(ATTRDEC'')\ {ε} U FO1(ATTRDEC'') = {_, :, a-z, A-Z, >}
FO1(DEFAULTDEC') = {"} U FO1(DEFAULTDEC'') = {"}
FO1(DEFAULTDEC'') = FO1(DEFAULTDEC') = {"}
FO1(NAME) = F1(ELEMDEC') U F1(CP')\ {ε} U FO1(CP) U {|} U F1(ATTRTYPE) = {EMPTY, ANY, (#PCDATA), (, ?, *, +, ), ',', |, CDATA, NMTOKEN, IDREF}
FO1(NAME') = FO1(NAME) = {EMPTY, ANY, (#PCDATA), (, ?, *, +, ), ',', |, CDATA, NMTOKEN, IDREF}
FO1(NAMECHAR) = F1(NAME')\ {ε} U FO1(NAME') = {., -, _, :, a-z, A-Z, 0-9, EMPTY, ANY, (#PCDATA), (, ?, *, +, ), ',', |, CDATA, NMTOKEN, IDREF}
FO1(LET) = F1(NAME')\ {ε} U FO1(NAME) U FO1(NAMECHAR) U FO1(CHAR) = {., -, _, :, a-z, A-Z, 0-9, EMPTY, ANY, (#PCDATA), (, ?, *, +, ), ',', |, CDATA, NMTOKEN, IDREF, @, ~, %}
FO1(DIG) = FO1(NAMECHAR) U FO1(CHAR) = {., -, _, :, a-z, A-Z, 0-9, EMPTY, ANY, (#PCDATA), (, ?, *, +, ), ',', |, CDATA, NMTOKEN, IDREF, @, ~, %}
FO1(WORD) = F1(ATTRTYPE') U {), |} U FO1(WORD') = {), |}
FO1(WORD') = FO1(WORD) = {), |}
FO1(CHAR) = F1(WORD')\ {ε} U FO1(WORD) = {a-z, A-Z, 0-9, @, ~, %, ), |}
```

### LL(1)

#### First-Follow konflikty
```
Nullable: {DTDOC', SEQ''', CP', ATTRDEC'', DEFAULTDEC'', NAME', WORD'}

F1(DTDOC') ∩ FO1(DTDOC') = {<!ATTLIST, <!ELEMENT, ε} ∩ {$} = {}
F1(SEQ''') ∩ FO1(SEQ''') = {',', ε} ∩ {)} = {}
F1(CP') ∩ FO1(CP') = {?, *, +, ε} ∩ {), ',', |} = {}
F1(ATTRDEC'') ∩ FO1(ATTRDEC'') = {_, :, a-z, A-Z, ε} ∩ {>} = {}
F1(DEFAULTDEC'') ∩ FO1(DEFAULTDEC'') = {a-z, A-Z, 0-9, @, ~, %, ε} ∩ {"} = {}
F1(NAME') ∩ FO1(NAME') = {., -, _, :, a-z, A-Z, 0-9, ε} ∩ {EMPTY, ANY, (#PCDATA), (, ?, *, +, ), ',', |, CDATA, NMTOKEN, IDREF} = {}
F1(WORD') ∩ FO1(WORD') = {a-z, A-Z, 0-9, @, ~, %, ε} ∩  {), |} = {}

No First-Follow conflicts exist.
```

## Parsing table

Tabuľka je v externom súbore "parsing_table.html" alebo "parsing_table.csv"


# Program

Implementovali sme zotavenie z chýb aj pre lexikálny aj syntaktický analyzátor. Toto zotavovanie z chýb je možné zapnúť pomocou príznakov. Taktiež je možné zapnúť krokovanie programu.

Lexikálny analyzár sa dokáže zotaviť z jednoduchých preklepov vo vyhradených slovách, ako napríklad "<ELEMENT". Zisťuje sa tu Levenshteinova vzdialenosť, ak je menšia alebo rovná dva, tak považujeme dané slovo za preklep.

Syntaktický analyzátor sa dokáže zotaviť z nesprávneho počtu zátvoriek.

## Použitie
```
basicDTD, usage:
  -h            shows this help
  -p [path]     path to the input file, if unspecified, takes in input.txt
  -l            enables automatic fixing of typos of reserved keyword, based on Levenshtein distance
  -r            enables recovery from incorrect usage of round brackets
  -L            enables step by step execution of lexical analyzer
  -S            enables step by step execution of syntax analyzer
```

## Testovacie vstupy a výstupy

### Examples

Example 1:
```
<!ELEMENT note ((to,from,heading,body))>
<!ELEMENT to (#PCDATA)>
<!ELEMENT from (#PCDATA)>
<!ELEMENT heading (#PCDATA)>
<!ELEMENT body (#PCDATA)>
```
should be
```
green regardless of flags
```

Example 2:
```
<!ELEMENT note ((to,from,heading,body))>
<!EEMENT to (#PCDATA)>
<!ELEEMNT from (#PCDATA)>
<!ELEMENTE heading (#PCDATA)>
<!ELEMENT body (#PCDATA)>
```
should be
```
green with    flag -l
red   without flag -l : Parsing table out of bounds.
```

Example 3:
```
<!ELEMENT note (to,from,heading,body))>
<!ELEMENT note ((to,from,heading,body)>
<!ELEMENT note (((to,from,heading,body))>
```
should be
```
green with    flag -s
red   without flag -s : No match in parsing table.
```

Example 4:
```
<!ELEMENT note (to,from,heading,body)*)>
<!EEMENT to (#PCDATA)>
<!ELEEMNT from (#PCDATA)>
<!ELEMENTE heading (#PCDATA)>
<!ELEMENT body (#PCDATA)>
```
should be
```
green with    flag -ls
red   with    flag -s  : Parsing table out of bounds.
      with    flag -l  : No match in parsing table.
      without flags    : No match in parsing table.
```

Example 5:
```
<!ATTLIST sipvs | FiiT IDREF #REQUIRED>
```
should be
```
green regardless of flags
```

Example 6:
```
<!ELEMENT :w.i-n.t-e.r.._..i-s.._..h.e-r.e: (((or* | not*)?))>
```
should be
```
green regardless of flags
```

Example 7:
```
<!ATTLIST santa:clause- | s.t.r.o.m.c.e.k (ab|cd) #FIXED "@%|~0|">
```
should be
```
green regardless of flags
```

Example 8:
```
<!ELEMENT : ANY>
<!ELEMENT _ ANY>
<!ELEMENT x ANY>
<!ATTLIST : |>
<!ATTLIST _ |>
<!ATTLIST x |>
```
should be
```
green regardless of flags
```

Example 9:
```
<!ELEMENT cHrIsTmAs ((:santa007+ | ((fiit,no:more))*)?)>
```
should be
```
green regardless of flags
```

Example 10:
```
<!ATTLIST _ | : (svk | cz) "sweden|finland|">
```
should be
```
green regardless of flags
```