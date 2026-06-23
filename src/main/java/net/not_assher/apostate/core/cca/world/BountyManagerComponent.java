package net.not_assher.apostate.core.cca.world;

import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.World;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.utilities.bounty.Bounty;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public class BountyManagerComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<BountyManagerComponent> KEY = ComponentRegistry.getOrCreate(
            Apostate.id("bounty_manager"),
            BountyManagerComponent.class
    );
    private final World world;

    private List<Bounty> targets = new ArrayList<>();

    public BountyManagerComponent(World world) {
        this.world = world;
    }

    public void tick() {

    }

    public void sync() {
        KEY.sync(world);
    }

    public void readData(ReadView nbt) {
        this.targets = nbt.read("Targets", Bounty.CODEC.listOf()).orElse(new ArrayList<>());
    }

    public void writeData(WriteView nbt) {
        nbt.put("Targets", Bounty.CODEC.listOf(), targets);
    }

    public List<Bounty> getTargets() {
        return targets;
    }

    public void setTargets(List<Bounty> targets) {
        this.targets = targets;
        this.sync();
    }
}
