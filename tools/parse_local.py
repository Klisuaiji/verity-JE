#!/usr/bin/env python3
# Parse local (Chinese-locale) Gradle compileJava log:
#   <file>:<line>: 错误: 找不到符号
#   symbol: 类/接口/变量/方法 NAME
#   location: <file>:<line>
import re, sys
from collections import defaultdict

log = "tools/local_build.log"
text = open(log, encoding="utf-8", errors="replace").read()
lines = text.splitlines()

err_re = re.compile(r'^(?P<path>.+?\.java):(?P<line>\d+):\s*错误:\s*(?P<msg>.*)$')
sym_re = re.compile(r'^\s*symbol:\s*(?P<kind>类|接口|变量|方法)\s+(?P<name>\S+)\s*$')
loc_re = re.compile(r'^\s*location:\s*(?P<path>.+?\.java):(?P<line>\d+)\s*$')

errors = []  # (err_file, err_line, msg, kind, name, loc_file, loc_line)
cur = None
for ln in lines:
    m = err_re.match(ln)
    if m:
        cur = {"ef": m.group("path"), "el": m.group("line"), "msg": m.group("msg"),
               "kind": None, "name": None, "lf": None, "ll": None}
        continue
    if cur is None:
        continue
    s = sym_re.match(ln)
    if s:
        cur["kind"], cur["name"] = s.group("kind"), s.group("name")
        continue
    l = loc_re.match(ln)
    if l:
        cur["lf"], cur["ll"] = l.group("path"), l.group("line")
        errors.append(cur)
        cur = None
        continue

print(f"TOTAL error blocks: {len(errors)}")
per_file = defaultdict(int)
for e in errors:
    per_file[e["ef"]] += 1
print("\n=== errors per file ===")
for f, c in sorted(per_file.items(), key=lambda x: -x[1]):
    print(f"  {c:4d}  {f}")

# symbol -> list of (location file:line)
sym_loc = defaultdict(list)
for e in errors:
    loc = f'{e["lf"]}:{e["ll"]}' if e["lf"] else "?"
    sym_loc[(e["kind"], e["name"])].append(loc)
print("\n=== symbol -> locations ===")
for (kind, name), locs in sorted(sym_loc.items(), key=lambda x: x[0][1]):
    uniq = sorted(set(locs))
    print(f"  [{kind}] {name}  ({len(locs)})")
    for u in uniq[:6]:
        print(f"        {u}")
    if len(uniq) > 6:
        print(f"        ... +{len(uniq)-6} more")
