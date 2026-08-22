package net.not_assher.apostate.core.index;

import com.mojang.serialization.Codec;
import net.acoyt.acornlib.api.registrants.DataComponentTypeRegistrant;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodecs;
import net.not_assher.apostate.core.Apostate;
import net.not_assher.apostate.core.item.component.BookComponent;
import net.not_assher.apostate.core.item.component.TabletComponent;
import net.not_assher.apostate.core.utilities.records.Bounty;
import net.not_assher.apostate.core.utilities.records.Pact;

import java.util.List;

/**
 * @author Chemthunder
 */
public interface ModDataComponentTypes {
    DataComponentTypeRegistrant plugin = new DataComponentTypeRegistrant(Apostate.MOD_ID);

    ComponentType<Bounty> STORED_BOUNTY = plugin.register(
            "stored_bounty",
            Bounty.CODEC,
            Bounty.PACKET
    );

    ComponentType<Pact> STORED_PACT = plugin.register(
            "stored_pact",
            Pact.CODEC,
            Pact.PACKET
    );

    ComponentType<TabletComponent> TABLET = plugin.register(
            "tablet_component",
            TabletComponent.CODEC,
            TabletComponent.PACKET
    );

    ComponentType<BookComponent> BOOK = plugin.register(
            "bounty_book",
            BookComponent.CODEC,
            BookComponent.PACKET
    );

    ComponentType<Integer> INTEGER = plugin.register(
            "integer",
            Codec.INT,
            PacketCodecs.INTEGER
    );

    ComponentType<String> STRING = plugin.register(
            "string",
            Codec.STRING,
            PacketCodecs.STRING
    );

    ComponentType<List<ItemStack>> STACK_LIST = plugin.register("stack_list", ComponentType.<List<ItemStack>>builder()
            .codec(ItemStack.CODEC.listOf())
            .packetCodec(ItemStack.OPTIONAL_LIST_PACKET_CODEC)
            .build()
    );

    static void init() {}
}
