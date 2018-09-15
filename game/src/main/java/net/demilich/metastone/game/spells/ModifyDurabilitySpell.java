package net.demilich.metastone.game.spells;

import com.github.fromage.quasi.fibers.Suspendable;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

import java.util.Map;

public class ModifyDurabilitySpell extends Spell {

	public static SpellDesc create(EntityReference target, int durability) {
		Map<SpellArg, Object> arguments = new SpellDesc(ModifyDurabilitySpell.class);
		arguments.put(SpellArg.VALUE, durability);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(int durability) {
		return create(null, durability);
	}

	@Override
	@Suspendable
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		// if there is no weapon, just do nothing
		if (player.getHero().getWeapon() == null) {
			return;
		}
		int durabilityChange = desc.getValue(SpellArg.VALUE, context, player, target, source, 0);


		context.getLogic().modifyDurability(player.getHero().getWeapon(), durabilityChange);
	}

}
