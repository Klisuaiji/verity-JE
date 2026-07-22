#!/usr/bin/env python3
# Remove TickEvent.Phase guards (phase removed in 1.21.1; events fire once per tick).
import os, re, glob

files = glob.glob("src/main/java/**/*.java", recursive=True)
rx = re.compile(r'[ \t]*if \(event\.phase != TickEvent\.Phase\.(?:END|START)\) \{\n[ \t]*return;\n[ \t]*\}\n')
n = 0
for path in files:
    with open(path, encoding='utf-8') as f:
        t = f.read()
    t2, c = rx.subn('', t)
    if c:
        with open(path, 'w', encoding='utf-8') as f:
            f.write(t2)
        n += c
        print(f"{path}: removed {c} phase guard(s)")
print(f"total {n}")
