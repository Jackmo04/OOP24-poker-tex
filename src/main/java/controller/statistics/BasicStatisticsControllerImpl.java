package controller.statistics;

import java.util.List;
import org.apache.commons.lang3.tuple.ImmutablePair;

import controller.scene.SceneControllerImpl;
import model.combination.api.CombinationType;
import model.statistics.BasicStatisticsImpl;
import model.statistics.StatisticsManagerImpl;
import model.statistics.api.BasicStatistics;
import view.View;

/**
 * Implementation of the StatsController interface.
 * Manages the retrieval of the statistics form the statistics manager and the
 * return to the main menu scene.
 */
public class BasicStatisticsControllerImpl extends SceneControllerImpl implements StatisticsController {

    private static final String STATS_FILE_NAME = "stats.bin";

    /**
     * Constructor for the StatsControllerImpl class.
     * @param mainView The main view of the application.
     */
    public BasicStatisticsControllerImpl(final View mainView) {
        super(mainView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ImmutablePair<String, String>> getStatistics() {
        final var statsManager = new StatisticsManagerImpl<>(STATS_FILE_NAME, new BasicStatisticsImpl());
        return this.getAsList(statsManager.getTotalStatistics());
    }

    private List<ImmutablePair<String, String>> getAsList(final BasicStatistics stats) {
        return List.of(
            new ImmutablePair<>("Hands played", String.valueOf(stats.getNumOfHandsPlayed())),
            new ImmutablePair<>("Hands won", String.valueOf(stats.getNumOfHandsWon())),
            new ImmutablePair<>("Games played", String.valueOf(stats.getNumOfGamesPlayed())),
            new ImmutablePair<>("Games won", String.valueOf(stats.getNumOfGamesWon())),
            new ImmutablePair<>("Best Combination", stats.getBestCombination().map(CombinationType::getName).orElse("None")),
            new ImmutablePair<>("Biggest win", stats.getBiggestWin() + " chips"),
            new ImmutablePair<>("Hands win rate", String.format("%.2f%%", stats.getHandWinRate())),
            new ImmutablePair<>("Games win rate", String.format("%.2f%%", stats.getGameWinRate()))
        );
    }
}
