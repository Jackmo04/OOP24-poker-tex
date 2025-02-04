package model.player.ai;

import java.util.Random;
import java.util.function.Function;

import model.player.ai.api.AIPlayer;
import model.player.ai.api.AIPlayerFactory;
import model.combination.api.Combination;
import model.combination.api.CombinationType;
import model.game.api.State;

/**
 * Implementation of the {@link AIPlayerFactory} interface.
 * This class provides methods to create AI players with different difficulty
 * levels.
 * The difficulty levels are: easy, medium and hard.
 * The decision-making process of the AI players is based on the
 * {@link Combination} they have.
 * The better the combination, the more likely the AI player is to call or
 * raise.
 * They're also much more likely to raise if no one has betted yet.
 */
public class AIPlayerFactoryImpl implements AIPlayerFactory {

    private static final double EASY_DIFFICULTY_MODIFIER = 0.70;
    private static final double MEDIUM_DIFFICULTY_MODIFIER = 1.00;
    private static final double HARD_DIFFICULTY_MODIFIER = 1.25;
    private static final double EASY_RAISING_FACTOR = 0.50;
    private static final double MEDIUM_RAISING_FACTOR = 1.00;
    private static final double HARD_RAISING_FACTOR = 2.00;

    /**
     * {@inheritDoc}
     */
    @Override
    public AIPlayer easy(final int id, final int initialChips) {
        return standard(id, initialChips, EASY_RAISING_FACTOR, EASY_DIFFICULTY_MODIFIER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AIPlayer medium(final int id, final int initialChips) {
        return standard(id, initialChips, MEDIUM_RAISING_FACTOR, MEDIUM_DIFFICULTY_MODIFIER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AIPlayer hard(final int id, final int initialChips) {
        return standard(id, initialChips, HARD_RAISING_FACTOR, HARD_DIFFICULTY_MODIFIER);
    }

    private AIPlayer standard(final int id, final int initialChips,
            final double raisingFactor, final double difficultyModifier) {
        return custom(id, initialChips, raisingFactor, difficultyModifier,
            type -> switch (type) {
                case HIGH_CARD -> 0.80;
                case PAIR -> 0.90;
                case TWO_PAIRS -> 1.10;
                case TRIS -> 1.60;
                case STRAIGHT -> 1.80;
                case FLUSH -> 1.85;
                case FULL_HOUSE -> 1.90;
                case POKER -> 1.95;
                case ROYAL_FLUSH -> 2.00;
            },
            type -> switch (type) {
                case HIGH_CARD -> 0.01;
                case PAIR -> 0.05;
                case TWO_PAIRS -> 0.10;
                case TRIS -> 0.20;
                case STRAIGHT -> 0.25;
                case FLUSH -> 0.30;
                case FULL_HOUSE -> 0.40;
                case POKER -> 0.60;
                case ROYAL_FLUSH -> 0.80;
            }
        );
    }

    @Override
    public AIPlayer custom(int id, int initialChips,  double raisingFactor, double difficultyModifier,
            Function<CombinationType, Double> callChance, Function<CombinationType, Double> raiseChance) {
        return new AbstractAIPlayer(id, initialChips, raisingFactor) {

            private final Random random = new Random();

            @Override
            protected boolean shouldCall(State currentState) {
                var chance = difficultyModifier * callChance.apply(this.getCombination().type());
                chance = chance * switch (currentState.getHandPhase()) {
                    case PREFLOP -> 1.00;
                    case FLOP -> 0.75;
                    case TURN -> 0.60;
                    case RIVER -> 0.45;
                };
                if (this.getTotalPhaseBet() != 0 && requiredBet(currentState) > this.getTotalPhaseBet() * 1.5) {
                    chance = chance * 0.75;
                }
                return random.nextDouble() < chance;
            }

            @Override
            protected boolean shouldRaise(State currentState) {
                var chance = difficultyModifier * raiseChance.apply(this.getCombination().type());
                if (requiredBet(currentState) == 0) {
                    chance = chance + 0.80;
                }
                return random.nextDouble() < chance;
            }

        };
    }

}
