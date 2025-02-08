package view.gameScenePanels;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import view.gameScenePanels.api.MyLabel;

/**
 * Class that models an AIPlayer panel. 
 * It implements the PlayerPanel interface.
 */
public class AIPlayerPanel extends PlayerPanelImpl{

    private static final int PLAYER_IMAGE_WIDTH = 50;
    private static final int PLAYER_IMAGE_HEIGHT = 50;
    private static final int BORDER_THICKNESS = 5;
    private static final String HAT_PATH = "src/main/resources/hat.png";

    private final MyLabel playerImage;
    private final JPanel imagesPanel;
    private final JPanel dataPanel;
    private final JPanel mainPanel;
    
    /**
     * Constructor for the AIPlayerPanel class.
     */
    public AIPlayerPanel() {
        super();

        /*Sets the cardsPanel background*/
        this.getCardsPanel().setBackground(Color.LIGHT_GRAY);

        /*Creation of the player image label*/
        this.playerImage = new MyLabel("");
        playerImage.setSize(PLAYER_IMAGE_WIDTH, PLAYER_IMAGE_HEIGHT);
        playerImage.setImageFromPath(HAT_PATH);
        this.playerImage.setEnabled(false);

        /*Creation of imagesPanel, it contains the player image and his set of cards*/
        this.imagesPanel = new JPanel();
        imagesPanel.setLayout(new BoxLayout(imagesPanel, BoxLayout.X_AXIS));
        imagesPanel.add(playerImage);
        imagesPanel.add(this.getCardsPanel());
        imagesPanel.setBackground(Color.LIGHT_GRAY);

        /*Creation of dataPanel, it contains the basic data of a player*/
        this.dataPanel = new JPanel();
        dataPanel.add(this.getPlayerAction());
        dataPanel.add(this.getPlayerChips());
        dataPanel.add(this.getPlayerRole());
        
        dataPanel.setBackground(Color.LIGHT_GRAY);

        /*Adding panels to the main one*/
        this.mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(imagesPanel);
        mainPanel.add(dataPanel);
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setOpaque(true);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, BORDER_THICKNESS, true));

        this.setLayout(new GridBagLayout());
        this.add(mainPanel);

    }

    /**
     * Method that set the playerImage enabled or disabled depending on isTurn.
     * @param isTurn the boolean value that says whether it is the player's turn.
     */
    public void updateState(boolean isTurn) {
        this.playerImage.setEnabled(isTurn);
    }

}
