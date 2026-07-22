#!/usr/bin/env python3
# Layer-2 network call-site rewrite: SimpleChannel -> PacketDistributor payload sends.
import re, os

BASE = "src/main/java/varmite/verity"

files = {
    "event/ModEvents.java": [
        "import varmite.verity.network.ModMessages;",
        "import varmite.verity.network.ModNetwork;",
        "import varmite.verity.gui.PlayerKarmaProvider;",
        "import net.neoforged.neoforge.capabilities.ICapabilityProvider;",
        "import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;",
        "import net.neoforged.neoforge.event.AttachCapabilitiesEvent;",
    ],
    "entity/custom/VerityEntity.java": [
        "import varmite.verity.network.ModNetwork;",
    ],
    "item/VerityItem.java": [
        "import varmite.verity.network.ModNetwork;",
    ],
    "event/VeritySpawnScheduler.java": [
        "import varmite.verity.network.ModNetwork;",
    ],
}

rx_track = re.compile(
    r'ModNetwork\.INSTANCE\.send\(PacketDistributor\.TRACKING_ENTITY_AND_SELF\.with\(\(\)\s*->\s*([^,]+?)\s*\),\s*(.+)\)\s*;')
rx_player = re.compile(
    r'ModNetwork\.INSTANCE\.send\(PacketDistributor\.PLAYER\.with\(\(\)\s*->\s*([^,]+?)\s*\),\s*(.+)\)\s*;')
rx_karma = re.compile(
    r'ModMessages\.sendToPlayer\(\(Object\)new KarmaSyncS2CPacket\((.+?)\),\s*\(ServerPlayer\)(.+?)\)\s*;')

def fix_line(line):
    line, n1 = rx_track.subn(r'PacketDistributor.sendToPlayersTrackingEntityAndSelf(\1, \2);', line)
    line, n2 = rx_player.subn(r'PacketDistributor.sendToPlayer(\1, \2);', line)
    line, n3 = rx_karma.subn(r'PacketDistributor.sendToPlayer(\2, new KarmaSyncS2CPacket(\1));', line)
    line = re.sub(r'\(Object\)(new \w+)', r'\1', line)
    return line, n1 + n2 + n3

for rel, drop_imports in files.items():
    path = os.path.join(BASE, rel)
    with open(path, "r", encoding="utf-8") as f:
        lines = f.readlines()
    changed = 0
    new_lines = []
    for ln in lines:
        nl, c = fix_line(ln)
        changed += c
        new_lines.append(nl)
    # drop unused imports
    final = [l for l in new_lines if l.strip() not in drop_imports]
    with open(path, "w", encoding="utf-8") as f:
        f.writelines(final)
    print(f"{rel}: {changed} call-site replacements, imports dropped={len(drop_imports)}")
