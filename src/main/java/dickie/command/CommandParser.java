package dickie.command;

import dickie.exception.DickieException;
import dickie.task.TaskType;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;


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
     * Returns the details of a todo task.
     * If the input is "todo" with no task, throws DickieException with error message.
     *
     * @param message String message input by user
     * @return Details of todo task
     * @throws DickieException If details of todo task not specified
     */
    public static String getTodoDetails(String message) throws DickieException {
        String details = message.substring(4).trim();
        if (details.isBlank()) {
            throw new DickieException("   try again, no task indicated to do!");
        }
        return details;
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
        if (taskName.equals("todo")) {
            return TaskType.TODO;
        } else if (taskName.equals("deadline")) {
            return TaskType.DEADLINE;
        } else if (taskName.equals("event")) {
            return TaskType.EVENT;
        } else {
            throw new DickieException("   invalid task type, try again! :)");
        }
    }

    /**
     * Extracts the task name and deadline from a deadline command.
     * The deadline must be specified using the "/by" keyword.
     *
     * @param input Full user input string
     * @return String array containing the task name and deadline
     * @throws DickieException If "/by" is missing or no deadline is provided
     */
    public static String[] getDeadlineDetails(String input) throws DickieException {
        int byIndex = input.indexOf("/by");
        String taskDeadline = input.substring(byIndex + 3).trim();
        // if substring " /by " does not exist, or if there are no words after "/by", throw exception
        if (!input.matches(".*\\s" + "/by" + "\\s.*") || taskDeadline.isBlank()) {
            throw new DickieException("   try again! use \"/by\" to indicate the deadline of this task!");
        }
        try {
            LocalDate.parse(taskDeadline);
        } catch (DateTimeParseException e) {
            throw new DickieException("   try again! Deadline must be in YYYY-MM-DD format!");
        }
        String taskName = input.substring(9, byIndex).trim();

        return new String[]{taskName, taskDeadline};
    }

    /**
     * Extracts the task name, start time, and end time from an event command.
     * The duration must be specified using the "/from" and "/to" keywords.
     *
     * @param input Full user input string
     * @return String array containing the task name, start time, and end time
     * @throws DickieException If "/from" or "/to" is missing from the input
     */
    public static String[] getEventDetails(String input) throws DickieException {
        // if substring " /from " or " /to " does not exist, throw exception
        if (!input.matches(".*\\s" + "/from" + "\\s.*") ||
                !input.matches(".*\\s" + "/to" + "\\s.*")) {
            throw new DickieException("   try again! use \"/from\" and \"/to\" to indicate the duration of the event!");
        }
        int fromIndex = input.indexOf("/from");
        int toIndex = input.indexOf("/to");
        String taskName = input.substring(0, fromIndex).trim();
        String from = input.substring(fromIndex + 5, toIndex).trim();
        String to = input.substring(toIndex + 3).trim();

        return new String[]{taskName, from, to};
    }

    /**
     * Determines the type of command based on the user input.
     * Valid commands include list, mark, unmark, delete, and add task commands.
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
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
