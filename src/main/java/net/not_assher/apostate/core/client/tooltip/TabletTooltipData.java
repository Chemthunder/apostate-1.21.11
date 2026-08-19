package net.not_assher.apostate.core.client.tooltip;

import net.minecraft.component.type.ProfileComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;
import net.not_assher.apostate.core.item.component.TabletComponent;

import java.util.Optional;

/**
 * @author Chemthunder
 */
public record TabletTooltipData(ItemStack self, TabletComponent component) implements TooltipData {
}
