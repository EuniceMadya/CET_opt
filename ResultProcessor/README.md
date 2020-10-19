# Format of the file

First line would be the total node num for the graph.

Second line would be the smallest anchor number for the graph.

Third line would be the largest anchor number for the graph.

For DFS, BFS M_CET and T_CET, the format would just be:

```
Algo: algo name
time(s), memory saved?
```

For Hybrid,

```
Algo: algo name
Anchor Num, Selection Method,Time(s), Memory saved?
```

One traversal + one concatenate

- DD means  -- DFS + DFS
- DB means  -- DFS + BFS


One traversal + two concatenate:

- DBB means -- DFS + BFS BFS
- DDD means -- DFS + DFS DFS

