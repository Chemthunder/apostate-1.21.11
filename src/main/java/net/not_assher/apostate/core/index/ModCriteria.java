package net.not_assher.apostate.core.index;

import net.acoyt.acornlib.api.registrants.CriterionRegistrant;
import net.minecraft.advancement.criterion.TickCriterion;
import net.not_assher.apostate.core.Apostate;

import static net.not_assher.apostate.core.Apostate.MOD_ID;

public interface ModCriteria {
    CriterionRegistrant rant = new CriterionRegistrant(MOD_ID);

    TickCriterion ROOT = rant.register("root", new TickCriterion());

    TickCriterion PLACE_BOUNTY = rant.register("place_bounty", new TickCriterion());
    TickCriterion COLLECT_BOUNTY = rant.register("collect_bounty", new TickCriterion());

    TickCriterion SIGN_CONTRACT = rant.register("sign_contract", new TickCriterion());

    static void init() {
        Apostate.LOGGER.info("Registered Criterions");
    }
}
