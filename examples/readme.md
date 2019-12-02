# Examples

example1 should be
```
    green regardless of flags
```

example2 should be
```
    green with    flag -l
    red   without flag -l : Parsing table out of bounds.
```

example3 should be
```
    green with    flag -s
    red   without flag -s : No match in parsing table.
```

example4 should be
```
    green with    flag -ls
    red   with    flag -s  : Parsing table out of bounds.
          with    flag -l  : No match in parsing table.
          without flags    : No match in parsing table.
```

example5 should be
```
    green regardless of flags
```