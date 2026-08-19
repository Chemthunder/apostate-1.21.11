package net.not_assher.apostate.core.index;

import com.mojang.serialization.Codec;
import net.acoyt.acornlib.api.registrants.DataComponentTypeRegistrant;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.item.component.TabletComponent;
import net.not_assher.apostate.core.utilities.records.Bounty;
import net.not_assher.apostate.core.utilities.records.Pact;

import static net.not_assher.apostate.core.Apostate.LOGGER;

/**
 * @author Chemthunder
 */
public interface ModDataComponentTypes {
    DataComponentTypeRegistrant DCT = new DataComponentTypeRegistrant(Apostate.MOD_ID);

    ComponentType<Bounty> STORED_BOUNTY = DCT.register(
            "stored_bounty",
            Bounty.CODEC,
            Bounty.PACKET
    );

    ComponentType<Pact> STORED_PACT = DCT.register(
            "stored_pact",
            Pact.CODEC,
            Pact.PACKET
    );

    ComponentType<TabletComponent> TABLET = DCT.register(
            "tablet_component",
            TabletComponent.CODEC,
            TabletComponent.PACKET
    );

    ComponentType<Integer> INTEGER = DCT.register(
            "integer",
            Codec.INT,
            PacketCodecs.INTEGER
    );

    ComponentType<String> STRING = DCT.register(
            "string",
            Codec.STRING,
            PacketCodecs.STRING
    );

    static void init() {
        LOGGER.info("Registered Data Component Types");
    }
}
