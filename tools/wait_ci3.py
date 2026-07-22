import subprocess, json, sys, time, zipfile, os, re
from collections import Counter

import os
PAT = os.environ.get("VERITY_PAT", "")
if not PAT:
    sys.exit("ERROR: set environment variable VERITY_PAT to the GitHub PAT before running.")
REPO = "Klisuaiji/verity-JE"
HEAD = sys.argv[1] if len(sys.argv) > 1 else "3e8f340"
OUT_DIR = "tools/ci_logs"
os.makedirs(OUT_DIR, exist_ok=True)

def curl(url, out=None):
    cmd = ["curl", "--ssl-no-revoke", "-s",
           "-H", f"Authorization: Bearer {PAT}",
           "-H", "Accept: application/vnd.github+json", url]
    if out:
        cmd += ["-o", out]
    return subprocess.run(cmd, capture_output=True, text=True)

# 1. find run for this head sha
url = f"https://api.github.com/repos/{REPO}/actions/runs?head_sha={HEAD}&per_page=5"
r = curl(url)
try:
    runs = json.loads(r.stdout)
except Exception:
    print("Failed to parse runs JSON:", r.stdout[:500]); sys.exit(1)
if not runs.get("workflow_runs"):
    print("NO RUNS FOUND for", HEAD); sys.exit(1)
run = runs["workflow_runs"][0]
run_id = run["id"]
print(f"run_id={run_id} status={run['status']} conclusion={run['conclusion']}", flush=True)

# 2. poll until completed
polls = 0
while run["status"] != "completed" and polls < 60:
    time.sleep(30)
    polls += 1
    r = curl(f"https://api.github.com/repos/{REPO}/actions/runs/{run_id}")
    try:
        run = json.loads(r.stdout)
    except Exception:
        pass
    print(f"[poll {polls}] status={run.get('status')} conclusion={run.get('conclusion')}", flush=True)

print(f"DONE status={run.get('status')} conclusion={run.get('conclusion')}", flush=True)

# 3. download logs
logzip = os.path.join(OUT_DIR, f"run_{run_id}.zip")
rr = curl(f"https://api.github.com/repos/{REPO}/actions/runs/{run_id}/logs", out=logzip)
print(f"logs -> {logzip} size={os.path.getsize(logzip) if os.path.exists(logzip) else 'MISSING'}", flush=True)

# 4. parse error blocks from the zip
error_blocks = []
try:
    with zipfile.ZipFile(logzip) as z:
        for name in z.namelist():
            if not (name.lower().endswith('.txt') or 'build' in name.lower()):
                continue
            data = z.read(name).decode('utf-8', 'replace')
            lines = data.splitlines()
            for i, line in enumerate(lines):
                if re.search(r'\.java:\d+: error:', line):
                    # capture this line + up to 6 following lines of context
                    block = "\n".join(lines[i:i+7]).strip()
                    error_blocks.append(block)
except Exception as e:
    print("parse error:", e, flush=True)

print(f"\nTOTAL ERROR BLOCKS: {len(error_blocks)}", flush=True)

# categorize: extract symbol for cannot_find_symbol, package for package_does_not_exist
cat = Counter()
symbols = Counter()
for b in error_blocks:
    first = b.splitlines()[0]
    m = re.search(r'error:\s*(.+)', first)
    msg = m.group(1) if m else first
    cat[msg.strip()] += 1
    if 'cannot find symbol' in b:
        sm = re.search(r'symbol:\s*(?:variable|method|class|location:)?\s*([A-Za-z0-9_.<>]+)', b)
        if sm:
            symbols[sm.group(1)] += 1
    if 'package' in b and 'does not exist' in b:
        pm = re.search(r"package ([A-Za-z0-9_.]+) does not exist", b)
        if pm:
            symbols["package:" + pm.group(1)] += 1

print("\n=== CATEGORIES (by error message) ===", flush=True)
for k, v in cat.most_common():
    print(f"  {v:4d}  {k}", flush=True)
print("\n=== TOP MISSING SYMBOLS / PACKAGES ===", flush=True)
for k, v in symbols.most_common(40):
    print(f"  {v:4d}  {k}", flush=True)

# save blocks
with open(os.path.join(OUT_DIR, f"errors_{run_id}.txt"), "w", encoding="utf-8") as f:
    for b in error_blocks:
        f.write(b + "\n\n")
print(f"\nsaved {os.path.join(OUT_DIR, f'errors_{run_id}.txt')}", flush=True)
