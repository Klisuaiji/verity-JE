import zipfile, re, sys
from collections import defaultdict

ZIP = 'tools/ci_logs/run_29919852877.zip'
LOG = 'build/5_Build with Gradle.txt'

z = zipfile.ZipFile(ZIP)
log = z.read(LOG).decode('utf-8', 'replace')
lines = [re.sub(r'^\d{4}-\d{2}-\d{2}T[\d:.]+Z\s*', '', ln).rstrip() for ln in log.splitlines()]

# Error line: <file>:<line>: error: <msg>
err_re = re.compile(r'(/home/runner/work/.*?\.java):(\d+):\s*error:\s*(.*)')

blocks = []
for i, ln in enumerate(lines):
    m = err_re.search(ln)
    if m:
        file = m.group(1).split('verity-JE/')[-1]
        lineno = int(m.group(2))
        msg = m.group(3)
        # gather context (non-empty lines up to ~10)
        ctx = []
        j = i + 1
        while j < min(i + 12, len(lines)) and lines[j].strip() != '':
            ctx.append(lines[j])
            j += 1
        blocks.append((file, lineno, msg, ctx))

print("TOTAL error blocks:", len(blocks))

# For each block, find the source line (the one before the '^') and extract identifier
import_report = defaultdict(lambda: defaultdict(set))  # file -> identifier -> set of sourcesnippet
for file, lineno, msg, ctx in blocks:
    src_line = None
    for k in range(len(ctx)):
        if ctx[k].rstrip().endswith('^'):
            if k > 0:
                src_line = ctx[k - 1].strip()
            break
    if src_line is None:
        # take first context line
        src_line = ctx[0].strip() if ctx else ''
    # extract identifier under caret
    ident = None
    for k in range(len(ctx)):
        if ctx[k].rstrip().endswith('^'):
            caret_line = ctx[k].rstrip()
            caret_idx = caret_line.index('^')
            pre = src_line[:caret_idx]
            mm = re.search(r'[A-Za-z_]\w*$', pre)
            if mm:
                ident = mm.group(0)
            break
    if ident is None:
        # fallback: last capitalized word in src_line
        toks = re.findall(r'\b([A-Z]\w+)\b', src_line)
        ident = toks[-1] if toks else src_line[:40]
    import_report[file][ident].add(src_line[:120])

# Print summary
print("\n=== missing identifier -> source snippet, per file ===")
for f in sorted(import_report):
    print(f"\n## {f}")
    for ident in sorted(import_report[f]):
        for snip in sorted(import_report[f][ident]):
            print(f"   {ident:30s} <- {snip}")
