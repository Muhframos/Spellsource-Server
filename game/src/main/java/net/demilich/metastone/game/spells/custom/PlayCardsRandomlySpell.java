package net.demilich.metastone.game.spells.custom;

import co.paralleluniverse.common.util.Objects;
import co.paralleluniverse.fibers.Suspendable;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardList;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityLocation;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.cards.Attribute;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.Zones;

import java.util.List;

/**
 * Retrieves all the cards generated by {@link SpellUtils#getCards(GameContext, Player, Entity, Entity, SpellDesc, int)}
 * and plays them randomly.
 * <p>
 * Does not resolve battlecries, because the cards are not played from the hand.
 * <p>
 * Summons minions to the rightmost position on the battlefield.
 * <p>
 * If the choose one or adaptation has a choice associated with it (i.e., it has been played before), the same choice is
 * made. Otherwise, a random choice is made.
 * <p>
 * Does not stop executing if a card winds up destroying the {@code source}.
 * <p>
 * If the card generates itself, it will not cast itself again (no infinite loops).
 * <p>
 * If the card is not in the graveyard and in an otherwise valid location and a {@link
 * net.demilich.metastone.game.spells.desc.SpellArg#SPELL} is specified, cast it with {@link
 * net.demilich.metastone.game.targeting.EntityReference#OUTPUT} for every card cast by this effect.
 * <p>
 * For <b>example</b>, to implement the card, "Cast copies of all spells in your deck (targets chosen randomly):"
 * <pre>
 *   "spell": {
 *     "class": "PlayCardsRandomlySpell",
 *     "cardFilter": {
 *       "class": "CardFilter",
 *       "cardType": "SPELL"
 *     },
 *     "cardSource": {
 *       "class": "DeckSource",
 *       "targetPlayer": "SELF"
 *     }
 *   },
 * </pre>
 */
public class PlayCardsRandomlySpell extends Spell {

	@Override
	@Suspendable
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		CardList cards = getCards(context, player, desc, source, target);
		// Shuffle
		cards.shuffle(context.getLogic().getRandom());

		// Should not replay itself
		cards.removeIf(c ->
				Objects.equal(source, c)
						|| Objects.equal(source.getSourceCard(), c)
						|| Objects.equal(source.getId(), c.getAttribute(Attribute.COPIED_FROM))
						|| Objects.equal(source.getSourceCard().getId(), c.getAttribute(Attribute.COPIED_FROM)));
		// Cards can only be selected to be played randomly once per sequence.
		List<EntityReference> randomlyPlayedList = EnvironmentEntityList.getList(context).getReferences(context, source);
		if (!randomlyPlayedList.isEmpty()) {
			// Already invoking this card's effect.
			return;
		}

		// TODO: While invoking play cards randomly, should not play a play cards randomly. Use an environment stack.
		player.setAttribute(Attribute.RANDOM_CHOICES);

		// Replay
		for (int i = 0; i < cards.size(); i++) {
			Card card = cards.get(i);
			// TODO: Move the card temporarily to the set aside zone, so that effects apply to it correctly?

			if (card.equals(source)) {
				continue;
			}

			EnvironmentEntityList.getList(context).add(source, card);
			// The card may have been transformed!
			card = (Card) card.transformResolved(context);
			if (SpellUtils.playCardRandomly(context, player, card, source, true, false, source.getEntityType()
					== EntityType.MINION, false, false)) {
				context.getLogic().revealCard(player, card);
				if (desc.getSpell() != null
						&& card.getZone() != Zones.GRAVEYARD
						&& card.getZone() != Zones.REMOVED_FROM_PLAY
						&& !card.getEntityLocation().equals(EntityLocation.UNASSIGNED)) {
					SpellUtils.castChildSpell(context, player, desc.getSpell(), source, target, card);
				}
			}
		}

		EnvironmentEntityList.getList(context).clear(source);
		player.getAttributes().remove(Attribute.RANDOM_CHOICES);
	}

	protected CardList getCards(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		// Retrieve all the cards
		return SpellUtils.getCards(context, player, target, source, desc, Integer.MAX_VALUE);
	}
}

