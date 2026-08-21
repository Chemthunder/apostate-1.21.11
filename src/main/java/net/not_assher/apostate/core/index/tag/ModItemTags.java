package net.not_assher.apostate.core.index.tag;

import net.acoyt.acornlib.api.builder.TagBuilder;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.not_assher.apostate.core.Apostate;

/**
 * @author Chemthunder
 */
public interface ModItemTags {
    TagBuilder<Item> tag = new TagBuilder<>(Apostate.MOD_ID, RegistryKeys.ITEM);

    TagKey<Item> ACCEPTABLE = tag.register("acceptable");
    TagKey<Item> HIDES_FRAME = tag.register("hides_frame");
}
