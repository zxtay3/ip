package xian;

import java.io.IOException;
import java.time.format.DateTimeParseException;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * Represents the main JavaFX window used to interact with Xian.
 */
public class MainWindow {

    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;

    private final Xian xian;
    private final TextArea outputArea;
    private final TextField commandInput;
    private final Stage stage;

    /**
     * Creates the main Xian window and connects its input controls to the backend.
     *
     * @param stage The JavaFX stage on which the window is displayed.
     */
    public MainWindow(Stage stage) {
        this.stage = stage;
        this.xian = new Xian("data/xian.txt");
        this.outputArea = new TextArea();
        this.commandInput = new TextField();

        configureOutputArea();
        configureCommandInput();
        configureStage();
    }

    /**
     * Displays the configured main window.
     */
    public void show() {
        stage.show();
    }

    private void configureOutputArea() {
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
    }

    private void configureCommandInput() {
        commandInput.setPromptText("Enter a command");
        commandInput.setOnAction(event -> handleCommand());
    }

    private void configureStage() {
        Button sendButton = new Button("Send");
        sendButton.setOnAction(event -> handleCommand());

        HBox commandBox = new HBox(10, commandInput, sendButton);
        commandBox.setPadding(new Insets(10));
        HBox.setHgrow(commandInput, Priority.ALWAYS);

        BorderPane root = new BorderPane();
        root.setCenter(outputArea);
        root.setBottom(commandBox);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setTitle("Xian");
        stage.setScene(scene);
    }

    private void handleCommand() {
        String input = commandInput.getText();

        if (input.isBlank()) {
            return;
        }

        try {
            String response = xian.executeCommand(input);
            outputArea.appendText(response + System.lineSeparator());
        } catch (XianException | IOException | NumberFormatException
                | DateTimeParseException exception) {
            outputArea.appendText(exception.getMessage() + System.lineSeparator());
        }

        commandInput.clear();
    }
}
