import re
log = open("tools/local_build.log", encoding="utf-8", errors="ignore").read()
lines = log.splitlines()
pat = re.compile(r'([A-Za-z0-9_/\\:\-\. ]*\.java):(\d+):\s*错误:\s*(.*)')
errs = []
for ln in lines:
    m = pat.search(ln)
    if m:
        path = m.group(1).replace('\\', '/')
        fname = path.rsplit('/', 1)[-1]
        errs.append((fname, int(m.group(2)), m.group(3).strip(), path))
print("total raw error lines:", len(errs))
seen = set(); uniq = []
for e in errs:
    k = (e[0], e[2])
    if k not in seen:
        seen.add(k); uniq.append(e)
print("unique error entries:", len(uniq))
from collections import Counter
c = Counter(e[0] for e in uniq)
print("=== errors per file ===")
for f, n in sorted(c.items(), key=lambda x: -x[1]):
    print(f"  {n:3}  {f}")
print("\n=== all unique messages (file:line -> msg) ===")
for e in uniq:
    print(f"{e[0]}:{e[1]}  {e[2]}")
