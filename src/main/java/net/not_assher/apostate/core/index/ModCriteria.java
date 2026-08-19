package net.not_assher.apostate.core.index;

import net.acoyt.acornlib.api.registrants.CriterionRegistrant;
import net.minecraft.advancement.criterion.TickCriterion;
import net.not_assher.apostate.core.Apostate;

import static net.not_assher.apostate.core.Apostate.MOD_ID;

/**
 * @author Chemthunder
 */
public interface ModCriteria {
    CriterionRegistrant CRITERIA = new CriterionRegistrant(MOD_ID);

    TickCriterion ROOT = CRITERIA.register("root", new TickCriterion());

    TickCriterion PLACE_BOUNTY = CRITERIA.register("place_bounty", new TickCriterion());
    TickCriterion COLLECT_BOUNTY = CRITERIA.register("collect_bounty", new TickCriterion());

    TickCriterion SIGN_CONTRACT = CRITERIA.register("sign_contract", new TickCriterion());

    TickCriterion USE_TABLET = CRITERIA.register("use_tablet", new TickCriterion());

    static void init() {
        Apostate.LOGGER.info("Registered Criterions");
    }
}
