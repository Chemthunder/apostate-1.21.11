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

    ComponentType<BountyComponent> BOUNTY = plugin.register(
            "bounty",
            BountyComponent.CODEC,
            BountyComponent.PACKET_CODEC
    );

    ComponentType<PactComponent> PACT = plugin.register(
            "pact",
            PactComponent.CODEC,
            PactComponent.PACKET_CODEC
    );

    ComponentType<TabletComponent> TABLET = plugin.register(
            "tablet",
            TabletComponent.CODEC,
            TabletComponent.PACKET_CODEC
    );

    ComponentType<BookComponent> BOOK = plugin.register(
            "bounty_book",
            BookComponent.CODEC,
            BookComponent.PACKET_CODEC
    );

    ComponentType<VowComponent> VOW = plugin.register(
            "vow",
            VowComponent.CODEC,
            VowComponent.PACKET_CODEC
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
