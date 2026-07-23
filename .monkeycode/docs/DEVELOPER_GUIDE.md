# 开发者指南

## 项目目的

Verity JE 是一个 Minecraft NeoForge 1.21.1 恐怖生存模组。它引入具备 AI 对话能力的球形生物 "Verity"，玩家 Karma 系统决定生物友好还是转化为恶魔追猎。

**核心职责**:
- 通过 Groq/OpenRouter API 实现 AI 驱动对话
- 多阶段恶魔 AI 行为（跟踪、破门、碎玻璃、攻击）
- 自定义渲染（程序化球体、GeckoLib 骨骼动画、色相纹理）
- 动态光照手电筒 + 极限黑暗环境
- 实时语音输入 (STT) 和多路语音输出 (TTS)

## 环境搭建

### 前置条件

- JDK 21+
- Gradle 8.x (项目自带 Gradle Wrapper)

### 安装

```bash
# 克隆仓库
git clone https://github.com/Klisuaiji/verity-JE.git
cd verity-JE

# 构建模组
./gradlew build

# 生成的 JAR 位于
# build/libs/verity-5.7.3.jar
```

### IDE 配置

```bash
# 生成 IDE 运行配置
./gradlew genIntellijRuns    # IntelliJ IDEA
./gradlew genEclipseRuns     # Eclipse
./gradlew genVSCodeRuns      # VS Code
```

### 运行

```bash
# 启动 Minecraft 客户端
./gradlew runClient

# 启动 Minecraft 服务端
./gradlew runServer
```

## 项目结构说明

| 目录 | 职责 |
|------|------|
| `Verity.java` | @Mod 主入口，构造函数中注册所有组件 |
| `VerityConfig.java` | 约 25 个 ModConfigSpec 配置项定义 |
| `VerityClient.java` | YACL 图形化配置界面工厂方法 |
| `block/` | 方块注册 + 手电筒不可见光源方块 |
| `item/` | 物品注册、变体纹理、无着色烘焙模型 |
| `entity/` | 3 种实体注册 + 自定义行为 |
| `entity/AI/` | AI 集成 (AiAPI)、5 种恶魔 AI Goal、Sherpa 桥接 |
| `entity/client/` | 球体/恶魔/盒子渲染器 + 程序化网格 |
| `event/` | 服务端事件 (ModEvents 1300行) + 客户端事件 |
| `gui/` | Karma HUD 叠加层 + PlayerKarma Attachment |
| `client/` | 开场视频、键位处理、录音 HUD、动态光照 |
| `network/` | 2 个 S2C 自定义数据包 |
| `sounds/` | 14 种自定义音效注册 |
| `triggers/` | 8 种进度准则触发器 |
| `mixin/` | 光照 Mixin + 标题界面 Mixin |

## 开发工作流

### 代码质量工具

| 工具 | 命令 | 目的 |
|------|------|------|
| Gradle | `./gradlew build` | 编译 + 打包 JAR |
| Gradle | `./gradlew runClient` | 启动 Minecraft 客户端调试 |

### 提交前检查

1. 代码能通过 `./gradlew build` 编译
2. 客户端类不包含服务端专属引用
3. 所有 `@OnlyIn(Dist.CLIENT)` 注解位置正确

### 分支策略

- `main` - 主开发分支，直接推送
- `origin/old` - 旧版备份分支（仅用于资源恢复，不直接修改）

## 编码规范

### 命名

| 类型 | 约定 | 示例 |
|------|------|------|
| 包 | 全小写，层级化 | `varmite.verity.entity.client` |
| 类 | PascalCase | `VerityEntity`, `ModBusClientSetup` |
| 字段 | camelCase | `demonState`, `huntPhase` |
| 静态常量 | SCREAMING_SNAKE | `TOTAL_FRAMES`, `HUD_KARMA` |
| 方法 | camelCase | `transformIntoDemon()`, `getTextureRL()` |

