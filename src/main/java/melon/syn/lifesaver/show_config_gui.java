package melon.syn.lifesaver;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.ConfigManager;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;

public class show_config_gui extends LightweightGuiDescription {
    private final Object ConfigManager = null;

    public show_config_gui() {
        // Creates GUI controls
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(120, 25);

        WLabel title = new WLabel(new LiteralText("Syn lifesaver"));
        root.add(title,0,0,4,1);

        WTextField config_y_level = new WTextField(new LiteralText("minimal  Y level"));
        root.add(config_y_level, 0,1,10,1);

        WTextField config_command = new WTextField(new LiteralText("Command to run"));
        root.add(config_command, 0,2,10,1);

        WTextField config_wait = new WTextField(new LiteralText("Internal on each cmd run"));
        root.add(config_wait,0,3,10,1);

        WToggleButton config_enabled = new WToggleButton(new LiteralText("Enable/Disable mod"));
        root.add(config_enabled,0,4,4,1);

        WButton config_apply = new WButton(new LiteralText("Apply settings"));
        root.add(config_apply,0,5,10,1);

        // Sets the settings
        configloader config_file = AutoConfig.getConfigHolder(configloader.class).getConfig();
        config_y_level.setText(String.valueOf(config_file.level_y));
        config_command.setText(config_file.command);
        config_wait.setText(String.valueOf(config_file.internal));
        config_enabled.setToggle(config_file.enabled);

        try {
            config_file.validatePostLoad();
        } catch (ConfigData.ValidationException e) {

        }

        // Creates events for controls
        config_apply.setOnClick(() -> {
            try {
                config_file.level_y = Integer.parseInt(config_y_level.getText());
            } catch (NumberFormatException e) {
                MinecraftClient.getInstance().player.sendMessage(Text.of("§a[§3Syn lifesaver§a] §6'"+config_y_level.getText()+"' §4is not a number in Y level, this setting will not be changed"), false);
            }

            try {
                config_file.internal = Integer.parseInt(config_wait.getText());
            } catch (NumberFormatException e) {
                MinecraftClient.getInstance().player.sendMessage(Text.of("§a[§3Syn lifesaver§a] §6 '"+config_wait.getText()+"' §4is not a number in internal, this setting will not be changed"), false);
            }

            config_file.enabled = config_enabled.getToggle();
            config_file.command = config_command.getText();
            
            // Writes the config file

            try {
                FileWriter save_config_file = new FileWriter(FabricLoader.getInstance().getConfigDir()+"\\syn_life_saver.json");
                save_config_file.write(String.format("{\n" +
                        "  \"level_y\": %s,\n" +
                        "  \"internal\": %s,\n" +
                        "  \"command\": \"%s\",\n" +
                        "  \"enabled\": %s\n" +
                        "}", config_file.level_y, config_file.internal, config_file.command, config_file.enabled));
                save_config_file.close();
            } catch (IOException e) {
                MinecraftClient.getInstance().player.sendMessage(Text.of("§a[§3Syn lifesaver§a] §4A error occurred while saving config file"), false);
                e.printStackTrace();
            } finally {
                MinecraftClient.getInstance().player.sendMessage(Text.of("§a[§3Syn lifesaver§a] §2Applied settings"), false);
            }


        });

        // Shows the GUI
        root.validate(this);
    }
}