package net.not_assher.apostate.core.client.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.not_assher.apostate.core.utilities.bounty.Bounty;

/**
 * @author Chemthunder
 */
public class PosterScreen extends Screen {
    private final Bounty toEdit;

    public PosterScreen(Bounty toEdit) {
        super(Text.empty());
        this.toEdit = toEdit;
    }

    protected void init() {
        //
    }

    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {

    }
}
