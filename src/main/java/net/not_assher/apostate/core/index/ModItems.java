package net.not_assher.apostate.core.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.item.*;
import net.not_assher.apostate.core.item.component.BookComponent;
import net.not_assher.apostate.core.item.component.BountyComponent;
import net.not_assher.apostate.core.item.component.TabletComponent;

import java.util.ArrayList;

/**
 * @author Chemthunder
 */
public interface ModItems {
    ItemRegistrant plugin = new ItemRegistrant(Apostate.MOD_ID);

    Item BOUNTY_POSTER = plugin.register("bounty_poster", BountyPosterItem::new, new Item.Settings()
            .component(ModDataComponentTypes.STORED_BOUNTY, BountyComponent.EMPTY)
    );

    Item BOUNTY_BOOK = plugin.register("bounty_book", BountyBookItem::new, new Item.Settings()
            .maxCount(1)
            .rarity(Rarity.UNCOMMON)
            .component(ModDataComponentTypes.BOOK, new BookComponent(new ArrayList<>()))
    );

    Item PACT_CRYSTAL = plugin.register("pact_crystal", PactCrystalItem::new, new Item.Settings()
            .maxCount(1)
    );

    Item DIVINING_TABLET = plugin.register("divining_tablet", DiviningTabletItem::new, new Item.Settings()
            .maxCount(1)
            .fireproof()
            .rarity(Rarity.RARE)
            .component(ModDataComponentTypes.TABLET, new TabletComponent(null, ItemStack.EMPTY))
            .component(ModDataComponentTypes.INTEGER, DiviningTabletItem.MAX_USES)
    );

    Item IMMORTAL_DUST = plugin.register("immortal_dust", Item::new, new Item.Settings()
            .maxCount(16)
            .rarity(Rarity.RARE)
    );

    Item FLYER = plugin.register("flyer", FlyerItem::new, new Item.Settings()
            .component(ModDataComponentTypes.STRING, "Empty")
    );

    Item CHTHONIC_GOLD_INGOT = plugin.register("chthonic_gold_ingot", ChthonicGoldItem::new, new Item.Settings()
    );

    Item CHTHONIC_GOLD_NUGGET = plugin.register("chthonic_gold_nugget", ChthonicGoldItem::new, new Item.Settings()
    );

    static void init() {}
}
