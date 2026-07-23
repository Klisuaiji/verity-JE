import glob, re, os

triggers_dir = "src/main/java/varmite/verity/triggers"
files = glob.glob(os.path.join(triggers_dir, "*Trigger.java"))
for f in files:
    if "ModTrigger" in f: continue
    with open(f, encoding='utf-8') as fh:
        c = fh.read()
    # Add back ResourceLocation import
    if "import net.minecraft.resources.ResourceLocation;" not in c:
        c = c.replace("import net.minecraft.server.level.ServerPlayer;",
                       "import net.minecraft.resources.ResourceLocation;\nimport net.minecraft.server.level.ServerPlayer;")
    cn = os.path.splitext(os.path.basename(f))[0]
    simple = cn.replace("Trigger", "").lower()
    cid = f'verity:{simple}'
    # Add ID field and getId() after class declaration
    pattern = r'(public class \w+ extends SimpleCriterionTrigger<\w+\.TriggerInstance>) \{'
    repl = f'\\1 {{\n    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("verity", "{simple}");\n    public ResourceLocation getId() {{ return ID; }}'
    c2 = re.sub(pattern, repl, c)
    if c2 != c:
        with open(f, 'w', encoding='utf-8') as fh:
            fh.write(c2)
        print(f"Fixed: {os.path.basename(f)} -> {cid}")
    else:
        print(f"No change: {os.path.basename(f)}")

# Fix ModTriggers
f = os.path.join(triggers_dir, "ModTriggers.java")
with open(f, encoding='utf-8') as fh:
    c = fh.read()
# Replace: CriteriaTriggers.register(new XxxTrigger()) with CriteriaTriggers.register(XxxTrigger.ID, new XxxTrigger())
c2 = re.sub(r'CriteriaTriggers\.register\(new (\w+)\(\)\)',
            r'CriteriaTriggers.register(\1.ID, new \1())', c)
if c2 != c:
    with open(f, 'w', encoding='utf-8') as fh:
        fh.write(c2)
    print("Fixed ModTriggers")
