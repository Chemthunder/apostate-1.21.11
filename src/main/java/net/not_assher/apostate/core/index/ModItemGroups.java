package net.not_assher.apostate.core.index;

import net.acoyt.acornlib.api.registrants.CreativeModeTabRegistrant;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.not_assher.apostate.core.item.component.BountyComponent;
import net.not_assher.apostate.core.utilities.enums.KillContext;

import static net.not_assher.apostate.core.Apostate.MOD_ID;
import static net.not_assher.apostate.core.Apostate.id;

/**
 * @author Chemthunder
 */
public interface ModItemGroups {
    CreativeModeTabRegistrant plugin = new CreativeModeTabRegistrant(MOD_ID);

    RegistryKey<ItemGroup> GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, id(MOD_ID));
    ItemGroup ITEM_GROUP = plugin.register(GROUP_KEY.getValue().getPath(), FabricItemGroup.builder()
            .icon(ModItemGroups::createIcon)
            .displayName(Text.translatable("itemGroup." + MOD_ID).withColor(0xFF621414))
            .build());

    static void init() {
        ItemGroupEvents.modifyEntriesEvent(GROUP_KEY).register(
                entries ->  {
                    ModItems.plugin.toRegister.forEach(entries::add);
                    ModBlocks.plugin.toRegister.forEach(entries::add);
                }
        );
    }

    private static ItemStack createIcon() {
        ItemStack stack = new ItemStack(ModItems.BOUNTY_POSTER);

        stack.set(ModDataComponentTypes.STORED_BOUNTY, new BountyComponent(
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
