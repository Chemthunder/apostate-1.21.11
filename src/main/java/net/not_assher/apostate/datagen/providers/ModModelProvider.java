package net.not_assher.apostate.datagen.providers;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.SelectItemModel;
import net.minecraft.util.Identifier;
import net.not_assher.apostate.core.client.item.CompletedBountyProperty;
import net.not_assher.apostate.core.client.item.KillContextProperty;
import net.not_assher.apostate.core.index.ModItems;
import net.not_assher.apostate.core.utilities.bounty.KillContext;

import java.util.ArrayList;
import java.util.List;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {}

    public void generateItemModels(ItemModelGenerator itemModelGenerator) {}
}
