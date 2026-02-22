package dickie.task;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AI-assisted: Used ChatGPT to systematically identify observable behaviours,
 * edge cases, and failure scenarios for the Deadline class.
 *
 * I reviewed and refined the generated tests to ensure:
 * - Only public behaviour is tested
 * - No private methods or implementation details are relied upon
 * - Assertions verify observable outputs only
 * - Expected outputs align with Task superclass behaviour
 */
class DeadlineTest {

    @Test
    void toString_notDoneTask_correctFormatting() {
        Deadline deadline = new Deadline("submit report",
                "2024-06-30",
                Priority.HIGH);

        String result = deadline.toString();

        assertTrue(result.contains("[D]"));
        assertTrue(result.contains("submit report"));
        assertTrue(result.contains("Jun 30 2024"));
        assertTrue(result.contains("HIGH"));
    }

    @Test
    void toString_doneTask_showsDoneStatus() {
        Deadline deadline = new Deadline("submit report",
                "2024-06-30",
                Priority.MEDIUM,
                true);

        String result = deadline.toString();

        assertTrue(result.contains("[D]"));
        assertTrue(result.contains("submit report"));
        assertTrue(result.contains("Jun 30 2024"));
        assertTrue(result.contains("MEDIUM"));
        assertTrue(result.contains("X")); // assuming done icon is X
    }

    @Test
    void toString_singleDigitDay_formatsWithoutLeadingZero() {
        Deadline deadline = new Deadline("task",
                "2024-06-03",
                Priority.LOW);

        String result = deadline.toString();

        assertTrue(result.contains("Jun 3 2024"));
    }

    @Test
    void toString_invalidDate_throwsException() {
        Deadline deadline = new Deadline("task",
                "invalid-date",
                Priority.LOW);

        assertThrows(DateTimeParseException.class, deadline::toString);
    }

    @Test
    void toFileString_notDone_correctFormat() {
        Deadline deadline = new Deadline("submit report",
                "2024-06-30",
                Priority.HIGH);

        String result = deadline.toFileString();

        assertEquals("D |   | submit report | 2024-06-30 | HIGH", result);
    }

    @Test
    void toFileString_done_correctFormat() {
        Deadline deadline = new Deadline("submit report",
                "2024-06-30",
                Priority.HIGH,
                true);

        String result = deadline.toFileString();

        assertEquals("D | X | submit report | 2024-06-30 | HIGH", result);
    }
}