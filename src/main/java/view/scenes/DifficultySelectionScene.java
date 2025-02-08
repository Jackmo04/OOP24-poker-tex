package view.scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.difficulty.DifficultySelectionController;
import controller.game.api.Difficulty;
import view.scenes.api.Scene;

/**
 * The DifficultySelectionScene class represents the scene where the user can select 
 * the game difficulty and initial chips.
 * It provides radio buttons for selecting the difficulty level 
 * and a text field for entering the initial number of chips.
 * The user can proceed to the game scene by pressing the "Play" button.
 */
public class DifficultySelectionScene implements Scene {

    private static final int R_BUTTONS_PANEL = 236;
    private static final int G_BUTTONS_PANEL = 205;
    private static final int B_BUTTONS_PANEL = 153;
    private static final int R_BORDER = 0;
    private static final int G_BORDER = 0;
    private static final int B_BORDER = 0;
    private static final int A_BORDER = 50;
    private static final int FONT_SIZE_TITLE = 50; 
    private static final int FONT_SIZE_LABEL = 22; 
    private static final int FONT_SIZE_BUTTON = 22; 
    private static final int TEXT_FIELD_SIZE = 15; 
    private static final int THICKNESS = 2;
    private static final int R_BACKGROUND = 220;
    private static final int G_BACKGROUND = 186;
    private static final int B_BACKGROUND = 133;
    private static final int R_INPUT_PANEL = 236;
    private static final int G_INPUT_PANEL = 230;
    private static final int B_INPUT_PANEL = 208;
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 50;
    private static final String FONT = "Roboto";
    private static final String SCENE_NAME = "difficulty selection";

    private final DifficultySelectionController controller;
    private final JPanel diffSelPanel;
    private static final Logger LOGGER = LoggerFactory.getLogger(DifficultySelectionScene.class);

    /**
     * Constructs a new DifficultySelectionScene.
     * @param controller the controller that handles the difficulty selection logic.
     */
    public DifficultySelectionScene(final DifficultySelectionController controller) { 
        this.diffSelPanel = new JPanel(new BorderLayout());
        this.controller = controller;
        initialize();
    }

    private void initialize() {
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(R_BACKGROUND, G_BACKGROUND, B_BACKGROUND));

        final JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.setBackground(new Color(R_BACKGROUND, G_BACKGROUND, B_BACKGROUND));

        final JLabel title = new JLabel("DIFFICULTY");
        title.setFont(new Font(FONT, Font.BOLD, FONT_SIZE_TITLE));
        titlePanel.add(title);

        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        final JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 1));

        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 1));
        buttonsPanel.setBackground(new Color(R_BACKGROUND, G_BACKGROUND, B_BACKGROUND));

        final JRadioButton easy = new JRadioButton("EASY");
        easy.setFont(new Font(FONT, Font.BOLD, FONT_SIZE_BUTTON));
        easy.setHorizontalAlignment(SwingConstants.CENTER);

        final JRadioButton medium = new JRadioButton("MEDIUM");
        medium.setFont(new Font(FONT, Font.BOLD, FONT_SIZE_BUTTON));
        medium.setHorizontalAlignment(SwingConstants.CENTER);

        final JRadioButton hard = new JRadioButton("HARD");
        hard.setFont(new Font(FONT, Font.BOLD, FONT_SIZE_BUTTON));
        hard.setHorizontalAlignment(SwingConstants.CENTER);

        final ActionListener difficultyListener = e -> {
            final JRadioButton source = (JRadioButton) e.getSource();
            switch (source.getText()) {
                case "EASY" -> this.controller.setDifficulty(Difficulty.EASY);
                case "MEDIUM" -> this.controller.setDifficulty(Difficulty.MEDIUM);
                case "HARD" -> this.controller.setDifficulty(Difficulty.HARD);
                default -> {
                    LOGGER.info("Invalid difficulty, default: EASY");
                    this.controller.setDifficulty(Difficulty.EASY);
                }
            }
        };

        easy.addActionListener(difficultyListener);
        medium.addActionListener(difficultyListener);
        hard.addActionListener(difficultyListener);

        final ButtonGroup group = new ButtonGroup();
        group.add(easy);
        group.add(medium);
        group.add(hard);

        buttonsPanel.add(easy);
        buttonsPanel.add(medium);
        buttonsPanel.add(hard);

        final JPanel initialChipsPanel = new JPanel();
        initialChipsPanel.setLayout(new GridLayout(2, 1));

        final JLabel initialChipsLabel = new JLabel("How many chips do you want to start with?");
        initialChipsLabel.setFont(new Font(FONT, Font.BOLD, FONT_SIZE_LABEL));
        initialChipsLabel.setBackground(new Color(R_BACKGROUND, G_BACKGROUND, B_BACKGROUND));
        initialChipsLabel.setOpaque(true);
        initialChipsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        final JTextField input = new JTextField("", TEXT_FIELD_SIZE);
        input.setFont(new Font(FONT, Font.BOLD, FONT_SIZE_LABEL));
        input.setBackground(new Color(R_INPUT_PANEL, G_INPUT_PANEL, B_INPUT_PANEL));
        input.addActionListener(e -> {
            this.controller.setInitialChips(Integer.parseInt(input.getText()));
        });
        input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), 
            BorderFactory.createLineBorder(new Color(R_BORDER, G_BORDER, B_BORDER, A_BORDER), THICKNESS, true)));

        initialChipsPanel.add(initialChipsLabel);
        initialChipsPanel.add(input);

        final JPanel playPanel = new JPanel();
        playPanel.setLayout(new FlowLayout());
        playPanel.setBackground(new Color(R_BACKGROUND, G_BACKGROUND, B_BACKGROUND));

        final DiffSelButton play = new DiffSelButton("PLAY");

        play.getButton().addActionListener(e -> {
            this.controller.goToGameScene();
        });

        playPanel.add(play.getButton());

        inputPanel.add(buttonsPanel);
        inputPanel.add(initialChipsPanel);
        inputPanel.add(playPanel);

        centerPanel.add(titlePanel);
        centerPanel.add(inputPanel);
        mainPanel.add(centerPanel);
        this.diffSelPanel.add(mainPanel, BorderLayout.CENTER);

        final DiffSelButton backButton = new DiffSelButton("Back to Menu");
        backButton.getButton().addActionListener(e -> this.controller.goToMainMenuScene());
        this.diffSelPanel.add(backButton.getButton(), BorderLayout.SOUTH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel getPanel() {
        final var wrapper = new JPanel(new BorderLayout());
        wrapper.add(this.diffSelPanel, BorderLayout.CENTER);
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
     * Custom button class for the DifficultySelectionScene.
     * This class extends JButton and provides a style for buttons in this scene.
     */
    private static class DiffSelButton {

        private final JButton button;

        DiffSelButton(final String text) {
            button = new JButton(text);
            initializeButton();
        }

        private void initializeButton() {
            this.button.setBackground(new Color(R_BUTTONS_PANEL, G_BUTTONS_PANEL, B_BUTTONS_PANEL));
            this.button.setForeground(Color.BLACK);
            this.button.setFont(new Font(FONT, Font.BOLD, FONT_SIZE_BUTTON));
            this.button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), 
                BorderFactory.createLineBorder(new Color(R_BORDER, G_BORDER, B_BORDER, A_BORDER), THICKNESS, true)));
            this.button.setOpaque(true);
            this.button.setContentAreaFilled(true);
            this.button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        }

        public JButton getButton() {
            return this.button;
        }
    }
}
