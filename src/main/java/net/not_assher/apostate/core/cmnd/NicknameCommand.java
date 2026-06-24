package net.not_assher.apostate.core.cmnd;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.cca.entity.PlayerComponent;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class NicknameCommand implements CommandRegistrationCallback {
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(literal("nick")
                .then(literal("set").then(argument("nickname", MessageArgumentType.message()).executes(context -> {
                    String toSet = MessageArgumentType.getMessage(context, "nickname").getString();

                    PlayerComponent.KEY.get(context.getSource().getPlayerOrThrow()).setName(toSet);

                    context.getSource().sendFeedback(() -> Text.literal("Set nickname to " + toSet), false);
                    return SINGLE_SUCCESS;
                })))

                .then(literal("get").executes(context -> {
                    if (context.getSource().getPlayer() != null) {
                        context.getSource().sendFeedback(() ->
                                Text.literal("Your nickname is " + PlayerComponent.KEY.get(context.getSource().getPlayer()).getName()),
                                false
                        );
                    }
                    return SINGLE_SUCCESS;
                }))

                .then(literal("reset").executes(context -> {
                    if (context.getSource().getPlayer() != null) {
                        PlayerComponent.KEY.get(context.getSource().getPlayer()).setName("");

                        context.getSource().sendFeedback(() ->
                                Text.literal("Reset nickname."),
                                false
                        );
                    }
                    return SINGLE_SUCCESS;
                }))
        );
    }

    public static void create() {
        CommandRegistrationCallback.EVENT.register(new NicknameCommand());
        Apostate.LOGGER.info("Created NicknameCommand");
    }
}