### 文件组织

- 每个文件一个类
- 内部类定义在所属类的底部
- Entity AI Goal 放在 `entity/AI/` 子包
- 客户端渲染类放在 `entity/client/` 子包

### 注册模式

所有注册均使用 NeoForge `DeferredRegister`:

```java
public static final DeferredRegister<Item> ITEMS =
    DeferredRegister.create(Registries.ITEM, "verity");

public static final DeferredHolder<Item, Item> VERITY_ITEM =
    ITEMS.register("verity_item", () -> new VerityItem(...));

public static void register(IEventBus bus) {
    ITEMS.register(bus);
}
```

### 事件处理器

- MOD 总线事件通过 `@EventBusSubscriber` 注解或直接在 Mod 构造函数中注册
- GAME 总线事件使用 `@EventBusSubscriber` 注解
- 客户端事件需要 `value = Dist.CLIENT`

### Mixin

- 所有 Mixin 类在 `mixin/` 包下
- 使用 `@Mixin` 注解指定目标类
- 在 `mixins.verity.json` 中注册

## 常见任务

### 添加新配置项

**需修改的文件**:
1. `VerityConfig.java` — 添加 `ConfigValue<?>` 静态字段和对应的 `defineInXxx` 调用
2. `VerityClient.java` — 在对应的 YACL category 中添加 `OptionDescription.Builder` 条目
3. 如需默认值国际化，修改 `assets/minecraft/lang/en_us.json`

### 添加新物品

**需修改的文件**:
1. `ModItems.java` — 添加 `DeferredHolder<Item, Item>` 并 `register("name", ...)`
2. `ModCreativeModeTabs.java` — 添加到创造模式标签页
3. `assets/verity/models/item/xxx.json` — 物品模型 JSON

### 添加新实体

**需修改的文件**:
1. `ModEntities.java` — 注册 `EntityType`
2. `entity/custom/NewEntity.java` — 实体类实现
3. `entity/client/NewModel.java` — GeckoLib 模型 (GeoModel)
4. `entity/client/NewRenderer.java` — GeckoLib 渲染器 (GeoEntityRenderer)
5. `event/ModBusCommonSetup.java` — `registerAttributes` 中注册实体属性
6. `event/ModBusClientSetup.java` — `registerRenderers` 中绑定渲染器
7. `assets/verity/animations/entity/new.animation.json` — 动画定义
8. `assets/verity/textures/entity/` — 纹理贴图

### 添加新 AI Goal

**需修改的文件**:
1. `entity/AI/NewGoal.java` — 实现 `net.minecraft.world.entity.ai.goal.Goal`
2. `entity/custom/TargetEntity.java` — 在 `registerGoals()` 方法中 `goalSelector.addGoal(priority, new NewGoal(...))`

**Goal 生命周期**:
- `canUse()` — 返回 true 时开始执行
- `canContinueToUse()` — 返回 false 时停止
- `start()` — 开始时调用一次
- `tick()` — 每 tick 调用
- `stop()` — 停止时调用一次

### 添加新网络数据包

**需修改的文件**:
1. `network/NewPayload.java` — 实现 `CustomPacketPayload` record
2. `network/ModNetwork.java` — 在 `register()` 中注册类型和处理器
3. 客户端处理器放在 `network/` 或直接在 payload 的 `handleData` 中

### 修复渲染问题排查

1. 确认纹理文件存在于 `assets/verity/textures/` 对应路径
2. 检查 Renderer 的 `getTextureLocation()` 返回的 `ResourceLocation` 与文件路径一致
3. 检查 `ModBusClientSetup` 中是否正确注册了渲染器
4. 对于物品渲染，检查 `VerityItemRenderer` 是否正确绑定到 `ModItems.VERITY_ITEM`
5. 对于实体渲染，确认 GeckoLib 模型/动画 .json 文件存在且格式正确
