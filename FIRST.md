```
FIRST(DTDOC) = FIRST(DEC DTDOC') = FIRST(DEC) = {<!ATTLIST, <!ELEMENT}
FIRST(DTDOC') = FIRST(DTDOC) U {ε} = {<!ATTLIST, <!ELEMENT, ε}
FIRST(DEC) = FIRST(ATTRDEC) U FIRST(ELEMDEC) = {<!ATTLIST} U {<!ELEMENT} = {<!ATTLIST, <!ELEMENT}
FIRST(ELEMDEC) = FIRST(<!ELEMENT NAME ELEMDEC') = {<!ELEMENT}
FIRST(ELEMDEC') = FIRST(EMPTY >) U FIRST(ANY >) U FIRST((#PCDATA) >) U FIRST(ELEMCHILD) = {EMPTY, ANY, (#PCDATA)} U FIRST(ELEMCHILD) = {EMPTY, ANY, (#PCDATA), (}
FIRST(ELEMCHILD) = FIRST(( ELEMCHILD'') = {(}
FIRST(ELEMCHILD') = FIRST(? )) U FIRST(* )) U FIRST(+ )) U FIRST()) = {?, *, +, )}
FIRST(ELEMCHILD'') = FIRST(( ELEMCHILD''') = {(}
FIRST(ELEMCHILD''') = FIRST(CP ELEMCHILD'''') = FIRST(CP) = {_, :, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, (}
FIRST(ELEMCHILD'''') = FIRST('|' CP ) ELEMCHILD') U FIRST(SEQ'' ) ELEMCHILD' ) U FIRST() ELEMCHILD') = {'|', )} U FIRST(SEQ'') = {'|', ), ,}
FIRST(SEQ'') = FIRST(, CP SEQ''') = FIRST(,) = {,}
FIRST(SEQ''') = FIRST(SEQ'') U {ε} = {, ε}
FIRST(CP) = FIRST(NAME CP') U FIRST(( CP'') = FIRST(NAME) U {(} = {_, :, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, (}
FIRST(CP') = FIRST(?) U FIRST(*) U FIRST(+) U {ε} = {?, *, +, ε}
FIRST(CP'') = FIRST(CP CP''') = FIRST(CP) = {_, :, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, (}
FIRST(CP''') = FIRST('|' CP ) CP') U FIRST(SEQ'' ) CP') U FIRST() CP') = {'|', )} U FIRST(SEQ'') = {'|', ), ,}
FIRST(ATTRDEC) = FIRST(<!ATTLIST ATTRDEC' >) = {<!ATTLIST}
FIRST(ATTRDEC') = FIRST(NAME '|' ATTRDEC'') = FIRST(NAME) = {_, :, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z}
FIRST(ATTRDEC'') = FIRST(NAME ATTRTYPE DEFAULTDEC ATTRDEC'') U {ε} = FIRST(NAME) U {ε} = {_, :, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, ε}
FIRST(ATTRTYPE) = FIRST(CDATA) U FIRST(NMTOKEN) U FIRST(IDREF) U FIRST(( WORD ATTRTYPE') = {CDATA, NMTOKEN, IDREF, (}
FIRST(ATTRTYPE') = FIRST()) U FIRST('|' WORD )) = {), '|'}
FIRST(DEFAULTDEC) = FIRST(#REQUIRED) U FIRST(#IMPLIED) U FIRST(#FIXED "DEFAULTDEC' ") U FIRST(" DEFAULTDEC ' ") = {#REQUIRED, #IMPLIED, #FIXED, "}
FIRST(DEFAULTDEC') = FIRST(WORD '|' DEFAULTDEC'') = FIRST(WORD) = {a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, @, ~, %}
FIRST(DEFAULTDEC'') = FIRST(DEFAULTDEC') U {ε} = {a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, @, ~, %, ε}
FIRST(NAME) = FIRST(LET NAME') U FIRST(_ NAME') U FIRST(: NAME') = FIRST(LET) U {_, :} = {_, :, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z}
FIRST(NAME') = FIRST(NAMECHAR NAME') U {ε} = FIRST(NAMECHAR) U {ε} = {., -, _, :, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, ε}
FIRST(NAMECHAR) =  FIRST(LET) U FIRST(DIG) U FIRST(.) U FIRST(-) U FIRST(_) U FIRST(:) = FIRST(LET) U FIRST(DIG) U {., -, _, :} = {., -, _, :, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
FIRST(LET) = FIRST(a) U FIRST(b) U FIRST(c) U FIRST(d) U FIRST(e) U FIRST(f) U FIRST(g) U FIRST(h) U FIRST(i) U FIRST(j) U FIRST(k) U FIRST(l) U FIRST(m) U FIRST(n) U FIRST(o) U FIRST(p) U FIRST(q) U FIRST(r) U FIRST(s) U FIRST(t) U FIRST(u) U FIRST(v) U FIRST(x) U FIRST(y) U FIRST(z) U FIRST(A) U FIRST(B) U FIRST(C) U FIRST(D) U FIRST(E) U FIRST(F) U FIRST(G) U FIRST(H) U FIRST(I) U FIRST(J) U FIRST(K) U FIRST(L) U FIRST(M) U FIRST(N) U FIRST(O) U FIRST(P) U FIRST(Q) U FIRST(R) U FIRST(S) U FIRST(T) U FIRST(U) U FIRST(V) U FIRST(X) U FIRST(Y) U FIRST(Z) = {a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z}
FIRST(DIG) = FIRST(0) U FIRST(1) U FIRST(2) U FIRST(3) U FIRST(4) U FIRST(5) U FIRST(6) U FIRST(7) U FIRST(8) U FIRST(9) = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
FIRST(WORD) = FIRST(CHAR WORD') = FIRST(CHAR) = {a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, @, ~, %}
FIRST(WORD') = FIRST(WORD) U {ε} = {a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, @, ~, %, ε}
FIRST(CHAR) = FIRST(LET) U FIRST(DIG) U FIRST(@) U FIRST(~) U FIRST(%) = FIRST(LET) U FIRST(DIG) U {@, ~, %} = {a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, X, Y, Z, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, @, ~, %}
```