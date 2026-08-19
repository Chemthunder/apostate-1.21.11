package net.not_assher.apostate.core.index;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.data.Model;
import net.minecraft.client.data.TextureKey;
import net.not_assher.apostate.core.Apostate;

import java.util.Optional;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public interface ModModels {
    Model DISPLAYED = create("template/displayed", TextureKey.LAYER0);

    private static Model create(String parent,  TextureKey... textureKeys) {
        return new Model(Optional.of(Apostate.id("item/" + parent)), Optional.empty(), textureKeys);
    }
}
