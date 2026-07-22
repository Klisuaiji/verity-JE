import re, os, json

MAPDIR = "D:/Users/Qq203/Downloads/verity-3.4.1/tools/mappings"
SRC = "D:/Users/Qq203/Downloads/verity-3.4.1/反编译后/src/main/java"

# ---------------- parse joined.tsrg (tsrg2) with robust class tracking ----------
method_srg = {}   # srg(m_XXXX_) -> (class_obf, obfMember, desc)
field_srg = {}    # srg(f_XXXX_) -> (class_obf, obfMember)

tsrg_path = f"{MAPDIR}/mcp_config_1.20.1/config/joined.tsrg"
with open(tsrg_path, encoding="utf-8") as f:
    cur_class_obf = None
    for raw in f:
        if raw[:1] in (" ", "\t"):
            s = raw.strip()
            if not s:
                continue
            tokens = s.split()
            if len(tokens) >= 4 and tokens[2].startswith("m_"):
                method_srg[tokens[2]] = (cur_class_obf, tokens[0], tokens[1])
            elif len(tokens) == 3 and tokens[1].startswith("f_"):
                field_srg[tokens[1]] = (cur_class_obf, tokens[0])
            elif len(tokens) == 3 and tokens[1].startswith("("):
                method_srg[tokens[2]] = (cur_class_obf, tokens[0], tokens[1])
        else:
            s = raw.strip()
            if s and not s.startswith("tsrg2"):
                cur_class_obf = s.split()[0]

print(f"[tsrg] methods={len(method_srg)} fields={len(field_srg)}")

# ---------------- parse Mojang 1.20.1 mappings (obf -> official) ----------------
mojang_class = {}   # obfClass -> officialClass
mm120 = {}          # obfClass -> { obfMember -> [(param_tuple, name)] }
mf120 = {}          # obfClass -> { obfField -> official }

PRIM = {"int":"int","long":"long","boolean":"boolean","byte":"byte","char":"char",
        "double":"double","float":"float","short":"short","void":"void"}
JVM_PRIM = {"I":"int","J":"long","Z":"boolean","B":"byte","C":"char","D":"double",
            "F":"float","S":"short","V":"void"}

def mojang_params(sig):
    # sig like "int,net.minecraft.world.entity.ai.goal.Goal" or "" (no params)
    sig = sig.strip()
    if sig == "":
        return ()
    out = []
    for p in sig.split(","):
        p = p.strip()
        if p in PRIM:
            out.append(PRIM[p])
        else:
            out.append(p.rsplit(".",1)[-1])  # simple name
    return tuple(out)

def jvm_params(desc, obf2off):
    # desc like "(ILbmv;)V" -> tuple of simple param type names (official)
    i = desc.index("(") + 1
    out = []
    while desc[i] != ")":
        c = desc[i]
        if c == "[":
            i += 1
            c = desc[i]
            out.append("[")  # array marker (approx)
        if c == "L":
            end = desc.index(";", i)
            cls = desc[i+1:end].replace("/",".")
            official = obf2off.get(cls, cls)
            simple = official.rsplit(".",1)[-1]
            out.append(simple)
            i = end + 1
        elif c in JVM_PRIM:
            out.append(JVM_PRIM[c])
            i += 1
        else:
            i += 1
    return tuple(t for t in out if t != "[")

with open(f"{MAPDIR}/1.20.1-mappings.txt", encoding="utf-8") as f:
    cur_official = None
    cur_obf = None
    for line in f:
        s = line.rstrip("\n")
        if not s.strip():
            continue
        if " -> " in s and s.strip().endswith(":") and not s.startswith(" "):
            left = s[:s.index(" -> ")].strip()
            right = s[s.index(" -> ")+4:].strip().rstrip(":")
            cur_official, cur_obf = left, right
            mojang_class[right] = left
            mm120.setdefault(right, {})
            mf120.setdefault(right, {})
        elif s.startswith(" "):
            if " -> " not in s:
                continue
            lpart, rpart = s.rsplit(" -> ", 1)
            rpart = rpart.strip().rstrip(":")
            lpart = re.sub(r"^<\d+>:<\d+>:\s*", "", lpart.strip())
            if "(" in lpart:
                # method: ret name(params)
                name = lpart[:lpart.index("(")].rsplit(" ",1)[-1]
                params = lpart[lpart.index("(")+1:lpart.rindex(")")]
                mm120.setdefault(cur_obf, {}).setdefault(rpart, []).append((mojang_params(params), name))
            else:
                name = lpart.rsplit(" ",1)[-1]
                mf120.setdefault(cur_obf, {})[rpart] = name

print(f"[1.20.1] classes={len(mojang_class)} methods={sum(len(v) for v in mm120.values())} fields={sum(len(v) for v in mf120.values())}")

