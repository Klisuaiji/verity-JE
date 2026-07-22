package varmite.verity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import me.shedaniel.clothconfig2.api.ConfigBuilder;

/**
 * In-game configuration screen for Verity, built on top of Cloth Config API.
 *
 * <p>Registered via {@link net.neoforged.neoforge.client.gui.IConfigScreenFactory},
 * which expects a {@code (Minecraft, Screen) -> Screen} factory. This class satisfies
 * that contract through its {@code (Minecraft, Screen)} constructor, since it is a
 * {@link Screen} subclass.</p>
 */
public class ConfigurationScreen extends Screen {
    private final Screen parent;

    public ConfigurationScreen(Minecraft minecraft, Screen parent) {
        super(Component.translatable("verity.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("verity.config.title"))
                .setSavingRunnable(() -> {
                    // TODO: persist configuration values here
                });

        // TODO: register config categories and entries, e.g.
        // ConfigCategory general = builder.getOrCreateCategory(Component.translatable("verity.config.general"));
        // ConfigEntryBuilder entryBuilder = ConfigEntryBuilder.create();
        // general.addEntry(entryBuilder.startBooleanToggle(
        //         Component.translatable("verity.config.example"), true)
        //         .setDefaultValue(true)
        //         .setSaveConsumer(value -> { /* store value */ })
        //         .build());

        minecraft.setScreen(builder.build());
    }
}
