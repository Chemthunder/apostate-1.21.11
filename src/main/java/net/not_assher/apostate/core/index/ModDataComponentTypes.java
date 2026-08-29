package net.not_assher.apostate.core.index;

import com.mojang.serialization.Codec;
import net.acoyt.acornlib.api.registrants.DataComponentTypeRegistrant;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.item.component.BookComponent;
import net.not_assher.apostate.core.item.component.BountyComponent;
import net.not_assher.apostate.core.item.component.PactComponent;
import net.not_assher.apostate.core.item.component.TabletComponent;

/**
 * @author Chemthunder
 */
public interface ModDataComponentTypes {
    DataComponentTypeRegistrant plugin = new DataComponentTypeRegistrant(Apostate.MOD_ID);

    ComponentType<BountyComponent> STORED_BOUNTY = plugin.register(
            "stored_bounty",
            BountyComponent.CODEC,
            BountyComponent.PACKET
    );

    ComponentType<PactComponent> STORED_PACT = plugin.register(
            "stored_pact",
            PactComponent.CODEC,
            PactComponent.PACKET
    );

    ComponentType<TabletComponent> TABLET = plugin.register(
            "tablet_component",
            TabletComponent.CODEC,
            TabletComponent.PACKET
    );

    ComponentType<BookComponent> BOOK = plugin.register(
            "bounty_book",
            BookComponent.CODEC,
            BookComponent.PACKET
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
