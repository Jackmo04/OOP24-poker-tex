package model.player.user;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import controller.player.user.UserPlayerController;
import model.combination.CombinationHandlerImpl;
import model.game.api.Phase;
import model.game.api.State;
import model.player.AbstractPlayer;
import model.player.api.Action;
import model.statistics.BasicStatisticsImpl;
import model.statistics.api.BasicStatistics;
import model.statistics.api.StatisticsContributor;

/**
 * Class representing a human player in the game.
 */
public class UserPlayer extends AbstractPlayer implements StatisticsContributor<BasicStatistics> {

    private static final int INITIAL_TOTAL_PHASE_BET = 0;

    private final UserPlayerController controller;
    private Action action;
    private final BasicStatistics statistics;

    /**
     * Constructor for the UserPlayer class.
     * @param id the identifier for the player.
     * @param initialChips the initial amount of chips that the player has.
     */
    public UserPlayer(final int id, final int initialChips) {
        super(id, initialChips);
        this.controller = new UserPlayerController(this);
        this.setTotalPhaseBet(INITIAL_TOTAL_PHASE_BET);
        this.statistics = new BasicStatisticsImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Action getAction(final State currentState) {
        this.controller.setCurrentState(currentState);
        if(this.getCards().size() != 2) {
            throw new IllegalStateException("Player must have 2 cards to play");
        }
        this.updateCombination(currentState);
        if(currentState.getHandPhase() == Phase.PREFLOP && this.getTotalPhaseBet() == 0 && this.getRole().isPresent()) {
            this.setTotalPhaseBet((int) (currentState.getCurrentBet() * this.getRole().get().getMultiplier()));
            this.setChips(this.getChips() - this.getTotalPhaseBet());
            this.action = Action.CALL;
        } else { 
            this.action = this.controller.getUserAction();
            int bet = this.calculateChipsToBet(currentState.getCurrentBet(), this.action);
            this.setChips(this.getChips() - bet);
            this.setTotalPhaseBet(this.getTotalPhaseBet() + bet);
        }
        return this.action;
    }

    /**
     * Updates the player's best combination based on the current state of the game.
     * This method combines the player's cards with the community cards and calculates the best combination.
     * @param currentState the current state of the game, which includes the community cards.
     */
    private void updateCombination(final State currentState) {
        var allCards = Stream.concat(currentState.getCommunityCards().stream(), 
                        this.getCards().stream()).collect(Collectors.toSet());

        var combination = (new CombinationHandlerImpl()).getBestCombination(allCards);
        this.statistics.setBestCombinationIfSo(combination.type());
        this.setCombination(combination); 
    }

    /**
     * Calculates the chips to bet based on the current bet and the action taken by the player.
     * @param currentBet the current bet in the game.
     * @param action the action taken by the player (RAISE, CALL, ALL_IN, FOLD, CHECK).
     * @return the number of chips to bet.
     */
    private int calculateChipsToBet(final int currentBet, final Action action) {
        switch (action) {
            case Action.RAISE -> {
                return this.controller.getRaiseAmount();
            }
            case Action.CALL -> {
                return this.getChips() < (currentBet - this.getTotalPhaseBet()) ? this.getChips() : (currentBet - this.getTotalPhaseBet());
            }
            case Action.ALL_IN -> {
                return this.getChips();
            }
            case Action.FOLD, Action.CHECK -> {
                return 0;
            }
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAI() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handWon(final int winnings) {
        this.setChips(this.getChips() + winnings);
        this.statistics.incrementHandsWon(1);
        this.statistics.setBiggestWinIfSo(winnings);
        this.endHand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handLost() {
        this.endHand();
    }

    /**
     * Ends the current hand for the player.
     * This method resets the player's cards.
     */
    private void endHand() {
        this.setCards(Set.of());
    }

    @Override
    public void updateStatistics(final BasicStatistics stats) {
        stats.append(this.statistics);
        this.statistics.reset();
    }

    public UserPlayerController getController() {
        return controller;
    }
    
}