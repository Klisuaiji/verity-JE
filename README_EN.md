## ⚠️ Important Legal Disclaimer
1. **Unofficial artifact**: This repository is an educational decompilation study artifact and is **in no way** an official fork or remake of the Verity™ mod.
2. **Copyright ownership**: All code logic and resource files (textures, models, audio) have their **intellectual property fully owned by** the original author on CurseForge.
3. **No infringement intended**: This repository is for **Minecraft mod-development technical research** and **personal study** only; it provides no playable成品 or alternative.

## 🚫 Usage Restrictions
- **No commercialization**: You may not charge, crowdfund, or profit from advertising based on this code.
- **Code-audit only**: The code here is for studying obfuscation/encryption techniques or API-call logic only.

## 💻 Stability Statement
- This code is a decompiled artifact and may contain **missing logic, obfuscation residue, or runtime crashes**.
- Do not import it into a production development environment. Any save corruption or client crash resulting from this is at your own risk.

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

## Copyright

All code in this repository (including decompiled artifacts) is copyrighted to the original mod author and is provided for study/research purposes only.
This repository grants **no open-source license of any kind** (All Rights Reserved). See [LICENSE](LICENSE).
