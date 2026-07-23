# event/ — 事件系统

处理所有服务端和客户端 Minecraft 游戏事件，包括玩家交互、AI 对话触发、恶魔生成调度、手电筒逻辑、Karma 更新和配置重载。

## 结构

```
event/
├── ModBusCommonSetup.java        # MOD 总线通用设置（实体属性注册）
├── ModBusClientSetup.java        # MOD 总线客户端设置（渲染器+GUI+HUD+物品渲染注册）
├── ModEvents.java                # GAME 总线主服务端事件 (~1308行)
├── ModClientEvents.java          # GAME 总线主客户端事件
├── ConfigEventHandler.java       # 配置重载监听
├── DemonWindowSpawner.java       # 恶魔窗口后追猎生成
├── VeritySpawnScheduler.java     # 定时生成调度器
├── ChestCloseHandler.java        # 箱子关闭检测 Verity 物品
├── FlashlightServerLogic.java    # 手电筒光线追踪 + 方块放置
├── VerityVisuals.java            # 自定义雾色/雾距
├── VerityPleadingHandler.java    # 玩家安抚恶魔逻辑
└── WorldSpawnData.java           # SavedData 全局持久化

```

## 关键文件

| 文件 | 目的 |
|------|------|
| `ModEvents.java` | 最核心的事件处理器：聊天解析、AI 对话触发、30+ 事件监听 |
| `ModBusClientSetup.java` | 注册所有客户端组件：实体渲染器、物品渲染器、GUI 叠加层、模型修改 |
| `FlashlightServerLogic.java` | 服务端每 tick 为手电筒玩家放置/清理 FlashlightLightBlock |
| `DemonWindowSpawner.java` | 100 tick 检测玻璃/室内/室外条件并生成恶魔 |
| `VerityPleadingHandler.java` | 检测玩家聊天中 "I came back for you" 或 3 次道歉安抚恶魔 |
| `WorldSpawnData.java` | 全局 NBT 持久化：Karma、生成标志、聊天历史 (10条) |

## 依赖

**本模块依赖**:
- `../entity/` — 实体类型和实体实例
- `../entity/AI/` — AiAPI 调用
- `../network/` — Karma 同步数据包
- `../gui/` — PlayerKarma Attachment

**依赖本模块的**:
- `../Verity.java` — 在构造函数中注册事件监听器

## 规范

### 事件总线区分

- **MOD 总线**: 用于注册组件时触发（属性、渲染器、GUI、数据包）。在 Mod 构造函数中注册或用 `@EventBusSubscriber(bus=MOD)` 注解。
- **GAME 总线**: 用于游戏运行时事件（聊天、tick、交互）。用 `@EventBusSubscriber(bus=GAME)` 注解。

### 客户端/服务端隔离

- 客户端事件标注 `value = Dist.CLIENT`，避免服务端崩溃
- GAME 总线的客户端事件处理类名后缀 `Client` (如 `ModClientEvents`)
