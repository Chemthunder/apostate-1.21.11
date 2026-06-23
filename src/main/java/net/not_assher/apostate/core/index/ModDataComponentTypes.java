package net.not_assher.apostate.core.index;

import com.mojang.serialization.Codec;
import net.acoyt.acornlib.api.registrants.DataComponentTypeRegistrant;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.utilities.Contract;
import net.not_assher.apostate.core.utilities.bounty.Bounty;

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

    ComponentType<Boolean> COMPLETED_BOUNTY = DCT.register(
            "completed_bounty",
            Codec.BOOL,
            PacketCodecs.BOOLEAN
    );

    ComponentType<Contract> STORED_CONTRACT = DCT.register(
            "stored_contract",
            Contract.CODEC,
            Contract.PACKET
    );

    static void init() {
        LOGGER.info("Registered Data Component Types");
    }
}
