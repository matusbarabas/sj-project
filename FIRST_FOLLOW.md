```
F1(DTDOC) = F1(DEC DTDOC') = F1(DEC) = {<!ATTLIST, <!ELEMENT}
F1(DTDOC') = F1(DTDOC) U {ε} = {<!ATTLIST, <!ELEMENT, ε}
F1(DEC) = F1(ATTRDEC) U F1(ELEMDEC) = {<!ATTLIST} U {<!ELEMENT} = {<!ATTLIST, <!ELEMENT}
F1(ELEMDEC) = F1(<!ELEMENT NAME ELEMDEC') = {<!ELEMENT}
F1(ELEMDEC') = F1(EMPTY >) U F1(ANY >) U F1((#PCDATA) >) U F1(ELEMCHILD) = {EMPTY, ANY, (#PCDATA)} U F1(ELEMCHILD) = {EMPTY, ANY, (#PCDATA), (}
F1(ELEMCHILD) = F1(( ELEMCHILD'') = {(}
F1(ELEMCHILD') = F1(? )) U F1(* )) U F1(+ )) U F1()) = {?, *, +, )}
F1(ELEMCHILD'') = F1(( ELEMCHILD''') = {(}
F1(ELEMCHILD''') = F1(CP ELEMCHILD'''') = F1(CP) = {_, :, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, (}
F1(ELEMCHILD'''') = F1('|' CP ) ELEMCHILD') U F1(SEQ'' ) ELEMCHILD' ) U F1() ELEMCHILD') = {'|', )} U F1(SEQ'') = {'|', ), ,}
F1(SEQ'') = F1(, CP SEQ''') = F1(,) = {,}
F1(SEQ''') = F1(SEQ'') U {ε} = {, ε}
F1(CP) = F1(NAME CP') U F1(( CP'') = F1(NAME) U {(} = {_, :, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, (}
F1(CP') = F1(?) U F1(*) U F1(+) U {ε} = {?, *, +, ε}
F1(CP'') = F1(CP CP''') = F1(CP) = {_, :, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, (}
F1(CP''') = F1('|' CP ) CP') U F1(SEQ'' ) CP') U F1() CP') = {'|', )} U F1(SEQ'') = {'|', ), ,}
F1(ATTRDEC) = F1(<!ATTLIST ATTRDEC' >) = {<!ATTLIST}
F1(ATTRDEC') = F1(NAME '|' ATTRDEC'') = F1(NAME) = {_, :, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z}
F1(ATTRDEC'') = F1(NAME ATTRTYPE DEFAULTDEC ATTRDEC'') U {ε} = F1(NAME) U {ε} = {_, :, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, ε}
F1(ATTRTYPE) = F1(CDATA) U F1(NMTOKEN) U F1(IDREF) U F1(( WORD ATTRTYPE') = {CDATA, NMTOKEN, IDREF, (}
F1(ATTRTYPE') = F1()) U F1('|' WORD )) = {), '|'}
F1(DEFAULTDEC) = F1(#REQUIRED) U F1(#IMPLIED) U F1(#FIXED "DEFAULTDEC' ") U F1(" DEFAULTDEC ' ") = {#REQUIRED, #IMPLIED, #FIXED, "}
F1(DEFAULTDEC') = F1(WORD '|' DEFAULTDEC'') = F1(WORD) = {a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, @, ~, %}
F1(DEFAULTDEC'') = F1(DEFAULTDEC') U {ε} = {a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, @, ~, %, ε}
F1(NAME) = F1(LET NAME') U F1(_ NAME') U F1(: NAME') = F1(LET) U {_, :} = {_, :, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z}
F1(NAME') = F1(NAMECHAR NAME') U {ε} = F1(NAMECHAR) U {ε} = {., -, _, :, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, ε}
F1(NAMECHAR) =  F1(LET) U F1(DIG) U F1(.) U F1(-) U F1(_) U F1(:) = F1(LET) U F1(DIG) U {., -, _, :} = {., -, _, :, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
F1(LET) = F1(a) U F1(b) U F1(c) U F1(d) U F1(e) U F1(f) U F1(g) U F1(h) U F1(i) U F1(j) U F1(k) U F1(l) U F1(m) U F1(n) U F1(o) U F1(p) U F1(q) U F1(r) U F1(s) U F1(t) U F1(u) U F1(v) U F1(x) U F1(y) U F1(z) U F1(A) U F1(B) U F1(C) U F1(D) U F1(E) U F1(F) U F1(G) U F1(H) U F1(I) U F1(J) U F1(K) U F1(L) U F1(M) U F1(N) U F1(O) U F1(P) U F1(Q) U F1(R) U F1(S) U F1(T) U F1(U) U F1(V) U F1(X) U F1(Y) U F1(Z) = {a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z}
F1(DIG) = F1(0) U F1(1) U F1(2) U F1(3) U F1(4) U F1(5) U F1(6) U F1(7) U F1(8) U F1(9) = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
F1(WORD) = F1(CHAR WORD') = F1(CHAR) = {a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, @, ~, %}
F1(WORD') = F1(WORD) U {ε} = {a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, @, ~, %, ε}
F1(CHAR) = F1(LET) U F1(DIG) U F1(@) U F1(~) U F1(%) = F1(LET) U F1(DIG) U {@, ~, %} = {a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, @, ~, %}
```
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