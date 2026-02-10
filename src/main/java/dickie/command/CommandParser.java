package dickie.command;

import dickie.exception.DickieException;
import dickie.task.*;
import dickie.utils.TaskList;
import dickie.utils.Ui;

import java.util.ArrayList;


/**
 * Includes methods to parse input commands
 */
public class CommandParser {
    /**
     * Splits the user input into individual words using whitespace as a delimiter.
     *
     * @param input Full user input string
     * @return Array of strings obtained by splitting the input
     */
    public static String[] splitInput(String input) {
        return input.split("\\s+");
    }

    /**
     * Processes the user input and executes the corresponding command.
     *
     * @param input Full user input string
     * @param taskList Tasklist object with full task list
     * @param ui Ui object to handle displayed messages
     * @throws DickieException If the input is invalid or cannot be parsed
     */
    public static String handleInput(String input, TaskList taskList, Ui ui) throws DickieException {
        CommandType commandType = CommandParser.getInputCommandType(input, taskList.getSize());
        String[] splitInput = CommandParser.splitInput(input);

        switch (commandType) {
        case LIST:
            return ui.listTasks(taskList);
        case MARK:
            Task markedTask = taskList.mark(splitInput[1]);
            return ui.showTaskMarked(markedTask.toString());
        case UNMARK:
            Task unmarkedTask = taskList.unmark(splitInput[1]);
            return ui.showTaskUnmarked(unmarkedTask.toString());
        case DELETE:
            Task deletedTask = taskList.delete(splitInput[1]);
            return ui.showTaskDeleted(deletedTask.toString(), taskList.getSize());
        case FIND:
            ArrayList<Task> foundTasks = taskList.find(splitInput[1]);
            return ui.showFoundTasks(foundTasks);
        case ADDTASK:
            return handleAddTask(input, splitInput, taskList, ui);
        default:
            throw new DickieException("   unknown command!");
        }
    }

    /**
     * Determines the type of command based on the user input.
     * Valid commands include list, mark, unmark, delete, find and add task commands.
     *
     * @param input Full user input string
     * @param noOfTasks Current number of tasks in the task list
     * @return The CommandType corresponding to the user input
     * @throws DickieException If the input is empty or contains an invalid task number
     */
    public static CommandType getInputCommandType(String input, int noOfTasks) throws DickieException {

        String[] splitInput = input.split("\\s+");

        if (input.isEmpty()) {
            throw new DickieException("   empty input :( let me know how I can help!");
        }
        if (input.equals("list")) {
            return CommandType.LIST;
        }
        if (splitInput[0].equals("find")) {
            return CommandType.FIND;
        }
        if (splitInput.length == 2 && isNumeric(splitInput[1])
                && (splitInput[0].equals("mark") ||
                splitInput[0].equals("unmark") ||
                splitInput[0].equals("delete"))) {
            int num = Integer.parseInt(splitInput[1]);
            if ((num < 1) || (num > noOfTasks)) {
                throw new DickieException("   invalid task number, try again!");
            }
            if (splitInput[0].equals("mark")) {
                return CommandType.MARK;
            } else if (splitInput[0].equals("unmark")) {
                return CommandType.UNMARK;
            } else { // splitInput[0] equals "delete"
                return CommandType.DELETE;
            }
        }
        return CommandType.ADDTASK;
    }

    /**
     * Checks whether a given string can be parsed as a numeric value.
     *
     * @param input String to be checked
     * @return True if the string is numeric, false otherwise
     */
    public static boolean isNumeric(String input) {
        if (input == null) {
            return false;
        }
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Handles the creation and addition of a new task based on the user input.
     *
     * @param input Full user input string
     * @param splitInput User input split into individual words
     * @param ui Ui object to display messages
     * @throws DickieException If the task type or task details are invalid
     */
    public static String handleAddTask(String input, String[] splitInput, TaskList taskList,
                                     Ui ui) throws DickieException {
        TaskType taskType = CommandParser.getTaskType(splitInput);
        Task newTask = switch (taskType) {
            case TODO -> TaskFactory.createTodo(input);
            case DEADLINE -> TaskFactory.createDeadline(input);
            case EVENT -> TaskFactory.createEvent(input);
        };

        taskList.addTask(newTask);
        return ui.showTaskAdded(newTask, taskList.getSize());
    }

    /**
     * Returns the TaskType of an input.
     * If the input does not start with "todo", "deadline" or "event",
     * throws DickieException with error message.
     *
     * @param splitInput String array of input split by words
     * @return The type of task specified by the user input
     * @throws DickieException If task does not start with "todo", "deadline" or "event"
     */
    public static TaskType getTaskType(String[] splitInput) throws DickieException {
        String taskName = splitInput[0];
        return switch (taskName) {
            case "todo" -> TaskType.TODO;
            case "deadline" -> TaskType.DEADLINE;
            case "event" -> TaskType.EVENT;
            default -> throw new DickieException("   invalid task type, try again! :)");
        };
    }
}
