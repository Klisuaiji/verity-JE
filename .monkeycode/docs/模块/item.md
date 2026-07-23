# item/ — 物品系统

注册 4 种模组物品、管理 Verity 纹理变体和全亮渲染模型。

## 结构

```
item/
├── ModItems.java                 # DeferredRegister 物品注册
├── ModCreativeModeTabs.java      # 创造模式标签页
├── VerityItem.java               # 核心 Verity 物品（可食用、掉落生成实体）
├── VerityVariants.java           # 16 种纹理变体定义与校验
├── UnshadedBakedModel.java       # 全亮无着色烘焙模型包装器
└── client/
    └── VerityItemRenderer.java   # 物品栏球体渲染 (BEWLR)
```

## 关键文件

| 文件 | 目的 |
|------|------|
| `ModItems.java` | 注册 verity_item / flashlight / verity_disc / verity_edit_disc |
| `ModCreativeModeTabs.java` | 注册 "Verity" 创造模式标签页包含 4 种物品 |
| `VerityItem.java` | `extends Item`，可食用，掉落 20tick 后生成 VerityEntity，不同破坏方式有不同反应 |
| `VerityVariants.java` | 定义 16 种变体名 (happy/crazy/evil 等)，`fromStack(ItemStack)` 从 NBT 读取并校验 |
| `UnshadedBakedModel.java` | `BakedModelWrapper`，覆盖每个 quad 的 color=white、lightmap=fullbright、normal=up |
| `VerityItemRenderer.java` | `BlockEntityWithoutLevelRenderer`，在物品栏中以程序化球体渲染 |

## 依赖

**本模块依赖**:
- `../entity/client/SphereMesh.java` — VerityItemRenderer 使用相同的程序化球体
- `../entity/client/VerityEntityTexture.java` — 类似 HSB 色相逻辑

**依赖本模块的**:
- `../event/ModBusClientSetup.java` — `RegisterClientExtensionsEvent` 绑定 VerityItemRenderer
- `../event/ModBusClientSetup.java` — `ModifyBakingResult` 包装为 UnshadedBakedModel
- `../event/ModEvents.java` — 物品相关事件

## 规范

### 全亮渲染原理

`UnshadedBakedModel.getQuads()` 针对 Block 顶点格式（每顶点 8 ints）修改：
- Offset 1 (color): `-1` = 纯白 (0xFFFFFFFF ABGR)
- Offset 4 (lightmap): `0xF000F0` = LightTexture.pack(15, 15) 全亮
- Offset 5 (normal): `32512` = 法线 (0, 1, 0)

### 变体 NBT

- Tag 键: `Variant`
- Tag 类型: `String`
- 有效值: `VerityVariants.SANITIZED_VARIANTS` 集合中的 16 种
- 无效值自动回退到 `"happy"`
