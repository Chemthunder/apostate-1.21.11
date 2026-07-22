package net.not_assher.apostate.core.index;

import net.acoyt.acornlib.api.registrants.DataComponentTypeRegistrant;
import net.minecraft.component.ComponentType;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.utilities.records.Bounty;
import net.not_assher.apostate.core.utilities.records.Contract;
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

    ComponentType<Contract> STORED_CONTRACT = DCT.register(
            "stored_contract",
            Contract.CODEC,
            Contract.PACKET
    );

    ComponentType<Pact> STORED_PACT = DCT.register(
            "stored_pact",
            Pact.CODEC,
            Pact.PACKET
    );

    static void init() {
        LOGGER.info("Registered Data Component Types");
    }
}
