package melon.syn.lifesaver;


import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name="syn_life_saver")
public class configloader implements ConfigData {
    int level_y = 0;
    int internal = 20;
    String command = "bsu";
    boolean enabled = true;

    @Override
    public void validatePostLoad() throws ValidationException {

    }

}
