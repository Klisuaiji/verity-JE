package varmite.verity;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModContainer;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

/**
 * In-game configuration screen for Verity, built on top of Cloth Config API.
 *
 * <p>Registered via {@link net.neoforged.neoforge.client.gui.IConfigScreenFactory},
 * whose {@code createScreen(ModContainer, Screen)} maps to this class's
 * {@code (ModContainer, Screen)} constructor (constructor reference).</p>
 */
public class ConfigurationScreen extends Screen {
    private final Screen parent;

    public ConfigurationScreen(ModContainer container, Screen parent) {
        super(Component.translatable("verity.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("verity.config.title"))
                .setSavingRunnable(this::saveConfig);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        // ---- General Settings ----
        ConfigCategory general = builder.getOrCreateCategory(
                Component.translatable("verity.config.general"));

        general.addEntry(entryBuilder.startBooleanToggle(
                        Component.translatable("verity.configuration.playVideo"),
                        VerityConfig.PLAY_VIDEO.get())
                .setDefaultValue(true)
                .setTooltip(Component.translatable("verity.configuration.playVideo.tooltip"))
                .setSaveConsumer(VerityConfig.PLAY_VIDEO::set)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(
                        Component.translatable("verity.configuration.requireVerity"),
                        VerityConfig.REQUIRE_VERITY.get())
                .setDefaultValue(false)
                .setTooltip(Component.translatable("verity.configuration.requireVerity.tooltip"))
                .setSaveConsumer(VerityConfig.REQUIRE_VERITY::set)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(
                        Component.translatable("verity.configuration.canCrash"),
                        VerityConfig.CAN_CRASH.get())
                .setDefaultValue(true)
                .setTooltip(Component.translatable("verity.configuration.canCrash.tooltip"))
                .setSaveConsumer(VerityConfig.CAN_CRASH::set)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(
                        Component.translatable("verity.configuration.useNativeTts"),
                        VerityConfig.USE_NATIVE_TTS.get())
                .setDefaultValue(false)
                .setTooltip(Component.translatable("verity.configuration.useNativeTts.tooltip"))
                .setSaveConsumer(VerityConfig.USE_NATIVE_TTS::set)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(
                        Component.translatable("verity.configuration.clearPeacefulMobs"),
                        VerityConfig.CLEAR_PEACEFUL_MOBS.get())
                .setDefaultValue(false)
                .setTooltip(Component.translatable("verity.configuration.clearPeacefulMobs.tooltip"))
                .setSaveConsumer(VerityConfig.CLEAR_PEACEFUL_MOBS::set)
                .build());

        // ---- AI Settings ----
        ConfigCategory aiSettings = builder.getOrCreateCategory(
                Component.translatable("verity.configuration.AISettings"));

        aiSettings.addEntry(entryBuilder.startStrField(
                        Component.translatable("verity.configuration.apiKey"),
                        VerityConfig.API_KEY.get())
                .setDefaultValue("")
                .setTooltip(Component.translatable("verity.configuration.apiKey.tooltip"))
                .setSaveConsumer(VerityConfig.API_KEY::set)
                .build());

        aiSettings.addEntry(entryBuilder.startEnumSelector(
                        Component.translatable("verity.configuration.aiModel"),
                        AiModel.class,
                        VerityConfig.AI_MODEL.get())
                .setDefaultValue(AiModel.FAST)
                .setTooltip(Component.translatable("verity.configuration.aiModel.tooltip"))
                .setSaveConsumer(model -> VerityConfig.AI_MODEL.set(model))
                .build());

        minecraft.setScreen(builder.build());
    }

    private void saveConfig() {
        VerityConfig.SPEC.save();
    }
}
