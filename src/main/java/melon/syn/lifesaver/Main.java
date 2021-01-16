package melon.syn.lifesaver;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Main extends LightweightGuiDescription implements ModInitializer {

    // Creates global variables

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // MinecraftClient.getInstance().player.getBlockPos().getY();
        System.out.println("Loading Syn lifesaver v1.0");

        AutoConfig.register(configloader.class, GsonConfigSerializer::new);
        configloader config_file = AutoConfig.getConfigHolder(configloader.class).getConfig();

        KeyBinding keyBinding_config;
        keyBinding_config = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Open config GUI",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                "Syn lifesaver"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            while (keyBinding_config.wasPressed()) {
                MinecraftClient.getInstance().openScreen(new config_gui(new show_config_gui()));

            }
        });

        final int[] guiboy56_syn_lifesaver_tickcount = new int[1];
        guiboy56_syn_lifesaver_tickcount[0] = 0;
        ClientTickEvents.END_WORLD_TICK.register(world -> {
            PlayerEntity player = MinecraftClient.getInstance().player;
            // do stuff here
            guiboy56_syn_lifesaver_tickcount[0] +=1;
            if (config_file.enabled) {
                if (guiboy56_syn_lifesaver_tickcount[0]>=config_file.internal) {
                    guiboy56_syn_lifesaver_tickcount[0] = 0;
                    if (player.getBlockPos().getY() <= config_file.level_y) {
                    MinecraftClient.getInstance().player.sendChatMessage("/"+config_file.command);
                    }
                }
            }
        System.out.println("Finished loading Syn lifesaver v1.0");
        // System.out.println(player.getBlockPos().getY());

        });
    }

}