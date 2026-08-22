package net.not_assher.apostate.core.index;

import net.acoyt.acornlib.api.registrants.CriterionTriggerRegistrant;
import net.minecraft.advancement.criterion.TickCriterion;

import static net.not_assher.apostate.core.Apostate.MOD_ID;

/**
 * @author Chemthunder
 */
public interface ModCriteria {
    CriterionTriggerRegistrant plugin = new CriterionTriggerRegistrant(MOD_ID);

    TickCriterion ROOT = plugin.register("root", new TickCriterion());

    TickCriterion PLACE_BOUNTY = plugin.register("place_bounty", new TickCriterion());
    TickCriterion COLLECT_BOUNTY = plugin.register("collect_bounty", new TickCriterion());

    TickCriterion SIGN_CONTRACT = plugin.register("sign_contract", new TickCriterion());

    TickCriterion USE_TABLET = plugin.register("use_tablet", new TickCriterion());

    TickCriterion CRIMSON_CANDLE = plugin.register("crimson_candle", new TickCriterion());

    static void init() {}
}
