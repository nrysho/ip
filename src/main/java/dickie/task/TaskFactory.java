package dickie.task;

import dickie.exception.DickieException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Factory class for creating different types of Task objects from user input.
 * Handles parsing and validation of task creation commands.
 */
public class TaskFactory {
    /**
     * Creates a new todo task from the user input.
     *
     * @param input Full user input string
     * @return Newly created todo task
     * @throws DickieException If the task description is missing
     */
    public static Task createTodo(String input) throws DickieException {
        String details = getTodoDetails(input);
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
        String[] details = getDeadlineDetails(input);
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
        String[] details = getEventDetails(input);
        return new Event(details[0], details[1], details[2]);
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
        String details = message.substring("todo".length()).trim();
        if (details.isBlank()) {
            throw new DickieException("   try again, no task indicated to do!");
        }
        return details;
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

        if (byIndex == -1) {
            throw new DickieException("   try again! use \"/by\" to indicate the deadline of this task!");
        }

        String taskName = input.substring("deadline".length(), byIndex).trim();
        String taskDeadline = input.substring(byIndex + "/by".length()).trim();

        if (taskName.isBlank()) {
            throw new DickieException("   try again! enter the name of the deadline task!");
        }

        if (taskDeadline.isBlank()) {
            throw new DickieException("   try again! enter a deadline date!");
        }

        try {
            LocalDate.parse(taskDeadline);
        } catch (DateTimeParseException e) {
            throw new DickieException("   try again! Deadline must be in YYYY-MM-DD format!");
        }
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
        int fromIndex = input.indexOf("/from");
        int toIndex = input.indexOf("/to");

        if (fromIndex == -1 || toIndex == -1) {
            throw new DickieException("   try again! use \"/from\" and \"/to\" to indicate the duration of the event!");
        }

        if (fromIndex > toIndex) {
            throw new DickieException("   invalid event format! use \"/from\" before \"/to\"!");
        }

        String taskName = input.substring("event".length(), fromIndex).trim();

        if (taskName.isBlank()) {
            throw new DickieException("   try again! enter the name of the event!");
        }

        String from = input.substring(fromIndex + "/from".length(), toIndex).trim();
        String to = input.substring(toIndex + "/to".length()).trim();

        if (from.isBlank() || to.isBlank()) {
            throw new DickieException("   try again! event must have both start and end time!");
        }

        return new String[]{taskName, from, to};
    }
}
