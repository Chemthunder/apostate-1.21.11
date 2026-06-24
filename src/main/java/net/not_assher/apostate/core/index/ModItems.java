package net.not_assher.apostate.core.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.minecraft.item.Item;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.item.BountyPosterItem;
import net.not_assher.apostate.core.item.ContractItem;
import net.not_assher.apostate.core.utilities.Contract;
import net.not_assher.apostate.core.utilities.bounty.Bounty;

import static net.not_assher.apostate.core.Apostate.LOGGER;

/**
 * @author Chemthunder
 */
public interface ModItems {
    ItemRegistrant ITEMS = new ItemRegistrant(Apostate.MOD_ID);

    Item BOUNTY_POSTER = ITEMS.register("bounty_poster", BountyPosterItem::new, new Item.Settings()
            .maxCount(1)
            .component(ModDataComponentTypes.STORED_BOUNTY, Bounty.EMPTY)
    );

    Item CONTRACT = ITEMS.register("contract", ContractItem::new, new Item.Settings()
            .maxCount(1)
            .component(ModDataComponentTypes.STORED_CONTRACT, Contract.EMPTY)
    );

    static void init() {
        LOGGER.info("Registered Items");
    }
}
