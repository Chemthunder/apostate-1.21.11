package net.not_assher.apostate.core.cmnd;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.not_assher.apostate.core.Apostate;

import static net.minecraft.server.command.CommandManager.literal;

/**
 * @author Chemthunder
 */
public class BungerCommand implements CommandRegistrationCallback {
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(literal("protocol_debug").executes(context -> {
            context.getSource().getPlayer().sendMessage(Text.literal("Bunger"));
            return 1;
        }));
    }

    public static void create() {
        CommandRegistrationCallback.EVENT.register(new BungerCommand());
        Apostate.LOGGER.info("Created BungerCommand");
    }
}
