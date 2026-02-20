package dickie;

import dickie.command.CommandParser;
import dickie.exception.DickieException;
import dickie.task.*;
import dickie.utils.Storage;
import dickie.utils.TaskList;
import dickie.utils.Ui;

import java.util.*;

/**
 * The main class for the Dickie chatbot application.
 * Dickie is a task management chatbot that helps users manage todos, deadlines, and events.
 * It handles user input, processes commands, and persists tasks to storage.
 */
public class Dickie {
    private final Storage storage;
    private final Ui ui;
    private final TaskList taskList;

    /**
     * Constructs a new Dickie instance.
     * Initializes storage, UI, and loads existing tasks from file.
     * The storage file path is set to "./data/dickie.txt".
     */
    public Dickie() {
        this.storage = new Storage("./data/dickie.txt");
        this.ui = new Ui();

        // Load tasks from file on startup
        ArrayList<Task> loadedTasks = storage.load();
        assert loadedTasks != null : "Loaded tasks should not be null";

        // put loaded tasks into a taskList object
        this.taskList = new TaskList(loadedTasks);
    }

    /**
     * Generates a response to user input.
     * Processes the input command and returns the appropriate response.
     * If the input is "bye", saves all tasks to storage before returning goodbye message.
     *
     * @param input The user's input command as a string
     * @return The response message to be displayed to the user
     */
    public String getResponse(String input) {
        if (input.equals("bye")) {
            storage.save(taskList);
            return ui.showGoodbye();
        }

        try {
            String response = CommandParser.handleInput(input, taskList, ui);
            assert response != null : "Response from CommandParser should not be null";
            return response;
        } catch (DickieException e) {
            return e.getMessage();
        }
    }

    /**
     * Returns the greeting message to be shown when the application starts.
     *
     * @return The greeting message string
     */
    public String getGreeting() {
        return ui.showGreeting();
    }

    /**
     * Main entry point for the command-line interface version of Dickie.
     * Creates a Dickie instance, displays greeting, and enters a loop to process user commands
     * until the user types "bye".
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        Dickie dickie = new Dickie();
        System.out.println(dickie.getGreeting());

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            String response = dickie.getResponse(input);
            assert response != null : "Response from CommandParser should not be null";
            System.out.println(response);
            if (input.equals("bye")) {
                dickie.storage.save(dickie.taskList);
                break;
            }
        }
    }
}
