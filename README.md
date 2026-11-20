> **分支通道说明**：本分支（`beta`）为 **Beta 通道**，包含最新开发与测试版本内容，可能不稳定。正式稳定版本请查看 `release` 分支（正式发行通道）。

##  重要法律声明
1. **非官方产物**：本仓库为教育性质的反编译学习产物，**绝非** Verity™ 模组的官方分支或重制版。
2. **版权归属**：所有代码逻辑、资源文件（纹理、模型、音效）的**知识产权完全归属于** CurseForge 上的原始作者。
3. **无侵权意图**：本仓库仅用于 **Minecraft 模组开发技术研究** 与 **个人学习**。
4. **禁止商业化**：不得基于此代码进行任何收费、众筹或广告盈利行为。
5. **代码审查专用**：此代码仅用于研究混淆/加密技术或 API 调用逻辑，不提供可运行的成品或替代版本。

##  稳定性声明
- 此代码为反编译产物，可能存在**逻辑缺失、混淆残留或运行时崩溃**。
- 不建议将其导入正式开发环境，由此引发的任何存档损坏或客户端崩溃，责任自负。

# Verity™ (NeoForge 1.21.1)

[English README](README_EN.md)

[![CurseForge](https://img.shields.io/badge/CurseForge-Verity_JE-f16436?logo=curseforge)](https://www.curseforge.com/minecraft/mc-mods/verity-je)
[![Modrinth](https://img.shields.io/badge/Modrinth-Verity_JE-1bd96a?logo=modrinth)](https://modrinth.com/mod/verity-je-official)
> 一个为 Minecraft打造的 **AI 伙伴模组**——把名为 **Verity™** 的角色带进你的世界。

## 简介

Verity 是一个基于 NeoForge 的模组，核心是一个由 **Groq API** 驱动、支持**实时语音对话**的 AI 伙伴。你可以按住按键与它说话，它既能用 **原生 TTS** 朗读回应，也能在游戏中通过音符盒 / 唱片机播放声音。除了对话，Verity 还拥有一套完整的内容：恶魔形态 **VERITY**、 **盒子**、**手电筒** 与 **Verity 唱片**。

## 特性

-  **AI 对话**：接入 Groq API，可在配置界面填写 API Key 与“智能等级（模型）”。
-  **语音交互**：支持“按住说话”与“切换麦克风”，使用原生 TTS 进行语音朗读。
-  **盒子**：可交互的实体，内含 Verity 的“小彩蛋”与音效。
-  **手电筒**：实用照明道具。
-  **Verity 唱片**：专属背景音乐唱片。
-  **配置项**：AI 设置、是否要求称呼 “Verity”、是否播放视频、是否使用原生 TTS 等。
-  **联机支持**：多人模式下所有客户端需配置相同的 API Key。

## 环境要求

| 依赖 | 版本 |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.234 |
| Java | 21 |

## 构建

本项目使用 Gradle（NeoForge ModDev 插件）。需要联网以下载 NeoForge / Minecraft 依赖。

```bash
# 使用包装器构建（自动下载 Gradle）
./gradlew build

# 构建产物位于：
build/libs/verity-5.7.3.jar
```

Windows 用户请使用 `gradlew.bat`。

## 资源说明

模组资源位于 `src/main/resources/assets/`：

- `assets/verity/`：模组自有资源（贴图、音效、模型、Geo/动画、语言文件）。
- `assets/minecraft/`：对原版命名空间的**自定义覆盖**（如 `lang/en_us.json`、`texts/splashes.txt`、主菜单全景图、草/树叶颜色映射）。

## 版权声明

本仓库所有代码（含反编译产物）的版权归属于原始模组作者，仅以学习研究目的提供代码阅读参考。
本仓库**不授予任何形式的开源许可**（All Rights Reserved）。详见 [LICENSE](LICENSE)。
