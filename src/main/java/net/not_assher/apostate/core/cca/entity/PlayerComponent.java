package net.not_assher.apostate.core.cca.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.not_assher.apostate.core.Apostate;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class PlayerComponent implements AutoSyncedComponent {
    public static final ComponentKey<PlayerComponent> KEY = ComponentRegistry.getOrCreate(
            Apostate.id("player"),
            PlayerComponent.class
    );
    private final PlayerEntity player;

    private String name = "";

    public PlayerComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(player);
    }

    public void readData(ReadView readView) {
        name = readView.getString("Name", "");
    }

    public void writeData(WriteView writeView) {
        writeView.putString("Name", name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.sync();
    }
}
