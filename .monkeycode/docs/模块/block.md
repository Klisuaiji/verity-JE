# block/ — 方块系统

提供手电筒光束使用的不可见光源方块。

## 结构

```
block/
├── ModBlocks.java                # DeferredRegister 方块注册
└── FlashlightLightBlock.java     # 不可见无碰撞可替换光照方块
```

## 关键文件

| 文件 | 目的 |
|------|------|
| `ModBlocks.java` | 注册 `flashlight_light` 方块 |
| `FlashlightLightBlock.java` | `extends Block implements SimpleWaterloggedBlock`, 不可见(INVISIBLE)、无碰撞(NO)、可替换、支持含水 |

## 依赖

**本模块依赖**:
- 无外部依赖，纯 Minecraft/NeoForge API

**依赖本模块的**:
- `../event/FlashlightServerLogic.java` — 服务端每 tick 放置/清理此方块
- `../mixin/MixinLevelRenderer.java` — 动态光照计算

## 规范

- 方块 `RenderShape` 必须为 `INVISIBLE`，避免对原版渲染管线产生视觉影响
- 支持 `SimpleWaterloggedBlock` 以保证水下光照正确
- `neighborChanged()` 检测方块移除光源标志
