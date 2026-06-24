package net.not_assher.apostate.core.utilities.enums;

import com.mojang.serialization.Codec;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.StringIdentifiable;

/**
 * @author Chemthunder
 */
public enum KillContext implements StringIdentifiable {
    DEAD("dead", Text.literal("dead."), 0xff0000, Formatting.RED, Formatting.BOLD),
    ALIVE("alive", Text.literal("alive."), 0x00ff16, Formatting.GREEN, Formatting.BOLD),
    EITHER("either", Text.literal("dead or alive."), 0xfdff00, Formatting.YELLOW, Formatting.BOLD);

    public final String id;
    public final Text txt;
    public final Formatting[] formatting;

    public final int color;

    public static final Codec<KillContext> CODEC = StringIdentifiable.createCodec(KillContext::values);

    KillContext(String id, Text txt, int color, Formatting... formattings) {
        this.id = id;
        this.txt = txt;
        this.color = color;
        this.formatting = formattings;
    }

    public String asString() {
        return id;
    }
}
