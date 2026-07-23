# 接口文档

Verity JE 作为 Minecraft NeoForge 模组，其"接口"体现为网络数据包、事件钩子、命令系统和配置项。以下逐一说明。

## 网络数据包

模组使用 NeoForge 1.21.1 的 `CustomPacketPayload` 体系，`ModNetwork` 类在 `RegisterPayloadHandlersEvent` 中注册。

### PlayTtsPayload (S2C)

服务端向客户端下发 TTS 语音播放指令。

| 字段 | 类型 | 说明 |
|------|------|------|
| `entityId` | `int` | 说话实体的网络 ID |
| `text` | `String` | 要播放的文本内容 |

**客户端处理** (`PlayTtsClientHandler`): 在客户端世界中根据 `entityId` 查找 VerityEntity，调用 `AiAPI.playTTS()` 播放语音。

### KarmaSyncS2CPacket (S2C)

服务端向客户端同步玩家 Karma 值。

| 字段 | 类型 | 说明 |
|------|------|------|
| `karma` | `int` | 当前 Karma 值 (0-20) |

**客户端处理**: 更新 `ClientKarmaData` 静态缓存，供 KarmaHudOverlay 读取显示。

## 命令

### /changekarma

修改指定玩家的 Karma 值。

```
/changekarma <0-20>
```

- **权限等级**: 2 (需要 OP)
- **实现**: `ChangeKarmaCommand.java`，调用 `ModEvents.setAndSyncKarma()`

### /recoververity

在玩家旁边重新生成 Verity 实体。有 1 小时冷却时间。

```
/recoververity
```

- **权限等级**: 0 (所有玩家可用)
- **实现**: `RecoverVerityCommand.java`，在玩家位置生成 VerityEntity，设置冷却时间到 NBT

## 配置项

所有配置项定义在 `VerityConfig.java` 中，使用 `ModConfigSpec`。分为三大类：

### General

| 配置键 | 类型 | 默认值 | 说明 |
|--------|------|--------|------|
| `SHOW_VERITYS_KARMA` | `Boolean` | `true` | 是否显示 Karma HUD |
| `IMMERSIVE_MODE` | `Boolean` | `false` | 沉浸模式（隐藏 HUD） |
| `EXTREME_DARKNESS` | `Boolean` | `false` | 极度黑暗模式 |
| `VERITY_SPAWN_CHANCE` | `Integer` | `100` | Verity 在箱子中生成概率 (0-100) |

### AI & Voice

| 配置键 | 类型 | 默认值 | 说明 |
|--------|------|--------|------|
| `AI_PROVIDER` | `Enum<AiProvider>` | `GROQ` | AI 服务提供商 |
| `AI_MODEL` | `Enum<AiModel>` | `FAST_LITE` | AI 模型智能级别 |
| `API_KEY` | `String` | `""` | API 密钥 |
| `USE_CLOUD_TTS` | `Boolean` | `true` | 是否使用云端 TTS |
| `CLOUD_TTS_VOICE` | `Enum<VerityVoice>` | `AUTUMN` | 云端 TTS 语音 |
| `LOCAL_TTS` | `Boolean` | `false` | 使用本地 Piper TTS |
| `KOKORO_VOICE` | `Enum<KokoroVoice>` | `af_heart` | 本地 Kokoro TTS 语音 |
| `USE_SYSTEM_TTS` | `Boolean` | `false` | 使用操作系统原生 TTS |
| `PUSH_TO_TALK` | `Boolean` | `true` | 启用语音输入 |
| `LOCAL_STT` | `Boolean` | `false` | 使用本地 Sherpa STT |
| `WHISPER_CPP` | `Boolean` | `false` | 使用本地 Whisper STT |

### Customization

| 配置键 | 类型 | 默认值 | 说明 |
|--------|------|--------|------|
| `HUE_ROTATION` | `Integer` | `0` | 色相旋转角度 (0-360) |
| `VERITY_VARIANT` | `String` | `"happy"` | Verity 变体纹理 (happy/crazy/evil/serious 等) |
| `DEMON_CHASE_VOLUME` | `Double` | `1.0` | 恶魔追击音效音量 |
| `FLASHLIGHT_RANGE` | `Integer` | `8` | 手电筒光照范围 |

## 事件钩子

模组订阅的 NeoForge 事件总线：

### MOD 总线 (在 Verity.java 构造函数中注册)

