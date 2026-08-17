package net.not_assher.apostate.ext;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import eu.midnightdust.lib.config.MidnightConfig;

import static net.not_assher.apostate.core.Apostate.MOD_ID;

/**
 * @author Chemthunder
 */
public class ModMenuCompat implements ModMenuApi {
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> MidnightConfig.getScreen(parent, MOD_ID);
    }
}
