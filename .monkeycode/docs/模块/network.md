# network/ — 网络同步

定义 S2C (服务端到客户端) 数据包，用于 TTS 语音下发和 Karma 值同步。

## 结构

```
network/
├── ModNetwork.java               # RegisterPayloadHandlersEvent 注册处理
├── PlayTtsPayload.java           # TTS 语音下发 record (CustomPacketPayload)
├── PlayTtsClientHandler.java     # 客户端 TTS 处理
├── PlayTtsHandler.java           # 旧版 SimpleChannel 处理器（已废弃）
├── KarmaSyncS2CPacket.java       # Karma 同步 record
└── ClientKarmaData.java          # 客户端 Karma 静态缓存
```

## 关键文件

| 文件 | 目的 |
|------|------|
| `ModNetwork.java` | 在 `RegisterPayloadHandlersEvent` 中注册 2 个 S2C 数据包和处理器 |
| `PlayTtsPayload.java` | `record(int entityId, String text)`, 客户端收到后播放 TTS |
| `KarmaSyncS2CPacket.java` | `record(int karma)`, 客户端收到后更新 `ClientKarmaData` |
| `ClientKarmaData.java` | 静态 `int playerKarma` 缓存，供 KarmaHudOverlay 读取 |

## 网络注册模式

在 `ModNetwork.register()`:

```java
registrar.playToClient(
    PlayTtsPayload.TYPE,
    PlayTtsPayload.STREAM_CODEC,
    PlayTtsPayload::handleData
);
registrar.playToClient(
    KarmaSyncS2CPacket.TYPE,
    KarmaSyncS2CPacket.STREAM_CODEC,
    KarmaSyncS2CPacket::handleData
);
```

## 依赖

**本模块依赖**:
- `../entity/AI/AiAPI.java` — TTS 处理调用
- `../gui/PlayerKarma.java` — Karma 数据源

**依赖本模块的**:
- `../event/ModEvents.java` — 同步 Karma 到客户端
- `../event/ModBusClientSetup.java` — 无直接依赖（网络在 Verity.java 中注册）
