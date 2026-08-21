package net.not_assher.apostate.core.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.item.BountyPosterItem;
import net.not_assher.apostate.core.item.DiviningTabletItem;
import net.not_assher.apostate.core.item.FlyerItem;
import net.not_assher.apostate.core.item.PactCrystalItem;
import net.not_assher.apostate.core.item.component.TabletComponent;
import net.not_assher.apostate.core.utilities.records.Bounty;

import static net.not_assher.apostate.core.Apostate.LOGGER;

/**
 * @author Chemthunder
 */
public interface ModItems {
    ItemRegistrant ITEMS = new ItemRegistrant(Apostate.MOD_ID);

    Item BOUNTY_POSTER = ITEMS.register("bounty_poster", BountyPosterItem::new, new Item.Settings()
            .component(ModDataComponentTypes.STORED_BOUNTY, Bounty.EMPTY)
    );

    Item PACT_CRYSTAL = ITEMS.register("pact_crystal", PactCrystalItem::new, new Item.Settings()
            .maxCount(1)
    );

    Item DIVINING_TABLET = ITEMS.register("divining_tablet", DiviningTabletItem::new, new Item.Settings()
            .maxCount(1)
            .fireproof()
            .rarity(Rarity.RARE)
            .component(ModDataComponentTypes.TABLET, new TabletComponent(null, ItemStack.EMPTY))
            .component(ModDataComponentTypes.INTEGER, 8)
    );

    Item IMMORTAL_DUST = ITEMS.register("immortal_dust", Item::new, new Item.Settings()
            .maxCount(16)
            .rarity(Rarity.RARE)
    );

    Item FLYER = ITEMS.register("flyer", FlyerItem::new, new Item.Settings()
            .component(ModDataComponentTypes.STRING, "Empty")
    );

    static void init() {
        LOGGER.info("Registered Items");
    }
}
