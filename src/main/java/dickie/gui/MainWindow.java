package dickie.gui;

import dickie.Dickie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 * Manages the display of dialog boxes, user input handling,
 * and interaction with the Dickie chatbot.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Dickie dickie;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dickieImage = new Image(this.getClass().getResourceAsStream("/images/DaDickie.png"));

    /**
     * Initializes the main window.
     * Binds the scroll pane's vertical value to the dialog container's height
     * to enable automatic scrolling as new messages are added.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Dickie instance into this controller.
     * This method is called by Main after the FXML is loaded to provide
     * the controller with access to the chatbot logic.
     *
     * @param d The Dickie chatbot instance
     */
    public void setDickie(Dickie d) {
        dickie = d;
    }

    /**
     * Displays the greeting message from Dickie when the application starts.
     * Adds a dialog box containing the greeting to the dialog container.
     */
    public void showGreeting() {
        String greetingMessage = dickie.getGreeting();
        dialogContainer.getChildren().add(
                DialogBox.getDickieDialog(greetingMessage, dickieImage)
        );
    }

    /**
     * Handles user input when the send button is clicked or Enter is pressed.
     * Creates a dialog box containing Dickie's response to the dialog container.
     * Clears the user input field after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = dickie.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDickieDialog(response, dickieImage)
        );
        userInput.clear();
    }
}
