package net.not_assher.apostate.core.client.tooltip;

import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;
import net.not_assher.apostate.core.item.component.DiviningTabletComponent;

/**
 * @author Chemthunder
 */
public record TabletTooltipData(ItemStack self, DiviningTabletComponent component) implements TooltipData {
}
