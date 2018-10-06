package net.demilich.metastone.game.spells;

import com.github.fromage.quasi.fibers.Suspendable;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.utils.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TemporaryAttackSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(TemporaryAttackSpell.class);

	public static SpellDesc create(EntityReference target, int attackBonus) {
		Map<SpellArg, Object> arguments = new SpellDesc(TemporaryAttackSpell.class);
		arguments.put(SpellArg.VALUE, attackBonus);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(int attackBonus) {
		return create(null, attackBonus);
	}

	@Override
	@Suspendable
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int attackBonus = desc.getValue(SpellArg.VALUE, context, player, target, source, 0);

		logger.debug("{} gains {} attack", target, attackBonus);

		Actor targetActor = (Actor) target;

		if (attackBonus != 0) {
			targetActor.modifyAttribute(Attribute.TEMPORARY_ATTACK_BONUS, +attackBonus);
		}
	}

}
