package dickie.task;

import dickie.exception.DickieException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AI-assisted: Used ChatGPT to help identify comprehensive test categories
 * including happy path, edge case, exception, and boundary scenarios
 * for TaskFactory.
 *
 * The suggestions included validating marker ordering, blank field handling,
 * date parsing edge cases, and priority parsing behaviour.
 *
 * All generated test cases were reviewed, refined, and adapted to strictly
 * match the visible behaviour and exception messages defined in TaskFactory.
 */
class TaskFactoryTest {

    @Test
    void createTodo_validInput_success() throws DickieException {
        Task task = TaskFactory.createTodo("todo read book /p LOW");

        assertInstanceOf(Todo.class, task);
        assertEquals("read book", task.getDescription());
    }
    // Validates: correct parsing of name and priority for Todo

    @Test
    void createDeadline_validInput_success() throws DickieException {
        Task task = TaskFactory.createDeadline(
                "deadline submit report /by 2026-03-01 /p HIGH");

        assertInstanceOf(Deadline.class, task);
        assertEquals("submit report", task.getDescription());
    }

    @Test
    void createEvent_validInput_success() throws DickieException {
        Task task = TaskFactory.createEvent(
                "event hackathon /from 2026-03-01 /to 2026-03-03 /p MEDIUM");

        assertInstanceOf(Event.class, task);
        assertEquals("hackathon", task.getDescription());
    }

    @Test
    void createTodo_priorityLowercase_parsedCorrectly() throws DickieException {
        Task task = TaskFactory.createTodo("todo study /p high");

        assertInstanceOf(Todo.class, task);
    }

    @Test
    void createDeadline_extraSpaces_trimmedCorrectly() throws DickieException {
        Task task = TaskFactory.createDeadline(
                "deadline   submit   /by   2026-03-01   /p   LOW  ");

        assertEquals("submit", task.getDescription());
    }

    @Test
    void createEvent_sameStartAndEndDate_allowed() throws DickieException {
        Task task = TaskFactory.createEvent(
                "event meeting /from 2026-03-01 /to 2026-03-01 /p LOW");

        assertInstanceOf(Event.class, task);
    }

    @Test
    void createTodo_missingPriorityMarker_throwsException() {
        DickieException ex = assertThrows(DickieException.class,
                () -> TaskFactory.createTodo("todo read book"));

        assertEquals(
                "try again! use \"/p\" to indicate the priority of this task!",
                ex.getMessage());
    }

    @Test
    void createDeadline_wrongMarkerOrder_throwsException() {
        DickieException ex = assertThrows(DickieException.class,
                () -> TaskFactory.createDeadline(
                        "deadline submit /p LOW /by 2026-03-01"));

        assertEquals(
                "invalid format! \"/by\" should come before \"/p\"!",
                ex.getMessage());
    }

    @Test
    void createDeadline_invalidDateFormat_throwsException() {
        DickieException ex = assertThrows(DickieException.class,
                () -> TaskFactory.createDeadline(
                        "deadline submit /by 01-03-2026 /p LOW"));

        assertEquals(
                "try again! Deadline must be in YYYY-MM-DD format!",
                ex.getMessage());
    }

    @Test
    void createEvent_startAfterEnd_throwsException() {
        DickieException ex = assertThrows(DickieException.class,
                () -> TaskFactory.createEvent(
                        "event trip /from 2026-03-05 /to 2026-03-01 /p LOW"));

        assertEquals(
                "try again! event start date should be before end date!",
                ex.getMessage());
    }

    @Test
    void createTodo_blankName_throwsException() {
        DickieException ex = assertThrows(DickieException.class,
                () -> TaskFactory.createTodo("todo   /p LOW"));

        assertEquals(
                "try again, no task indicated to do!",
                ex.getMessage());
    }

    @Test
    void createTodo_invalidPriority_throwsException() {
        DickieException ex = assertThrows(DickieException.class,
                () -> TaskFactory.createTodo("todo read /p URGENT"));

        assertEquals(
                "invalid priority syntax, try LOW, MEDIUM or HIGH!",
                ex.getMessage());
    }

    @Test
    void createEvent_startOneDayBeforeEnd_success() throws DickieException {
        Task task = TaskFactory.createEvent(
                "event exam /from 2026-03-01 /to 2026-03-02 /p HIGH");

        assertInstanceOf(Event.class, task);
    }

    @Test
    void createDeadline_minimalValidDate_success() throws DickieException {
        Task task = TaskFactory.createDeadline(
                "deadline test /by 0001-01-01 /p LOW");

        assertInstanceOf(Deadline.class, task);
    }

    @Test
    void createTodo_priorityJustAfterMarker_success() throws DickieException {
        Task task = TaskFactory.createTodo("todo read/p LOW");

        assertInstanceOf(Todo.class, task);
    }
}
