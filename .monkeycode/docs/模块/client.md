# client/ — 客户端系统

处理所有客户端侧渲染、音频输入/输出、按键绑定和 HUD 显示。

## 结构

```
client/
├── IntroVideoScreen.java         # 532 帧开场视频播放
├── AudioHudRenderer.java         # 录音音量电平条 HUD
├── DynamicLightManager.java      # 动态光照管理器（线程安全光束存储）
├── KeybindRegistry.java          # V/M 键位注册
├── KeybindHandler.java           # V 键按住录音、M 键切换麦克风
├── VerityPreviewTexture.java     # 配置界面色相预览纹理
├── audio/                        # 音频输入
│   ├── MicrophoneManager.java    # 麦克风设备扫描与切换
│   └── MicrophoneRecorder.java   # 16kHz/16bit 录音 + RMS 电平
└── sound/                        # 音频输出
    ├── ClientSoundHandler.java   # 恶魔追击音效播放
    └── DemonChaseSoundInstance.java  # 循环追击音效实例
```

## 关键文件

| 文件 | 目的 |
|------|------|
| `IntroVideoScreen.java` | 532 帧 24fps PNG 动画，ESC 跳过，配乐 intro_video_audio |
| `AudioHudRenderer.java` | 屏幕底部中央录音电平条，带淡出动画（类似对讲机 UI） |
| `DynamicLightManager.java` | 线程安全的 `List<Beam>` 存储，每帧更新供 Mixin 读取 |
| `KeybindRegistry.java` | 注册两个键位：`PUSH_TO_TALK` (V) 和 `CYCLE_MIC` (M) |
| `KeybindHandler.java` | 按住 V 录音循环、松开后异步转写 + 发送聊天、M 键切换麦克风 |
| `MicrophoneRecorder.java` | 后台线程 16kHz 16bit 单声道录音，提供 RMS 电平值 |
| `MicrophoneManager.java` | 扫描系统音频设备，循环切换 TargetDataLine |
| `ClientSoundHandler.java` | 播放/停止恶魔追击循环音效 |
| `DemonChaseSoundInstance.java` | `AbstractTickableSoundInstance` 跟随恶魔位置，状态变更时停止 |

## 依赖

**本模块依赖**:
- `../entity/AI/AiAPI.java` — 录音后调用 STT 转写
- `../mixin/` — DynamicLightManager 数据被 Mixin 读取注入光照

**依赖本模块的**:
- `../event/ModBusClientSetup.java` — 注册 GUI 叠加层和按键
- `../event/ModClientEvents.java` — 客户端 tick 更新 DynamicLightManager 和开场视频
