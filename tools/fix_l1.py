import os, re
ROOT = 'src/main/java/varmite/verity'
pyfiles = []
for dp, _, fs in os.walk(ROOT):
    for f in fs:
        if f.endswith('.java'):
            pyfiles.append(os.path.join(dp, f))

def ensure_import(src, imp):
    if ('import ' + imp + ';') in src:
        return src
    lines = src.split('\n')
    insert_at = 0
    for i, l in enumerate(lines):
        if l.startswith('package '):
            j = i + 1
            while j < len(lines) and (lines[j].startswith('import ') or lines[j].strip() == ''):
                j += 1
            insert_at = j
            break
    lines.insert(insert_at, 'import ' + imp + ';')
    return '\n'.join(lines)

changed = []
for path in pyfiles:
    src = open(path, encoding='utf-8').read()
    orig = src
    # 1. ForgeRegistries -> NeoForgeRegistries (import + usages)
    src = src.replace('ForgeRegistries', 'NeoForgeRegistries')
    # 2. IModInfo package
    src = src.replace('net.neoforged.forgespi.language.IModInfo', 'net.neoforged.neoforgespi.language.IModInfo')
    # 3. NeoForge class package
    src = src.replace('import net.neoforged.neoforge.NeoForge;', 'import net.neoforged.neoforge.common.NeoForge;')
    src = src.replace('net.neoforged.neoforge.NeoForge.', 'net.neoforged.neoforge.common.NeoForge.')
    # 4. INBTSerializable package
    src = src.replace('net.neoforged.neoforge.capabilities.INBTSerializable', 'net.neoforged.neoforge.common.util.INBTSerializable')
    # 5. NetworkRegistry package
    src = src.replace('import net.neoforged.neoforge.network.NetworkRegistry;', 'import net.neoforged.neoforge.network.registration.NetworkRegistry;')
    src = src.replace('net.neoforged.neoforge.network.NetworkRegistry.', 'net.neoforged.neoforge.network.registration.NetworkRegistry.')
    # 6. RegistryObject<X> -> DeferredHolder<Registry<X>, X>; fix import
    src = re.sub(r'RegistryObject<([^>]+)>', r'DeferredHolder<Registry<\1>, \1>', src)
    src = src.replace('import net.neoforged.neoforge.registries.RegistryObject;', 'import net.neoforged.neoforge.registries.DeferredHolder;')
    if 'DeferredHolder' in src and 'import net.neoforged.neoforge.registries.DeferredHolder;' not in src:
        src = ensure_import(src, 'net.neoforged.neoforge.registries.DeferredHolder')
    if 'Registry<' in src and 'import net.minecraft.core.Registry;' not in src:
        src = ensure_import(src, 'net.minecraft.core.Registry')
    # 7. IForgeRegistry<X> -> Registry<X>; drop import
    src = re.sub(r'IForgeRegistry<([^>]+)>', r'Registry<\1>', src)
    src = src.replace('import net.neoforged.neoforge.registries.IForgeRegistry;', '')
    # 8. TickEvent subclass split
    if 'TickEvent.ClientTickEvent' in src:
        src = src.replace('TickEvent.ClientTickEvent', 'ClientTickEvent')
        src = ensure_import(src, 'net.neoforged.neoforge.client.event.ClientTickEvent')
    if 'TickEvent.ServerTickEvent' in src:
        src = src.replace('TickEvent.ServerTickEvent', 'ServerTickEvent')
        src = ensure_import(src, 'net.neoforged.neoforge.event.tick.ServerTickEvent')
    if 'TickEvent.PlayerTickEvent' in src:
        src = src.replace('TickEvent.PlayerTickEvent', 'PlayerTickEvent')
        src = ensure_import(src, 'net.neoforged.neoforge.event.tick.PlayerTickEvent')
    src = src.replace('import net.neoforged.neoforge.event.TickEvent;', '')
    if src != orig:
        open(path, 'w', encoding='utf-8').write(src)
        changed.append(path)
print(f"Changed {len(changed)} files")
for p in changed:
    print(' ', p.replace('src/main/java/varmite/verity/', ''))
