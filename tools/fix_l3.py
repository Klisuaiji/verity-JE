#!/usr/bin/env python3
# Layer-3 mechanical renames (1.20.1 -> 1.21.1).
import os, re, glob

BASE = "src/main/java"
files = glob.glob(f"{BASE}/**/*.java", recursive=True)

# (regex, replacement) applied to every file
global_rules = [
    # EventBusSubscriber: @Mod.EventBusSubscriber -> @EventBusSubscriber
    (r'@Mod\.EventBusSubscriber', '@EventBusSubscriber'),
    (r'Mod\.EventBusSubscriber\.Bus', 'EventBusSubscriber.Bus'),
    (r'net\.neoforged\.fml\.common\.Mod\$?EventBusSubscriber\$?Bus',
     'net.neoforged.fml.common.EventBusSubscriber'),
    (r'net\.neoforged\.fml\.common\.Mod\$?EventBusSubscriber',
     'net.neoforged.fml.common.EventBusSubscriber'),

    # BlockPathTypes -> PathType
    (r'net\.minecraft\.world\.level\.pathfinder\.BlockPathTypes',
     'net.minecraft.world.level.pathfinder.PathType'),
    (r'\bBlockPathTypes\b', 'PathType'),

    # DeferredHolder<Registry<X>, X> -> DeferredHolder<X, X>
    (r'DeferredHolder<Registry<Block>,\s*Block>', 'DeferredHolder<Block, Block>'),
    (r'DeferredHolder<Registry<Item>,\s*Item>', 'DeferredHolder<Item, Item>'),
    (r'DeferredHolder<Registry<SoundEvent>,\s*SoundEvent>', 'DeferredHolder<SoundEvent, SoundEvent>'),
    (r'DeferredHolder<Registry<EntityType<\?>>,\s*EntityType<\?>>', 'DeferredHolder<EntityType<?>, EntityType<?>>'),

    # IForgeRegistry raw cast removal
    (r'\(IForgeRegistry\)NeoForgeRegistries', 'NeoForgeRegistries'),

    # Entity movement / screen API
    (r'setMaxUpStep', 'setStepHeight'),
    (r'setInitialScreen', 'setScreen'),

    # TickEvent imports + RenderTickEvent rename
    (r'net\.neoforged\.neoforge\.event\.TickEvent\$?ClientTickEvent',
     'net.neoforged.neoforge.client.event.ClientTickEvent'),
    (r'net\.neoforged\.neoforge\.event\.TickEvent\$?PlayerTickEvent',
     'net.neoforged.neoforge.event.tick.PlayerTickEvent'),
    (r'net\.neoforged\.neoforge\.event\.TickEvent\$?ServerTickEvent',
     'net.neoforged.neoforge.event.tick.ServerTickEvent'),
    (r'net\.neoforged\.neoforge\.event\.TickEvent\$?RenderTickEvent',
     'net.neoforged.neoforge.client.event.ClientTickEvent'),
    (r'TickEvent\.RenderTickEvent', 'ClientTickEvent'),
]

# import lines to remove entirely
remove_imports = {
    'import net.neoforged.neoforge.registries.IForgeRegistry;',
}

changed_files = 0
for path in files:
    with open(path, encoding='utf-8') as f:
        lines = f.readlines()
    new_lines = []
    file_changed = False
    for ln in lines:
        s = ln
        for rx, rep in global_rules:
            s = re.sub(rx, rep, s)
        if s.strip() in remove_imports:
            file_changed = True
            continue
        if s != ln:
            file_changed = True
        new_lines.append(s)
    if file_changed:
        with open(path, 'w', encoding='utf-8') as f:
            f.writelines(new_lines)
        changed_files += 1

print(f"Changed {changed_files} files")
