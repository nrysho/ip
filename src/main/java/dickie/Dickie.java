package dickie;

import dickie.command.CommandType;
import dickie.command.CommandParser;
import dickie.exception.DickieException;
import dickie.task.*;
import dickie.utils.Storage;
import dickie.utils.TaskList;
import dickie.utils.Ui;

import java.util.*;

/**
 * Entry point of the dickie task management application
 * Handles user input and coordinates command execution
 */
public class Dickie {
    public static void main(String[] args) {
        Storage storage = new Storage("./data/dickie.txt");
        Ui ui = new Ui();

        // Load tasks from file on startup
        ArrayList<Task> loadedTasks = storage.load();

        // put loaded tasks into a taskList object
        TaskList taskList = new TaskList(loadedTasks);

        ui.showGreeting();

        final Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                break;
            }

            try {
                CommandParser.handleInput(input, taskList, ui);
            } catch (DickieException e) {
                System.out.println(e.getMessage());
            }
        }

        storage.save(taskList);
        ui.showGoodbye();
    }
}
