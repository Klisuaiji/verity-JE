import zipfile, re

JAR = "C:/Users/Qq203/.gradle/caches/modules-2/files-2.1/net.neoforged/neoforge/21.1.234/4e270b39970b765d1c76505f9c305d43d035f635/neoforge-21.1.234-sources.jar"

# classes to verify: (simpleName, importedPackage)
checks = [
    ("FMLJavaModLoadingContext", "net.neoforged.fml.javafmlmod"),
    ("NeoForge", "net.neoforged.neoforge"),
    ("ConfigScreenHandler", "net.neoforged.neoforge.client"),
    ("ForgeRegistries", "net.neoforged.neoforge.registries"),
    ("IForgeRegistry", "net.neoforged.neoforge.registries"),
    ("RegistryObject", "net.neoforged.neoforge.registries"),
    ("RenderGuiOverlayEvent", "net.neoforged.neoforge.client.event"),
    ("VanillaGuiOverlay", "net.neoforged.neoforge.client.gui.overlay"),
    ("IGuiOverlay", "net.neoforged.neoforge.client.gui.overlay"),
    ("TickEvent", "net.neoforged.neoforge.event"),
    ("AttachCapabilitiesEvent", "net.neoforged.neoforge.event"),
    ("PlayerSleepInBedEvent", "net.neoforged.neoforge.event.entity.player"),
    ("IModInfo", "net.neoforged.forgespi.language"),
    ("RegisterGuiOverlaysEvent", "net.neoforged.neoforge.client.event"),
    ("Capability", "net.neoforged.neoforge.capabilities"),
    ("CapabilityManager", "net.neoforged.neoforge.capabilities"),
    ("CapabilityToken", "net.neoforged.neoforge.capabilities"),
    ("INBTSerializable", "net.neoforged.neoforge.capabilities"),
    ("LazyOptional", "net.neoforged.neoforge.capabilities"),
    ("NetworkEvent", "net.neoforged.neoforge.network"),
    ("NetworkDirection", "net.neoforged.neoforge.network"),
    ("NetworkRegistry", "net.neoforged.neoforge.network"),
    ("SimpleChannel", "net.neoforged.neoforge.network.simple"),
]

z = zipfile.ZipFile(JAR)
# Build index: simple class name -> package (from path)
name_to_pkg = {}
for n in z.namelist():
    if n.endswith(".java"):
        # path like net/neoforged/neoforge/registries/ForgeRegistries.java
        base = n[:-5].replace("/", ".")
        pkg = base.rsplit(".", 1)[0] if "." in base else ""
        simple = base.rsplit(".", 1)[-1]
        name_to_pkg.setdefault(simple, set()).add(pkg)

for simple, imported in checks:
    pkgs = name_to_pkg.get(simple)
    if pkgs:
        status = "OK" if imported in pkgs else "WRONG -> " + ", ".join(sorted(pkgs))
        print(f"{simple:25s} import={imported:45s} {status}")
    else:
        print(f"{simple:25s} import={imported:45s} NOT FOUND in neoforge sources")
