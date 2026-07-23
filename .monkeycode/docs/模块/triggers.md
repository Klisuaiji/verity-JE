# triggers/ — 自定义进度触发器

为 Minecraft 进度/成就系统提供 8 种自定义准则触发器 (Criterion Trigger)。

## 结构

```
triggers/
├── ModTriggers.java              # DeferredRegister 注册 8 种触发器
├── BadKarmaTrigger.java          # 低 Karma 触发
├── FavoriteSongTrigger.java      # 收藏歌曲触发
├── GoodKarmaTrigger.java         # 高 Karma 触发
├── KarmaChangeTrigger.java       # Karma 变化触发
├── OpenBoxTrigger.java           # 开盒子触发
├── PlaySoundTrigger.java         # 播放音效触发
├── TalkTrigger.java              # 对话触发
└── VillageTrigger.java           # 村庄触发

```

## 触发的触发器清单

| 触发器 | 触发位置 | 触发条件 |
|--------|---------|---------|
| `OpenBoxTrigger` | `BoxEntity.triggerOpen()` | 盒子实体触发打开动画时 |
| `TalkTrigger` | `AiAPI.askGroq()` | AI 对话成功返回时 |
| `VillageTrigger` | `ModEvents.java` | AI action `get_nearest_village` 执行时 |
| `KarmaChangeTrigger` | `ModEvents.updateAndSyncKarma()` | Karma 值发生任何变化时 |
| `GoodKarmaTrigger` | `ModEvents.updateAndSyncKarma()` | Karma >= 18 时 |
| `BadKarmaTrigger` | `ModEvents.updateAndSyncKarma()` | Karma <= 3 时 |
| `PlaySoundTrigger` | `ClientSoundHandler` / AiAPI action | 播放自定义音效时 |
| `FavoriteSongTrigger` | `ModEvents` (唱片右键) | 播放 verity_disc / verity_edit_disc 时 |

## 模式

所有触发器类遵循相同的内部结构：

```java
public class XxxTrigger extends SimpleCriterionTrigger<XxxTrigger.TriggerInstance> {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("verity", "xxx");

    public TriggerInstance createInstance(JsonObject json, ...) { ... }
    public void trigger(ServerPlayer player) { ... }

    public static class TriggerInstance implements SimpleCriterionTrigger.SimpleInstance {
        // 可能包含 JSON 反序列化的额外字段
    }
}
```

## 依赖

**本模块依赖**:
- 无外部依赖，纯 Minecraft API

**依赖本模块的**:
- `../entity/custom/BoxEntity.java` — triggerOpen() 调用
- `../event/ModEvents.java` — Karma 变化时触发
- `../entity/AI/AiAPI.java` — 对话完成时触发
