package net.not_assher.apostate.core.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.cca.entity.PlayerComponent;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

/**
 * @author Chemthunder
 */
public class StatusCommand implements CommandRegistrationCallback {
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(literal("status")
                .then(literal("lore").executes(context -> {
                    PlayerEntity player = context.getSource().getPlayer();
                    PlayerComponent component = PlayerComponent.KEY.get(player);

                    component.setLore(!component.isAfk());

                    for (ServerPlayerEntity serverPlayer : context.getSource().getServer().getPlayerManager().getPlayerList()) {
                        serverPlayer.networkHandler.sendPacket(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_DISPLAY_NAME, context.getSource().getPlayer()));
                    }
                    return 1;
                }).then(argument("state", BoolArgumentType.bool()).executes(context -> {
                    PlayerEntity player = context.getSource().getPlayer();

                    if (player != null) {
                        PlayerComponent component = PlayerComponent.KEY.get(player);

                        component.setLore(BoolArgumentType.getBool(context, "state"));

                        for (ServerPlayerEntity serverPlayer : context.getSource().getServer().getPlayerManager().getPlayerList()) {
                            serverPlayer.networkHandler.sendPacket(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_DISPLAY_NAME, context.getSource().getPlayer()));
                        }
                    }
                    return 1;
                })))
        );
    }

    public static void create() {
        CommandRegistrationCallback.EVENT.register(new StatusCommand());
        Apostate.LOGGER.info("Created StatusCommand");
    }
}
