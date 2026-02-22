package dickie.task;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AI-assisted: Used ChatGPT to systematically identify observable behaviours,
 * edge cases, and failure scenarios for the Event class.
 *
 * I reviewed and refined the generated tests to ensure:
 * - Only public behaviour is tested
 * - No private methods or implementation details are relied upon
 * - Expected outputs match the actual Task superclass behaviour
 */
public class EventTest {
    @Test
    public void constructor_defaultIsDone_taskNotMarkedDone() {
        Event event = new Event(
                "Project meeting",
                "2024-06-01",
                "2024-06-02",
                Priority.LOW
        );

        String output = event.toString();

        assertTrue(output.contains("[E]"));
        assertFalse(output.contains("[X]")); // assuming X indicates done
    }

    @Test
    public void constructor_withIsDone_true_statusReflectedInToString() {
        Event event = new Event(
                "Project meeting",
                "2024-06-01",
                "2024-06-02",
                Priority.LOW,
                true
        );

        String output = event.toString();

        assertTrue(output.contains("[X]"));
    }

    @Test
    public void toString_validDates_formatsDatesCorrectly() {
        Event event = new Event(
                "Conference",
                "2024-06-01",
                "2024-06-03",
                Priority.MEDIUM
        );

        String output = event.toString();

        assertTrue(output.contains("Jun 1 2024"));
        assertTrue(output.contains("Jun 3 2024"));
    }

    @Test
    public void toString_sameStartAndEndDate_formatsCorrectly() {
        Event event = new Event(
                "One day event",
                "2024-06-01",
                "2024-06-01",
                Priority.HIGH
        );

        String output = event.toString();

        assertTrue(output.contains("Jun 1 2024"));
    }

    @Test
    public void toFileString_notDone_correctFormatReturned() {
        Event event = new Event(
                "Workshop",
                "2024-07-01",
                "2024-07-02",
                Priority.HIGH
        );

        String fileString = event.toFileString();

        assertEquals(
                "E |   | Workshop | 2024-07-01 | 2024-07-02 | HIGH",
                fileString
        );
    }

    @Test
    public void toFileString_done_correctFormatReturned() {
        Event event = new Event(
                "Workshop",
                "2024-07-01",
                "2024-07-02",
                Priority.HIGH,
                true
        );

        String fileString = event.toFileString();

        assertTrue(fileString.startsWith("E | X |"));
    }

    @Test
    public void toString_invalidFromDate_throwsException() {
        Event event = new Event(
                "Invalid event",
                "invalid-date",
                "2024-06-02",
                Priority.LOW
        );

        assertThrows(DateTimeParseException.class, event::toString);
    }

    @Test
    public void toString_invalidToDate_throwsException() {
        Event event = new Event(
                "Invalid event",
                "2024-06-02",
                "not-a-date",
                Priority.LOW
        );

        assertThrows(DateTimeParseException.class, event::toString);
    }
}