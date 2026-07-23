# entity/ — 实体系统

注册并实现 3 种自定义生物实体：VerityEntity (球形 AI)、VerityDemonEntity (恶魔)、BoxEntity (初始容器)。

## 结构

```
entity/
├── ModEntities.java              # EntityType DeferredRegister 注册
├── AI/                           # AI 行为定义
│   ├── AiAPI.java                # LLM + TTS + STT 集成核心
│   ├── DemonAttackGoal.java      # 恶魔近战攻击
│   ├── DemonBreakDoorGoal.java   # 恶魔破门
│   ├── DemonGlassBreakAndLeapGoal.java  # 恶魔碎玻璃跳跃
│   ├── DemonStareAndBreakGoal.java      # 恶魔凝视转化
│   ├── DemonWindowStalkGoal.java        # 恶魔窗口跟踪
│   ├── SherpaBridge.java         # Sherpa-ONNX 反射桥接
│   ├── VerityLocalTTS.java       # 本地 Piper TTS
│   └── pathfinding/              # 自定义路径查找
│       ├── DemonPathNavigation.java
│       └── DemonNodeEvaluator.java
├── client/                       # 实体渲染
│   ├── BoxModel.java / BoxRenderer.java
│   ├── SphereEntityRenderer.java / SphereMesh.java / SphereRenderHelper.java
│   ├── VerityAnimation.java
│   ├── VerityDemonModel.java / VerityDemonRenderer.java
│   └── VerityEntityTexture.java
└── custom/                       # 实体类实现
    ├── VerityEntity.java         # 核心球形生物
    ├── VerityDemonEntity.java    # 恶魔形态
    └── BoxEntity.java            # 初始容器
```

## 关键文件

| 文件 | 目的 |
|------|------|
| `ModEntities.java` | 注册 3 种 EntityType (verity/box/verity_demon) |
| `VerityEntity.java` | 球体 PathfinderMob，弹跳物理、16 种纹理变体、恶魔转化 |
| `VerityDemonEntity.java` | GeckoLib Enemy，5 阶段 AI、爬墙、破门、碎玻璃 |
| `BoxEntity.java` | GeckoLib 静态实体，循环播放引导语音、触发开启动画 |

## 依赖

**本模块依赖**:
- GeckoLib 4.8.3 — 骨骼动画驱动
- `../event/` — 事件触发（WorldSpawnData, ModEvents）
- `../network/` — TTS 数据包下发

**依赖本模块的**:
- `../event/ModBusCommonSetup.java` — 注册实体属性
- `../event/ModBusClientSetup.java` — 绑定渲染器
- `../event/ModEvents.java` — 实体相关事件处理

## 规范

### 添加新 AI Goal (entity/AI/)

1. 创建 `entity/AI/NewGoal.java` 继承 `net.minecraft.world.entity.ai.goal.Goal`
2. 在目标实体类 `registerGoals()` 中注册
3. Goal 生命周期方法：`canUse()` -> `start()` -> `tick()` -> `stop()`

### 添加新实体渲染器 (entity/client/)

1. 创建 Model 类继承 `GeoModel<T>`（若使用 GeckoLib）
2. 创建 Renderer 类继承 `GeoEntityRenderer<T>` 或 `EntityRenderer<T>`
3. 在 `ModBusClientSetup.registerRenderers()` 中绑定实体类型到渲染器
4. 若需自定义渲染（如球体），参考 `SphereEntityRenderer`
