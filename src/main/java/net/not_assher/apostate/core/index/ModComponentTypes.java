package net.not_assher.apostate.core.index;

import com.mojang.serialization.Codec;
import net.acoyt.acornlib.api.registrants.DataComponentTypeRegistrant;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.item.component.*;

/**
 * @author Chemthunder
 */
public interface ModComponentTypes {
    DataComponentTypeRegistrant plugin = new DataComponentTypeRegistrant(Apostate.MOD_ID);

    ComponentType<BountyPosterComponent> BOUNTY = plugin.register(
            "bounty",
            BountyPosterComponent.CODEC,
            BountyPosterComponent.PACKET_CODEC
    );

    ComponentType<PactCrystalComponent> PACT = plugin.register(
            "pact",
            PactCrystalComponent.CODEC,
            PactCrystalComponent.PACKET_CODEC
    );

    ComponentType<DiviningTabletComponent> TABLET = plugin.register(
            "tablet",
            DiviningTabletComponent.CODEC,
            DiviningTabletComponent.PACKET_CODEC
    );

    ComponentType<BountyBookComponent> BOOK = plugin.register(
            "bounty_book",
            BountyBookComponent.CODEC,
            BountyBookComponent.PACKET_CODEC
    );

    ComponentType<CordialVowComponent> VOW = plugin.register(
            "vow",
            CordialVowComponent.CODEC,
            CordialVowComponent.PACKET_CODEC
    );

    ComponentType<Integer> INTEGER = plugin.register(
            "integer",
            Codec.INT,
            PacketCodecs.INTEGER
    );

    ComponentType<String> STRING = plugin.register(
            "string",
            Codec.STRING,
            PacketCodecs.STRING
    );

    static void init() {}
}
