import command.CommandType;
import command.CommandParser;
import exception.DickieException;
import task.*;
import utils.Storage;
import utils.TaskList;

import java.io.File;
import java.util.*;

/**
 * Entry point of the Dickie task management application
 * Handles user input and coordinates command execution
 */
public class Dickie {
    public static void main(String[] args) {
        Storage storage = new Storage("./data/dickie.txt");

        // Load tasks from file on startup
        ArrayList<Task> loadedTasks = storage.load();

        // put loaded tasks into a taskList object
        TaskList taskList = new TaskList(loadedTasks);

        greet();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                break;
            }

            try {
                handleInput(input, taskList, storage);
            } catch (DickieException e) {
                System.out.println(e.getMessage());
            }
        }

        storage.save(taskList);
        bye();
    }

    /**
     * Processes the user input and executes the corresponding command.
     *
     * @param input Full user input string
     * @throws DickieException If the input is invalid or cannot be parsed
     */
    public static void handleInput(String input, TaskList taskList, Storage storage) throws DickieException {
        CommandType commandType = CommandParser.getInputCommandType(input, taskList.getSize());
        String[] splitInput = CommandParser.splitInput(input);

        switch (commandType) {
            case LIST:
                taskList.list();
                break;
            case MARK:
                taskList.mark(splitInput[1]);
                break;
            case UNMARK:
                taskList.unmark(splitInput[1]);
                break;
            case DELETE:
                taskList.delete(splitInput[1]);
                break;
            case ADDTASK:
                handleAddTask(input, splitInput, taskList, storage);
                break;
            case FIND:
                taskList.find(splitInput[1]);
                break;
            default:
                throw new DickieException("   unknown command!");
        }
    }

    /**
     * Handles the creation and addition of a new task based on the user input.
     *
     * @param input Full user input string
     * @param splitInput User input split into individual words
     * @throws DickieException If the task type or task details are invalid
     */
    public static void handleAddTask(String input, String[] splitInput, TaskList taskList,
                                     Storage storage) throws DickieException {
        TaskType taskType = CommandParser.getTaskType(splitInput);
        Task newTask;

        switch (taskType) {
            case TODO:
                newTask = createTodo(input);
                break;
            case DEADLINE:
                newTask = createDeadline(input);
                break;
            case EVENT:
                newTask = createEvent(input);
                break;
            default:
                throw new DickieException("   invalid task type!");
        }

        taskList.addTask(newTask);
    }

    /**
     * Creates a new todo task from the user input.
     *
     * @param input Full user input string
     * @return Newly created todo task
     * @throws DickieException If the task description is missing
     */
    public static Task createTodo(String input) throws DickieException {
        String details = CommandParser.getTodoDetails(input);
        return new Todo(details);
    }

    /**
     * Creates a new deadline task from the user input.
     *
     * @param input Full user input string
     * @return Newly created deadline task
     * @throws DickieException If the deadline details are missing or invalid
     */
    public static Task createDeadline(String input) throws DickieException {
        String[] details = CommandParser.getDeadlineDetails(input);
        return new Deadline(details[0], details[1]);
    }

    /**
     * Creates a new event task from the user input.
     *
     * @param input Full user input string
     * @return Newly created event task
     * @throws DickieException If the event duration details are missing or invalid
     */
    public static Task createEvent(String input) throws DickieException {
        String[] details = CommandParser.getEventDetails(input);
        return new Event(details[0], details[1], details[2]);
    }

    /**
     * Prints the greeting message when the program starts.
     */
    public static void greet() {
            String greeting = "   Hey gorlll I'm Dickie\n" +
                    "   What can I do for you?";
            System.out.println(greeting);
        }

    /**
     * Prints the farewell message when the program ends.
     */
    public static void bye() {
        String greeting = "   Byee. See ya~";
        System.out.println(greeting);
    }


}
