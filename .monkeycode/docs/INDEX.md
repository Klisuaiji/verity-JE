# Verity JE 文档

Verity JE 是一个 Minecraft NeoForge 1.21.1 恐怖生存模组。引入具备 AI 对话能力的球形生物，Karma 系统决定其友好还是转化为恶魔。本文档面向 mod 开发者和贡献者，涵盖系统架构、接口定义和开发指南。

**快速链接**: [架构](./ARCHITECTURE.md) | [接口](./INTERFACES.md) | [开发者指南](./DEVELOPER_GUIDE.md)

---

## 核心文档

### [架构](./ARCHITECTURE.md)
系统设计、技术栈、组件结构和数据流程。包含实体系统、渲染系统、AI 集成、事件机制和设计决策。

### [接口](./INTERFACES.md)
网络数据包、命令、配置项、事件钩子和实体 API。集成或扩展本模组的参考。

### [开发者指南](./DEVELOPER_GUIDE.md)
环境搭建、项目结构、编码规范、常见任务和排错指南。贡献者必读。

---

## 核心概念

理解这些领域概念有助于导航代码库：

| 概念 | 描述 |
|------|------|
| [Karma 系统](./专有概念/Karma.md) | 玩家善恶值 (0-20)，控制 Verity 行为和恶魔转化 |
| [Verity 实体](./专有概念/VerityEntity.md) | 核心球形 AI 生物，翻滚弹跳、多纹理变体、恶魔转化 |
| [恶魔实体](./专有概念/VerityDemonEntity.md) | Verity 的恶魔形态，5 阶段 AI 行为，GeckoLib 骨骼动画 |
| [AI 对话系统](./专有概念/AiAPI.md) | LLM 对话集成，多路 TTS/STT，动态系统提示词 |

---

## 模块

| 模块 | 描述 | 文档 |
|------|------|------|
| `entity/` | 实体注册与自定义行为 | [README](./模块/entity.md) |
| `event/` | 服务端与客户端事件处理 | [README](./模块/event.md) |
| `client/` | 客户端渲染、音频、输入 | [README](./模块/client.md) |
| `network/` | S2C 数据包与同步 | [README](./模块/network.md) |
| `item/` | 物品注册与自定义渲染 | [README](./模块/item.md) |
| `block/` | 手电筒光源方块 | [README](./模块/block.md) |
| `mixin/` | 光照与标题界面 Mixin | [README](./模块/mixin.md) |
| `triggers/` | 自定义进度触发器 | [README](./模块/triggers.md) |

---

## 入门指南

### 项目新人？

按此路径学习：
1. **[架构](./ARCHITECTURE.md)** — 了解全局设计和子系统
2. **[核心概念](#核心概念)** — 学习领域术语
3. **[开发者指南](./DEVELOPER_GUIDE.md)** — 搭建环境并运行
4. **[接口](./INTERFACES.md)** — 探索公开 API 和事件

### 首次贡献？

1. **[开发者指南](./DEVELOPER_GUIDE.md)** — 搭建和工作流
2. **[常见任务](./DEVELOPER_GUIDE.md#常见任务)** — 分步添加新物品/实体/AI/数据包
3. **[接口](./INTERFACES.md)** — 理解网络协议和事件钩子

---

## 快速参考

### 命令

```bash
./gradlew build         # 编译并打包 JAR
./gradlew runClient     # 启动 Minecraft 客户端
./gradlew runServer     # 启动 Minecraft 服务端
./gradlew genIntellijRuns  # 生成 IDE 运行配置
```

### 重要文件

| 文件 | 目的 |
|------|------|
| `Verity.java` | @Mod 主入口，注册所有组件 |
| `ModEvents.java` | 主服务端事件处理器 (1300+ 行) |
| `AiAPI.java` | AI 对话、TTS、STT 集成核心 (634 行) |
| `VerityConfig.java` | 约 25 个配置项定义 |
| `build.gradle` | 依赖版本和构建配置 |
