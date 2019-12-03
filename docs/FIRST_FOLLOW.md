# First
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

# Follow
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