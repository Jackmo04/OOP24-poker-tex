package view;

import view.scenes.api.Scene;

/**
 * The main view for the application.
 */
public interface View {
    
    /**
     * Changes the scene of the view.
     * @param scene the {@link Scene} to change to
     */
    void changeScene(Scene scene);

    /**
     * Gets the width of the screen.
     * @return the width of the screen
     */
    int getScreenWidth();

    /**
     * Gets the height of the screen.
     * @return the height of the screen
     */
    int getScreenHeight();
}
