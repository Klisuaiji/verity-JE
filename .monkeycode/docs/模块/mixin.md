# mixin/ — Mixin 注入

通过 Mixin 修改原版 Minecraft 代码行为，实现极限黑暗、动态光照和标题界面定制。

## 结构

```
mixin/
├── LightTextureMixin.java        # 极致黑暗：移除环境光，只保留火把光照
├── MixinEntityRenderer.java      # 实体渲染注入动态光照（手电筒照射实体）
├── MixinLevelRenderer.java       # 方块渲染注入动态光照（手电筒照射方块）
└── TitleScreenMixin.java         # 标题界面添加 "Mod by Varmite" 按钮
```

## 关键文件

| 文件 | Mixin 目标 | 目的 |
|------|-----------|------|
| `LightTextureMixin.java` | `LightTexture.class` | 修改光照纹理：gamma=0 时移除所有环境光，仅保留火把/火等方块光照 |
| `MixinEntityRenderer.java` | `EntityRenderer.class` | 注入 `getPackedLightCoords`，为手电筒照射范围内实体增加方块光 |
| `MixinLevelRenderer.java` | `LevelRenderer.class` | 注入 `getLightColor`，为手电筒照射范围内方块增加光照 |
| `TitleScreenMixin.java` | `TitleScreen.class` | 在标题界面添加链接到 Varmite YouTube 频道的按钮 |

## Mixin 注册

在 `src/main/resources/mixins.verity.json`:

```json
{
  "required": true,
  "package": "varmite.verity.mixin",
  "compatibilityLevel": "JAVA_21",
  "mixins": [],
  "client": [
    "LightTextureMixin",
    "MixinEntityRenderer",
    "MixinLevelRenderer",
    "TitleScreenMixin"
  ],
  "server": []
}
```

所有 Mixin 均为客户端专用 (`"client"` 数组)。

## 依赖

**本模块依赖**:
- `../client/DynamicLightManager.java` — 手电筒光束数据来源

**依赖本模块的**:
- `../event/ModClientEvents.java` — 设置 gamma=0 触发极限黑暗
- `../event/FlashlightServerLogic.java` — 提供光束坐标数据

## 规范

- 所有 Mixin 方法使用 `@Inject` 注解，避免完全覆盖原版方法
- 使用 `@Local` 捕获局部变量而非全局字段
- Mixin 方法命名: `verity$methodPurpose`
