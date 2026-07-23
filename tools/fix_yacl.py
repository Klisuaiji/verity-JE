import re

f = "src/main/java/varmite/verity/VerityClient.java"
with open(f, encoding='utf-8') as fh:
    c = fh.read()

orig = c

# 1. Fix ConfigScreenHandler -> IConfigScreenFactory
c = c.replace(
    "ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, previousScreen) -> VerityClient.createYACLScreen((Screen)previousScreen)));",
    "ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (minecraft, previousScreen) -> VerityClient.createYACLScreen((Screen)previousScreen));"
)

# 2. Add imports
c = c.replace("import dev.isxander.yacl3.api.Option;", "import dev.isxander.yacl3.api.Binding;\nimport dev.isxander.yacl3.api.Option;")
c = c.replace("import java.util.Locale;", "import java.awt.Color;\nimport java.util.Locale;")
c = c.replace("import net.neoforged.neoforge.common.NeoForge;", "import net.neoforged.neoforge.client.gui.IConfigScreenFactory;\nimport net.neoforged.neoforge.common.NeoForge;")

def find_matching_paren(text, start):
    depth = 1
    pos = start
    while pos < len(text) and depth > 0:
        ch = text[pos]
        if ch == '(': depth += 1
        elif ch == ')': depth -= 1
        pos += 1
    return pos

def split_top_level_commas(text):
    parts = []
    depth = 0
    last = 0
    for i, ch in enumerate(text):
        if ch in '([{': depth += 1
        elif ch in ')]}': depth -= 1
        elif ch == ',' and depth == 0:
            parts.append(text[last:i].strip())
            last = i + 1
    parts.append(text[last:].strip())
    return parts

def infer_type(val):
    if val in ('true', 'false'): return 'Boolean'
    if val.isdigit() or (val.startswith('-') and val[1:].isdigit()): return 'Integer'
    if val.startswith('"'): return 'String'
    if '.' in val and not val.startswith('"') and not any(c.isalpha() for c in val.replace('.','').replace('-','')): return 'Double'
    for enum_cls in ('AiModel.', 'VerityVoice.', 'KokoroVoice.', 'AiProvider.'):
        if val.startswith(enum_cls): return enum_cls[:-1]
    return 'Object'

# 3. Find all .binding((Object)X, ...) and replace with .binding(Binding.generic(X, ...))
result = []
pos = 0
while True:
    idx = c.find('.binding(', pos)
    if idx == -1: break
    bstart = idx + len('.binding(')
    end = find_matching_paren(c, bstart)
    inner = c[bstart:end-1]
    
    if inner.startswith('(Object)'):
        inner_no_obj = inner[8:].strip()
        parts = split_top_level_commas(inner_no_obj)
        if len(parts) >= 3:
            default = parts[0]
            getter = parts[1]
            setter = ','.join(parts[2:])
            result.append((idx, end, default, getter, setter))
    
    pos = idx + 1

print(f"Found {len(result)} bindings with (Object) cast")

# Apply in reverse
for idx, end, default, getter, setter in reversed(result):
    replacement = f'.binding(Binding.generic({default}, {getter}, {setter}))'
    c = c[:idx] + replacement + c[end:]

# 4. Fix TickBoxControllerBuilder::create -> opt -> TickBoxControllerBuilder.create(opt)
c = c.replace('.controller(TickBoxControllerBuilder::create)', '.controller(opt -> TickBoxControllerBuilder.create(opt))')

# 5. Fix (Option)opt in EnumController
c = c.replace('EnumControllerBuilder.create((Option)opt)', 'EnumControllerBuilder.create(opt)')

# 6. Pair createBuilder with Binding.generic and add type witnesses
cb_positions = [(m.start(), m.end()) for m in re.finditer(r'Option\.createBuilder\(\)', c)]
bg_matches = [(m.start(), m.group(1).strip()) for m in re.finditer(r'Binding\.generic\(([^,(]+)', c)]

print(f"createBuilder count: {len(cb_positions)}, Binding.generic count: {len(bg_matches)}")

cb_list = sorted(cb_positions, key=lambda x: x[0])
bg_list = sorted(bg_matches, key=lambda x: x[0])

replacements2 = []
for cb_start, cb_end in cb_list:
    matched = None
    for i, (bg_start, default_val) in enumerate(bg_list):
        if bg_start > cb_end:
            ty = infer_type(default_val)
            matched = i
            break
    if matched is not None:
        _, default_val = bg_list[matched]
        ty = infer_type(default_val)
        replacements2.append((cb_start, cb_end, ty))
        bg_list.pop(matched)
        print(f"  {ty}: {default_val[:30]}")

# Apply in reverse
for cb_start, cb_end, ty in reversed(replacements2):
    c = c[:cb_start] + f'Option.<{ty}>createBuilder()' + c[cb_end:]

# 7. Fix (Object) remaining in set() calls
c = c.replace('.set((Object)', '.set(')

if c != orig:
    with open(f, 'w', encoding='utf-8') as fh:
        fh.write(c)
    print("All fixes applied successfully")
else:
    print("No changes applied")
