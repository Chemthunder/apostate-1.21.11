package net.not_assher.apostate.core.utilities.bounty;

import com.mojang.serialization.Codec;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.StringIdentifiable;

/**
 * @author Chemthunder
 */
public enum KillContext implements StringIdentifiable {
    DEAD("dead", Text.literal("wanted dead.").formatted(Formatting.RED)),
    ALIVE("alive", Text.literal("wanted alive.").formatted(Formatting.YELLOW)),
    EITHER("either", Text.literal("wanted dead or alive.").formatted(Formatting.GREEN));

    public final String id;
    public final Text txt;

    public static final Codec<KillContext> CODEC = StringIdentifiable.createCodec(KillContext::values);

    KillContext(String id, Text txt) {
        this.id = id;
        this.txt = txt;
    }

    public String asString() {
        return id;
    }
}
