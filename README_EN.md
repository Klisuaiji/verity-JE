# Verity™ (NeoForge 1.21.1)

[中文说明](README.md)

> This isn’t the official Verity™ repository; the official version is only released on CurseForge and is closed-source.An **AI companion mod** for Minecraft 1.21.1 (NeoForge) — bringing a character named **Verity™** into your world.

## Overview

Verity is a NeoForge mod centered on an AI companion powered by the **Groq API** with **real-time voice chat**. Hold a key to talk to her (Push-to-Talk); she can speak back through **native TTS** and play sounds in-game via note blocks / jukeboxes. Beyond conversation, Verity ships a full themed content set: a demonic form **VERITY**, a mysterious **Box**, a **Flashlight**, and **Verity's Disc**.

## Features

- 🤖 **AI conversation** — integrates the Groq API; set your API Key and "Intelligence Level" (model) in the config screen.
- 🎙️ **Voice interaction** — "Push to talk" and "Cycle Selected Mic" support, with native TTS playback.
- 👿 **Demon form** — `VERITY` (verity_demon), a dangerous second form.
- 📦 **The Box** — an interactive entity holding Verity's easter eggs and sounds.
- 🔦 **Flashlight** — a practical lighting item.
- 💿 **Verity's Disc** — a signature music disc.
- ⚙️ **Configuration** — AI settings, "Can Crash", "Require Verity", "Play Video", "Use Native TTS", and more.
- 🌐 **Multiplayer (WIP)** — all clients must configure the same API Key.

## Requirements

| Dependency | Version |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.234 |
| Java | 21 |

## Building

This project uses Gradle (NeoForge ModDev plugin). Internet access is required to download the NeoForge / Minecraft dependencies.

```bash
# Build with the wrapper (downloads Gradle automatically)
./gradlew build

# Output artifact:
build/libs/verity-3.4.1.jar
```

On Windows, use `gradlew.bat`.

## Assets

Mod assets live under `src/main/resources/assets/`:

- `assets/verity/` — the mod's own resources (textures, sounds, models, geo/animations, lang).
- `assets/minecraft/` — **custom overrides** of the vanilla namespace (e.g. `lang/en_us.json`, `texts/splashes.txt`, main-menu panorama, grass/foliage colormaps).

## License

Released under the **MIT License**. See [LICENSE](LICENSE).