| 事件 | 处理类 | 说明 |
|------|--------|------|
| `RegisterCapabilitiesEvent` | `ModEvents` | 注册 PlayerKarma AttachmentType |
| `EntityAttributeCreationEvent` | `ModBusCommonSetup` | 注册 3 种实体属性 |
| `RegisterClientExtensionsEvent` | `ModBusClientSetup` | 注册 VerityItemRenderer |
| `RegisterGuiLayersEvent` | `ModBusClientSetup` | 注册 KarmaHudOverlay + AudioHudRenderer |
| `ModelEvent.ModifyBakingResult` | `ModBusClientSetup` | 包装 Verity 物品模型为 UnshadedBakedModel |
| `RegisterKeyMappingsEvent` | `KeybindRegistry` | 注册 V/M 键位 |
| `RegisterPayloadHandlersEvent` | `ModNetwork` | 注册 2 个 S2C 数据包 |

### GAME 总线

| 事件 | 处理类 | 说明 |
|------|--------|------|
| `ServerChatEvent` | `ModEvents` | 玩家消息触发 AI 对话 |
| `PlayerInteractEvent.EntityInteract` | `ModEvents` | 右键 Verity 实体 |
| `PlayerInteractEvent.RightClickItem` | `ModEvents` | 右键闪灯开关 |
| `PlayerInteractEvent.RightClickBlock` | `ModEvents` | 右键方块闪灯 |
| `ServerTickEvent.Pre` | `ModEvents` | 主 tick：恶魔转换检测、Day2 清怪 |
| `ServerTickEvent.Pre` | `FlashlightServerLogic` | 手电筒光线追踪放置方块 |
| `ServerTickEvent.Pre` | `VeritySpawnScheduler` | 延迟生成调度 |
| `ServerTickEvent.Pre` | `DemonWindowSpawner` | 恶魔窗口出现 |
| `LivingDeathEvent` | `ModEvents` | 生物死亡 Karma 扣减 |
| `PlayerSleepInBedEvent` | `ModEvents` | 阻止睡觉 |
| `RegisterCommandsEvent` | `ModEvents` | 注册命令 |
| `ClientTickEvent.Pre` | `ModClientEvents` | 更新动态光照 + 标题界面处理 |
| `ClientTickEvent.Pre` | `KeybindHandler` | V/M 键位处理 |
| `ScreenEvent.Opening` | `ModClientEvents` | 开场视频播放 |
| `RenderFrameEvent.Pre` | `ModClientEvents` | 强制 gamma=0.0 |
| `ViewportEvent.ComputeFogColor` | `VerityVisuals` | 自定义雾色 |
| `ViewportEvent.RenderFog` | `VerityVisuals` | 自定义雾距 |

## 实体 API

### VerityEntity

| 方法 | 说明 |
|------|------|
| `setVariant(String)` / `getVariant()` | 设置/获取纹理变体 |
| `startTalking(int)` / `stopTalking()` | 开始/停止说话状态 |
| `isPlayerLookingAtMe(Player)` | 检测玩家是否在直视此实体 |
| `transformIntoDemon(Player)` | 转化为恶魔形态 |
| `getTextureRL()` | 获取当前纹理 ResourceLocation |
| `triggerBoxDrop()` | 从盒子掉落动画触发 |

### VerityDemonEntity

| 方法 | 说明 |
|------|------|
| `setDemonState(int)` / `getDemonState()` | 设置/获取恶魔 AI 阶段 (0-4) |
| `setHuntPhase(String)` / `getHuntPhase()` | 设置/获取狩猎阶段 |
| `isClimbing()` / `setClimbing(boolean)` | 攀爬状态 |
| `isCrawling()` / `setCrawling(boolean)` | 爬行状态 |
| `forceCrawl(int)` | 强制指定 tick 数的爬行姿势 |
| `triggerAttack()` | 触发攻击动画 |
| `startGrabbing(LivingEntity)` | 开始抓取目标 |
| `startEating(LivingEntity)` | 开始吞噬目标 |
| `hasLineOfSightThroughGlass(Player)` | 检测是否能透过玻璃看到玩家 |
| `canUseDoor()` | 恶魔能否使用门 |

### PlayerKarma (Attachment)

| 方法 | 说明 |
|------|------|
| `getKarma()` | 获取 Karma 值 (0-20) |
| `setKarma(int)` | 直接设置 Karma 值 |
| `addKarma(int)` | 增加 Karma (不超过 20) |
| `subKarma(int)` | 减少 Karma (不低于 0) |
| `copyFrom(PlayerKarma)` | 从另一个实例复制 |
| `serializeNBT()` / `deserializeNBT(CompoundTag)` | NBT 序列化 |
