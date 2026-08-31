package xian;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Starts the JavaFX user interface for the Xian task management application.
 */
public class Main extends Application {

    /**
     * Creates and displays the main Xian window.
     *
     * @param stage The primary JavaFX stage provided by the application.
     */
    @Override
    public void start(Stage stage) {
        MainWindow mainWindow = new MainWindow(stage);
        mainWindow.show();
    }
}
