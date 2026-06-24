package net.not_assher.apostate.core.index;

import net.acoyt.acornlib.api.registrants.ItemGroupRegistrant;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.not_assher.apostate.core.utilities.enums.KillContext;
import net.not_assher.apostate.core.utilities.records.Bounty;

import static net.not_assher.apostate.core.Apostate.*;

/**
 * @author Chemthunder
 */
public interface ModItemGroups {
    ItemGroupRegistrant GROUPS = new ItemGroupRegistrant(MOD_ID);

    RegistryKey<ItemGroup> GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, id(MOD_ID));
    ItemGroup ITEM_GROUP = GROUPS.register(GROUP_KEY.getValue().getPath(), FabricItemGroup.builder()
            .icon(ModItemGroups::createIcon)
            .displayName(Text.translatable("itemGroup." + MOD_ID).withColor(0xFF621414))
            .build());

    static void init() {
        ItemGroupEvents.modifyEntriesEvent(GROUP_KEY).register(
                entries -> ModItems.ITEMS.toRegister.forEach(entries::add)
        );

        LOGGER.info("Registered Item Groups");
    }

    private static ItemStack createIcon() {
        ItemStack stack = new ItemStack(ModItems.BOUNTY_POSTER);

        stack.set(ModDataComponentTypes.STORED_BOUNTY, new Bounty(
                "",
                "",
                KillContext.EITHER,
                true,
                false,
                true
        ));

        return stack;
    }
}
