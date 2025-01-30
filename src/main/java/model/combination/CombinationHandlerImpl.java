package model.combination;

import java.util.Set;

import model.combination.api.Combination;
import model.combination.api.CombinationHandler;
import model.combination.api.CombinationType;
import model.combination.api.CombinationsCardGetter;
import model.combination.api.CombinationsRules;
import model.deck.api.Card;

/**
 * Class that find type of combination.
 */
public class CombinationHandlerImpl implements CombinationHandler<Card> {


    /**
     * {@inheritDoc}
     */
    @Override
    public Combination<Card> getCombination(final Set<Card> totalCardList) {
        CombinationsRules<Card> combRules = new CombinationsRulesImpl(totalCardList);
        CombinationsCardGetter<Card> combGetter = new CombinationsCardGetterImpl(totalCardList);

        Integer tieBreakValue = totalCardList.stream()
                .mapToInt(Card::valueOfCard).sum();

        if (combRules.isRoyalFlush()) {
            return new Combination<>(combGetter.getRoyalFlush(), CombinationType.ROYAL_FLUSH);
        } else if (combRules.isPoker()) {
            return new Combination<>(combGetter.getPoker(), CombinationType.POKER);
        } else if (combRules.isFlush()) {
            return new Combination<>(combGetter.getFlush(), CombinationType.FLUSH);
        } else if (combRules.isFullHouse()) {
            return new Combination<>(combGetter.getFullHouse(), CombinationType.FULL_HOUSE);
        } else if (combRules.isStraight()) {
            return new Combination<>(combGetter.getStraight(), CombinationType.STRAIGHT);
        } else if (combRules.isTris()) {
            return new Combination<>(combGetter.getTris(), CombinationType.TRIS);
        } else if (combRules.isTwoPairs()) {
            return new Combination<>(combGetter.getTwoPairs(), CombinationType.TWO_PAIRS);
        } else if (combRules.isPair()) {
            return new Combination<>(combGetter.getPair(), CombinationType.PAIR);
        } else {
            return new Combination<>(totalCardList, CombinationType.HIGH_CARD);
        }

    }

}
