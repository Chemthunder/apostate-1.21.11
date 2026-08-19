package net.not_assher.apostate.core.cca.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.not_assher.apostate.core.Apostate;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author Chemthunder
 */
public class PlayerComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<PlayerComponent> KEY = ComponentRegistry.getOrCreate(
            Apostate.id("player"),
            PlayerComponent.class
    );
    private final PlayerEntity player;

    private String name = "";

    private boolean afk = false;
    private boolean lore = false;

    private String tabletTarget = "";
    private int echoTicks = 0;
    private int emeraldTicks = 0;

    public PlayerComponent(PlayerEntity player) {
        this.player = player;
    }

    public void tick() {
        if (echoTicks > 0) {
            echoTicks--;
            if (echoTicks == 0) {
                tabletTarget = "";
                sync();
            }
        }

        if (emeraldTicks > 0) {
            emeraldTicks--;
            if (emeraldTicks == 0) {
                tabletTarget = "";

                sync();
            }
        }
    }

    public void sync() {
        KEY.sync(player);
    }

    public void readData(ReadView readView) {
        name = readView.getString("Name", "");

        tabletTarget = readView.getString("TabletTarget", "");

        echoTicks = readView.getInt("TargetTicks", 0);
        emeraldTicks = readView.getInt("EmeraldTicks", 0);

        afk = readView.getBoolean("AFK", false);
        lore = readView.getBoolean("Lore", lore);
    }

    public void writeData(WriteView writeView) {
        writeView.putString("Name", name);

        writeView.putString("TabletTarget", tabletTarget);

        writeView.putInt("TargetTicks", echoTicks);
        writeView.putInt("EmeraldTicks", emeraldTicks);

        writeView.putBoolean("AFK", afk);
        writeView.putBoolean("Lore", lore);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.sync();
    }

    public boolean isAfk() {
        return afk;
    }

    public void setAfk(boolean afk) {
        this.afk = afk;
        sync();
    }

    public boolean isLore() {
        return lore;
    }

    public void setLore(boolean lore) {
        this.lore = lore;
        sync();
    }

    public String getTabletTarget() {
        return tabletTarget;
    }

    public void setTabletTarget(String tabletTarget) {
        this.tabletTarget = tabletTarget;
        sync();
    }

    public int getEchoTicks() {
        return echoTicks;
    }

    public void setEchoTicks(int echoTicks) {
        this.echoTicks = echoTicks;
        sync();
    }

    public int getEmeraldTicks() {
        return emeraldTicks;
    }

    public void setEmeraldTicks(int emeraldTicks) {
        this.emeraldTicks = emeraldTicks;
        sync();
    }
}
