package model.combination;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

import model.combination.api.Combination;
import model.combination.api.CombinationUtilities;
import model.deck.api.Card;

/**
 * Class that compare two combination to decretate the winner.
 * To return values can be read {@link Comparator}.
 */
public class CombinationComparator implements Comparator<Combination<Card>> ,Serializable{
    private final CombinationUtilities utilies = new CombinationUtilitiesImpl();

    /**
     * Method to compare two combination.
     * 
     * @param firstCombination First {@link Combination} of {@link Card} to be
     *                          comparable.
     * @param secondCombination Second {@link Combination} of
     *                          {@link Card} to be comparable.
     * @return 0 if they are equals, 1 if first is bigger, -1 if second is bigger.
     */
    @Override
    public int compare(final Combination<Card> firstCombination, final Combination<Card> secondCombination) {

        final int returnValue = Integer.compare(firstCombination.type().getValue(),
                secondCombination.type().getValue());

        if (returnValue == 0) {
            switch (firstCombination.type()) {
                case TWO_PAIRS:
                    return twoPairCompair(firstCombination.combinationCard(), secondCombination.combinationCard());
                case FULL_HOUSE:
                    try {
                        return Integer.compare(sumValueCard(getTrisFromCombination(firstCombination)),
                                sumValueCard(getTrisFromCombination(secondCombination)));
                    } catch (IllegalAccessException e) {
                        System.out.println("Tris not present in combination"); // NOPMD suppressed as it is a false
                                                                               // positive
                    }
                default:
                    return Integer.compare(sumValueCard(firstCombination.combinationCard()),
                            sumValueCard(secondCombination.combinationCard()));
            }
        }
        return returnValue;

    }

    /**
     * Method to sum value of Card set.
     * 
     * @param combinationCard Cards that be summed its values.
     * @return sum of card's value
     */
    private Integer sumValueCard(final Set<Card> combinationCard) {
        return combinationCard.stream().mapToInt(Card::valueOfCard).sum();
    }

    /**
     * Method to get the tris from a combination.
     * 
     * @param combination Set of card to be extract only tris combination.
     * @return Set of card that represent the tris.
     * @throws IllegalAccessException if was passed like argument combination that
     *                                not conteins tris combination.
     */
    private Set<Card> getTrisFromCombination(final Combination<Card> combination) throws IllegalAccessException {
        if (new CombinationRulesImpl(combination.combinationCard(), utilies).isTris()) {
            return new CombinationCardGetterImpl(combination.combinationCard(), utilies).getTris();
        } else {
            throw new IllegalAccessException();
        }
    }

    /**
     * Method to compare copple of two-pair and decretate winner.
     * Win copple with the highter pair , if are equal must compare the other pair.
     * if either are equal is parity.
     * 
     * @param firstList Set of card of firstCombination.
     * @param secondList Set of Card of secondCombination
     * @return 0 if they are equals, 1 if first is bigger, -1 if second is bigger.
     */
    private Integer twoPairCompair(final Set<Card> firstList, final Set<Card> secondList) {

        final var valueFirstList = firstList.stream().map(Card::valueOfCard)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        final var valueSecondList = secondList.stream().map(Card::valueOfCard)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        if (valueFirstList.getLast().equals(valueSecondList.getLast())) {
            if (valueFirstList.getFirst().equals(valueSecondList.getFirst())) {
                return 0;
            }
            return Integer.compare(valueFirstList.getFirst(), valueSecondList.getFirst());
        }
        return Integer.compare(valueFirstList.getLast(), valueSecondList.getLast());
    }
}