# ---------------- verify set from 1.21.1 ----------------
mc121, mm121, mf121 = {}, {}, {}
official121 = set()
with open(f"{MAPDIR}/1.21.1-mappings.txt", encoding="utf-8") as f:
    cur_official = None; cur_obf = None
    for line in f:
        s = line.rstrip("\n")
        if not s.strip():
            continue
        if " -> " in s and s.strip().endswith(":") and not s.startswith(" "):
            left = s[:s.index(" -> ")].strip(); right = s[s.index(" -> ")+4:].strip().rstrip(":")
            cur_official, cur_obf = left, right
            mc121[right]=left; mm121.setdefault(right,{}); mf121.setdefault(right,{})
            official121.add(left)
        elif s.startswith(" "):
            if " -> " not in s: continue
            lpart, rpart = s.rsplit(" -> ",1); rpart=rpart.strip().rstrip(":"); lpart=re.sub(r"^<\d+>:<\d+>:\s*","",lpart.strip())
            if "(" in lpart:
                name=lpart[:lpart.index("(")].rsplit(" ",1)[-1]
                official121.add(name)
            else:
                official121.add(lpart.rsplit(" ",1)[-1])
print(f"[1.21.1] official-name set size={len(official121)}")

# ---------------- resolve ----------------
def resolve_method(srg):
    if srg not in method_srg:
        return None
    class_obf, obf, desc = method_srg[srg]
    if class_obf not in mm120 or obf not in mm120[class_obf]:
        return None
    want = jvm_params(desc, mojang_class)
    cands = mm120[class_obf][obf]
    # exact param match
    for params, name in cands:
        if params == want:
            return name
    # fallback: param count match (if unique)
    samecount = [n for p,n in cands if len(p)==len(want)]
    if len(samecount)==1:
        return samecount[0]
    return None

def resolve_field(srg):
    if srg not in field_srg:
        return None
    class_obf, obf = field_srg[srg]
    if class_obf in mf120 and obf in mf120[class_obf]:
        return mf120[class_obf][obf]
    return None

TOKEN_RE = re.compile(r"\b(m_\d{3,}_|f_\d{3,}_)\b")
tokens = set()
for root, _, files in os.walk(SRC):
    for fn in files:
        if not fn.endswith(".java"): continue
        with open(os.path.join(root, fn), encoding="utf-8") as fh:
            txt = fh.read()
        tokens.update(TOKEN_RE.findall(txt))

resolved = {}; unresolved = []
for t in tokens:
    r = resolve_method(t) if t.startswith("m_") else resolve_field(t)
    if r: resolved[t]=r
    else: unresolved.append(t)

# Manual resolutions for tokens whose TSRG class context didn't match Mojang (verified by descriptor + usage)
MANUAL = {
    "m_213846_": "sendSystemMessage",   # Player.sendSystemMessage(Component)  desc (Lsw;)V
    "m_6846_":   "getPlayerList",        # MinecraftServer.getPlayerList()       desc ()Lalk;
    "m_129921_": "getTickCount",         # MinecraftServer.getTickCount()        desc ()I
}
resolved.update(MANUAL)

print(f"[source] unique tokens={len(tokens)} resolved={len(resolved)} unresolved={len(unresolved)}")
print("\n--- spot checks ---")
for t in ["m_25352_","m_130948_","m_60812_","m_130940_","m_6630_","m_123331_"]:
    if t in resolved:
        v = resolved[t] in official121
        print(f"  {t} -> {resolved[t]}{' [1.21.1 OK]' if v else ' [NOT in 1.21.1!]'}")
print("\n--- unresolved ---")
for t in unresolved: print("  ", t)

with open(f"{MAPDIR}/srg_resolved.json","w") as f:
    json.dump(resolved, f)
print(f"\nsaved {MAPDIR}/srg_resolved.json")

# ---------------- APPLY: rewrite source files ----------------
import re as _re
TOKEN_RE = _re.compile(r"\b(m_\d{3,}_|f_\d{3,}_)\b")
total_files = 0
total_subs = 0
for root, _, files in os.walk(SRC):
    for fn in files:
        if not fn.endswith(".java"):
            continue
        p = os.path.join(root, fn)
        with open(p, encoding="utf-8") as fh:
            txt = fh.read()
        def repl(m):
            t = m.group(0)
            return resolved.get(t, t)
        new = _re.sub(TOKEN_RE, repl, txt)
        if new != txt:
            with open(p, "w", encoding="utf-8") as fh:
                fh.write(new)
            total_files += 1
            total_subs += len(TOKEN_RE.findall(txt))
print(f"[apply] updated {total_files} files, {total_subs} substitutions")
