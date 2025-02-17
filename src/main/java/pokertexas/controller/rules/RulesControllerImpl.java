package pokertexas.controller.rules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import pokertexas.controller.scene.SceneControllerImpl;
import pokertexas.view.View;

/**
 * Implementation of the {@link RulesController} interface.
 */
public class RulesControllerImpl extends SceneControllerImpl implements RulesController {

    private static final String HTML_FILE_PATH = "rules/rules.html";

    /**
     * Creates a new rules controller.
     * @param mainView the main view of the application
     */
    public RulesControllerImpl(final View mainView) {
        super(mainView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRulesHtml() {
        final InputStream is = ClassLoader.getSystemResourceAsStream(HTML_FILE_PATH);
        if (is == null) {
            throw new IllegalArgumentException("File not found: " + HTML_FILE_PATH);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            final var html = reader.lines().collect(Collectors.joining("\n"));
            return correctImagesURLs(html);
        } catch (IOException e) {
            return "<html><body><p>Errore nel caricamento del file HTML</p></body></html>";
        }
    }

    private String correctImagesURLs(final String html) {
        return html.replaceAll("images/", ClassLoader.getSystemResource("rules/images/").toString());
    }

}
