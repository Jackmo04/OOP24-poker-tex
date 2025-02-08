package model.game.api;

import java.util.List;

import controller.game.api.GameController;
import model.dealer.api.Dealer;
import model.player.api.Player;
import model.player.user.UserPlayer;

/**
 * Interface that models a generic Game.
 * A Game has a {@link GameController}, a {@link Dealer}, a list of {@link Player}s and a {@link State}
 * which must always be updated. 
 * It provides methods to start the game, to check if it's over and if the user player won.
 */
public interface Game {

    /**
     * Returns true if the user Player is the only one still in the game or if he lost, 
     * false otherwise. 
     * @return whether the game is over. 
    */
    boolean isOver();

    /**
     * Returns whether the {@link UserPlayer} won.
     * @return whether the User Player won. 
    */
    boolean isWon();

    /**
     * Starts the game in a new Thread.
    */
    void start();

    /** 
     * Sets the initial list of {@link Player}s.
     * @param initialChips initial amount of chips of players.
     */
    void setInitialPlayers(int initialChips);

    /**
     * Returns the list of {@link Player}s in the game.
     * @return the list of players in the game.
     */
    List<Player> getPlayers();

    /**
     * Returns the game {@link State}.
     * @return the game State.
     */
    State getGameState();

    /**
     * Returns the {@link UserPlayer}.
     * @return the user player.
     */
    UserPlayer getUserPlayer();

}
