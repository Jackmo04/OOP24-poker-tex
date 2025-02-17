package pokertexas.model.deck;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pokertexas.model.deck.api.Deck;
import pokertexas.model.deck.api.DeckBuild;

/**
 * Generic class to manage deck , like create a deck ora shuffled or more to
 * keep card.
 * 
 * @param <X> Generic parameter useful to manage different card.
 */
public class DeckImpl<X> implements Deck<X> {

    private final DeckBuild<X> deckBuilder;
    private List<X> deck;

    /**
     * Create and shuffle new deck.
     * 
     * @param deckBuilder {@link DeckBuild} that permit to generate
     *                    deck.
     */
    public DeckImpl(final DeckBuild<X> deckBuilder) {
        this.deckBuilder = deckBuilder;
        this.deck = deckBuilder.buildDeck();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shuffled() {
        this.deck = deckBuilder.buildDeck();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<X> getSomeCards(final int numberOfCard) {
        if (numberOfCard > deck.size()) {
            throw new IllegalStateException("Keep more Cards than remaing in Deck");
        } else {
            return Stream.iterate(0, t -> t + 1)
                    .limit(numberOfCard)
                    .map(t -> this.deck.removeFirst())
                    .collect(Collectors.toList());
        }

    }

}
