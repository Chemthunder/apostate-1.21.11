package net.not_assher.apostate.core.command;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.not_assher.apostate.core.Apostate;

import static net.minecraft.server.command.CommandManager.literal;

/**
 * @author Chemthunder
 */
public class FlexCommand implements CommandRegistrationCallback {
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(literal("flex")
                .executes(context -> {
                    MinecraftServer server = context.getSource().getServer();
                    PlayerEntity player = context.getSource().getPlayer();

                    if (player != null) {
                        ItemStack stack = player.getMainHandStack();

                        if (!stack.isEmpty()) {
                            server.getPlayerManager().broadcast(Text.empty()
                                            .append(context.getSource().getName())
                                            .append(Text.literal(" is displaying: "))
                                            .append(Text.literal("").formatted(Formatting.LIGHT_PURPLE)
                                                    .append(stack.toHoverableText())
                                                    .append(Text.literal("").formatted(Formatting.LIGHT_PURPLE)
                                                            .append(Text.literal(" (" + stack.getItem() + ")").formatted(Formatting.DARK_GRAY))
                                                    )
                                            ),
                                    false
                            );
                        }
                    }
                    return 1;
                })
        );
    }

    public static void create() {
        CommandRegistrationCallback.EVENT.register(new FlexCommand());
        Apostate.LOGGER.info("Created FlexCommand");
    }
}
