package controller.game.api;

import java.util.Set;

import model.deck.api.Card;
import model.game.api.Game;
import model.player.api.Action;
import model.player.api.Player;
import view.scenes.GameScene;
import view.View;
import controller.card.CardGetterImage;

/**
 * Interface that models a GameController. 
 * It has a {@link Game}, a {@link GameScene}, a main{@link View} and a {@link CardGetterImage}.
 * It acts as a mediator between the model (Game) and the view (GameScene).
 */
public interface GameController {

    /**
     * Starts the game.
     */
    void startGame();

    /**
     * Converts the {@link Player}'s card set to a list of ImageIcons via its {@link CardGetterImage}
     * and calls the setPlayerCards method in its {@link GameScene}.
     * @param id the player's id.
     * @param cards the set of cards of the player.
     */
    void setPlayerCards(int id, Set<Card> cards);

    /**
     * Converts the set of community cards to a list of ImageIcons via its {@link CardGetterImage}
     * and calls the setCommunityCards method in its {@link GameScene}.
     * @param cards the set of community cards.
     */
    void setCommunityCards(Set<Card> cards);

    /**
     * Calls the setPot method in its {@link GameScene}.
     * @param pot the pot (or the winnings).
     */
    void setPot(int pot);

    /**
     * Calls the setPlayerAction method in a different PlayerPanel in its {@link GameScene} depending on the id.
     * @param id the player's id.
     * @param action the player's action.
     */
    void setPlayerAction(int id, Action action);

    /**
     * Calls the setPlayerBet method in its {@link GameScene}.
     * @param id the player's id.
     * @param bet the player's bet.
     */
    void setPlayerBet(int id, int bet);

    /**
     * Calls the setPlayerChips method in a different PlayerPanel in its {@link GameScene} depending on the id.
     * @param id the player's id.
     * @param chips the player's chips.
     */
    void setPlayerChips(int id, int chips);

    /**
     * Calls the setRole method in every PlayerPanel of its {@link GameScene} depending on 
     * the ids of the smallBlind and bigBlind.
     * @param smallBlindId the small blind player's id.
     * @param bigBlindId the big blind player's id.
     */
    void setRoles(int smallBlindId, int bigBlindId);
    
}
