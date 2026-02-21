package dickie.task;

import dickie.exception.DickieException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Factory class responsible for parsing user input and constructing Task objects.
 * Supports creation of Todo, Deadline, and Event tasks.
 * Each factory method validates that required markers exist and appear in the correct order,
 * that no required fields are left blank, that dates follow YYYY-MM-DD format,
 * and that event start dates are before end dates.
 */
public class TaskFactory {

    /**
     * Creates a Todo task from user input.
     *
     * @param input Raw user input string
     * @return A new Todo task
     * @throws DickieException If the input is missing required markers, has blank fields,
     *                         or contains an invalid priority value
     */
    public static Task createTodo(String input) throws DickieException {
        int pIndex = requireIndex(input, "/p",
                         "try again! use \"/p\" to indicate the priority of this task!");

        String name = extractSegment(
                input,
                "todo".length(),
                pIndex,
                "try again, no task indicated to do!"
        );
        Priority priority = parsePriority(input, pIndex);
        return new Todo(name, priority);
    }

    /**
     * Creates a Deadline task from user input.
     *
     * @param input Raw user input string
     * @return A new Deadline task
     * @throws DickieException If the input is missing required markers, has blank fields,
     *                         contains an invalid date format, or has markers in the wrong order
     */
    public static Task createDeadline(String input) throws DickieException {
        int byIndex = requireIndex(input, "/by",
                          "try again! use \"/by\" to indicate the deadline of this task!");

        int pIndex = requireIndex(input, "/p",
                         "try again! use \"/p\" to indicate the priority!");

        if (byIndex > pIndex) {
            throw new DickieException("invalid format! \"/by\" should come before \"/p\"!");
        }

        String name = extractSegment(
                input,
                "deadline".length(),
                byIndex,
                "try again, no task indicated to do!"
        );

        String deadline = extractSegment(
                input,
                byIndex + "/by".length(),
                pIndex,
                "try again! enter a deadline date!"
        );

        validateDate(deadline,
                "try again! Deadline must be in YYYY-MM-DD format!");

        Priority priority = parsePriority(input, pIndex);
        return new Deadline(name, deadline, priority);
    }

    /**
     * Creates an Event task from user input.
     *
     * @param input Raw user input string
     * @return A new Event task
     * @throws DickieException If the input is missing required markers, has blank fields,
     *                         contains an invalid date format, has markers in the wrong order,
     *                         or has a start date after the end date
     */
    public static Task createEvent(String input) throws DickieException {
        int fromIndex = requireIndex(input, "/from",
                            "try again! use \"/from\" to indicate the start of the event!");

        int toIndex = requireIndex(input, "/to",
                          "try again! use \"/to\" to indicate the end of the event!");

        int pIndex = requireIndex(input, "/p",
                         "try again! use \"/p\" to indicate the priority!");

        if (!(fromIndex < toIndex && toIndex < pIndex)) {
            throw new DickieException("invalid format! use \"/from\" before \"/to\" before \"/p\"!");
        }

        String name = extractSegment(
                input,
                "event".length(),
                fromIndex,
                "try again, no task indicated to do!"
        );

        String from = extractSegment(
                input,
                fromIndex + "/from".length(),
                toIndex,
                "try again! enter an event start date!"
        );

        String to = extractSegment(
                input,
                toIndex + "/to".length(),
                pIndex,
                "try again! enter an event end date!"
        );

        validateDate(from, "try again! Event dates must be in YYYY-MM-DD format!");
        validateDate(to,"try again! Event dates must be in YYYY-MM-DD format!");

        if (LocalDate.parse(from).isAfter(LocalDate.parse(to))) {
            throw new DickieException("try again! event start date should be before end date!");
        }

        Priority priority = parsePriority(input, pIndex);
        return new Event(name, from, to, priority);
    }

    /**
     * Extracts and trims a substring between two indices.
     *
     * @param input Raw user input string
     * @param startIndex Start index of the segment to extract
     * @param endIndex End index of the segment to extract
     * @param blankError Error message to throw if the extracted segment is blank
     * @return Trimmed substring between the two indices
     * @throws DickieException If the extracted segment is blank or the indices are invalid
     */
    private static String extractSegment(String input, int startIndex, int endIndex, String blankError)
            throws DickieException {

        if (startIndex >= endIndex) {
            throw new DickieException("invalid command format!");
        }

        String result = input.substring(startIndex, endIndex).trim();
        if (result.isBlank()) {
            throw new DickieException(blankError);
        }
        return result;
    }

    /**
     * Ensures a marker exists in the input string and returns its index.
     *
     * @param input Raw user input string
     * @param marker The marker substring to search for (e.g. "/p", "/by")
     * @param errorMsg Error message to throw if the marker is not found
     * @return Index of the marker within the input string
     * @throws DickieException If the marker is not found in the input
     */
    private static int requireIndex(String input,
                                    String marker,
                                    String errorMsg)
            throws DickieException {

        int index = input.indexOf(marker);
        if (index == -1) {
            throw new DickieException(errorMsg);
        }
        return index;
    }

    /**
     * Parses and validates the priority value from user input.
     *
     * @param input Raw user input string
     * @param pIndex Index of the "/p" marker within the input
     * @return The corresponding Priority enum value
     * @throws DickieException If the priority field is blank or does not match LOW, MEDIUM, or HIGH
     */
    private static Priority parsePriority(String input, int pIndex)
            throws DickieException {

        String value = input.substring(pIndex + "/p".length())
                .trim()
                .toUpperCase();

        if (value.isBlank()) {
            throw new DickieException(
                    "indicate the priority! Example: LOW, MEDIUM or HIGH");
        }

        return switch (value) {
            case "LOW" -> Priority.LOW;
            case "MEDIUM" -> Priority.MEDIUM;
            case "HIGH" -> Priority.HIGH;
            default -> throw new DickieException(
                    "invalid priority syntax, try LOW, MEDIUM or HIGH!");
        };
    }

    /**
     * Validates that a date string follows the ISO format YYYY-MM-DD.
     *
     * @param date The date string to validate
     * @param errorMsg Error message to throw if the date format is invalid
     * @throws DickieException If the date string cannot be parsed as a valid ISO date
     */
    private static void validateDate(String date, String errorMsg)
            throws DickieException {
        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new DickieException(errorMsg);
        }
    }
}