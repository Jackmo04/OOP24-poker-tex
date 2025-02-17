package pokertexas.view.scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import pokertexas.controller.menu.MainMenuController;
import pokertexas.view.scenes.api.Scene;

/**
 * The {@link Scene} that represents the main menu of the game.
 */
public class MainMenuScene implements Scene {

    private static final int COLOR_BUTTONS_PANEL = 0xECCD99;
    private static final int R_BORDER = 0;
    private static final int G_BORDER = 0;
    private static final int B_BORDER = 0;
    private static final int A_BORDER = 50;
    private static final int FONT_SIZE = 30;
    private static final int FONT_SIZE_TITLE = 50;
    private static final int THICKNESS = 4;
    private static final int COLOR_BACKGROUND = 0xDCBA85;
    private static final int BUTTON_WIDTH = 250;
    private static final int BUTTON_HEIGHT = 60;
    private static final int TOP = 2;
    private static final int LEFT = 2;
    private static final int BOTTOM = 2;
    private static final int RIGHT = 2;
    private static final String FONT = "Roboto";
    private static final String SCENE_NAME = "menu";

    private final MainMenuController controller;
    private final JPanel mainMenuPanel;

    /**
     * Creates a new {@link MainMenuScene}.
     * @param controller the controller for the main menu.
     */
    public MainMenuScene(final MainMenuController controller) {
        this.controller = controller;
        this.mainMenuPanel = new JPanel(new BorderLayout());
        initialize();
    }

    /**
     * Initializes the components of the MainMenuScene.
     * Sets up the layout, styles, and event listeners for the components.
     */
    private void initialize() {
        this.mainMenuPanel.setBackground(new Color(COLOR_BACKGROUND));

        final JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(COLOR_BACKGROUND));

        final GridBagConstraints gbc = new GridBagConstraints();

        final JLabel title = new JLabel("MENU", SwingConstants.CENTER);
        title.setFont(new Font(FONT, Font.BOLD, FONT_SIZE_TITLE));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(TOP, LEFT, BOTTOM, RIGHT); 
        mainPanel.add(title, gbc);

        final MenuButton goToStats = new MenuButton("Statistics");
        final MenuButton goToRules = new MenuButton("How to play");
        final MenuButton goToDifficultySelection = new MenuButton("New game");
        final MenuButton exit = new MenuButton("Exit");

        gbc.gridy = 1;
        mainPanel.add(goToDifficultySelection.getButton(), gbc);

        gbc.gridy = 2;
        mainPanel.add(goToRules.getButton(), gbc);

        gbc.gridy = 3;
        mainPanel.add(goToStats.getButton(), gbc);

        gbc.gridy = 4;
        mainPanel.add(exit.getButton(), gbc);

        this.mainMenuPanel.add(mainPanel, BorderLayout.CENTER);

        goToStats.getButton().addActionListener(e -> this.controller.goToStatsScene());
        goToRules.getButton().addActionListener(e -> this.controller.goToRulesScene());
        goToDifficultySelection.getButton().addActionListener(e -> this.controller.goToDifficultySelectionScene());
        exit.getButton().addActionListener(e -> this.controller.exitGame());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel getPanel() {
        final var wrapper = new JPanel(new BorderLayout());
        wrapper.add(this.mainMenuPanel, BorderLayout.CENTER);
        return wrapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSceneName() {
        return SCENE_NAME;
    }

    /**
     * Custom button class for the MainMenuScene.
     */
    private class MenuButton {

        private final JButton button;

        /**
         * Constructs a MenuButton with the specified text.
         * @param text the text to be displayed on the button.
         */
        MenuButton(final String text) {
            this.button = new JButton(text);
            initializeButton();
        }

        /**
         * Initializes the button with specific styles.
         */
        private void initializeButton() {
            this.button.setBackground(new Color(COLOR_BUTTONS_PANEL));
            this.button.setFont(new Font(FONT, Font.BOLD, FONT_SIZE));
            this.button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), 
                            BorderFactory.createLineBorder(new Color(R_BORDER, G_BORDER, B_BORDER, A_BORDER), THICKNESS, true)));
            this.button.setOpaque(true);
            this.button.setContentAreaFilled(true);
            this.button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
            this.button.setFocusable(false);
        }

        /**
         * Gets the JButton associated with this MenuButton.
         * This method returns the JButton component that is styled and initialized
         * by the MenuButton class. 
         * @return the JButton associated with this MenuButton.
         */
        private JButton getButton() {
            return this.button;
        }
    }

}
