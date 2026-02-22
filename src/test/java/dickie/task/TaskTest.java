package dickie.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// AI-assisted: Used ChatGPT to design comprehensive behavioural unit tests for the Task abstract class.
// Identified testable public behaviours, edge cases, and comparison logic based strictly on the class API.
// All tests were reviewed and adapted to follow project constraints (Java 17, JUnit 5, no private method testing).
class TaskTest {

    /**
     * Minimal concrete implementation for testing abstract Task.
     */
    private static class TestTask extends Task {

        public TestTask(String description, Priority priority) {
            super(description, priority);
        }

        public TestTask(String description, Priority priority, boolean isDone) {
            super(description, priority, isDone);
        }

        @Override
        public String toFileString() {
            return "TEST";
        }
    }

    @Test
    void constructor_defaultIsNotDone() {
        Task task = new TestTask("Test task", Priority.LOW);

        assertEquals("[ ]", task.getStatusIcon());
        assertEquals(" ", task.getFileStatusIcon());
    }

    @Test
    void constructor_withIsDoneTrue_setsDone() {
        Task task = new TestTask("Test task", Priority.LOW, true);

        assertEquals("[X]", task.getStatusIcon());
        assertEquals("X", task.getFileStatusIcon());
    }

    @Test
    void getDescription_returnsCorrectDescription() {
        Task task = new TestTask("My task", Priority.MEDIUM);

        assertEquals("My task", task.getDescription());
    }

    @Test
    void toString_returnsDescription() {
        Task task = new TestTask("My task", Priority.MEDIUM);

        assertEquals("My task", task.toString());
    }

    @Test
    void mark_setsTaskToDone() {
        Task task = new TestTask("Task", Priority.LOW);

        task.mark();

        assertEquals("[X]", task.getStatusIcon());
        assertEquals("X", task.getFileStatusIcon());
    }

    @Test
    void unmark_setsTaskToNotDone() {
        Task task = new TestTask("Task", Priority.LOW, true);

        task.unmark();

        assertEquals("[ ]", task.getStatusIcon());
        assertEquals(" ", task.getFileStatusIcon());
    }

    @Test
    void mark_whenAlreadyMarked_remainsDone() {
        Task task = new TestTask("Task", Priority.LOW, true);

        task.mark();

        assertEquals("[X]", task.getStatusIcon());
    }

    @Test
    void unmark_whenAlreadyUnmarked_remainsNotDone() {
        Task task = new TestTask("Task", Priority.LOW);

        task.unmark();

        assertEquals("[ ]", task.getStatusIcon());
    }

    @Test
    void getPriorityString_formatsCorrectly() {
        Task task = new TestTask("Task", Priority.HIGH);

        assertEquals("[HIGH]", task.getPriorityString());
    }

    @Test
    void compareTo_higherPriorityComesFirst() {
        Task high = new TestTask("High", Priority.HIGH);
        Task low = new TestTask("Low", Priority.LOW);

        assertTrue(high.compareTo(low) < 0);
    }

    @Test
    void compareTo_lowerPriorityComesAfter() {
        Task high = new TestTask("High", Priority.HIGH);
        Task low = new TestTask("Low", Priority.LOW);

        assertTrue(low.compareTo(high) > 0);
    }

    @Test
    void compareTo_equalPriority_returnsZero() {
        Task t1 = new TestTask("Task1", Priority.MEDIUM);
        Task t2 = new TestTask("Task2", Priority.MEDIUM);

        assertEquals(0, t1.compareTo(t2));
    }
}