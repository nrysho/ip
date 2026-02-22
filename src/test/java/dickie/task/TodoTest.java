package dickie.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// AI-assisted: Used ChatGPT to identify behavioural test cases for the Todo class.
// Generated edge cases for constructor and output methods (toString and toFileString).
// Focused on format validation, status variations, and field ordering.
// I reviewed all generated tests and adapted them to match my project structure and coding standards.
public class TodoTest {

    @Test
    void constructor_withDescriptionAndPriority_createsTodo() {
        Todo todo = new Todo("read book", Priority.LOW);

        assertNotNull(todo);
    }

    @Test
    void constructor_withIsDone_setsStatusCorrectly() {
        Todo todo = new Todo("read book", Priority.LOW, true);

        String fileString = todo.toFileString();
        assertTrue(fileString.startsWith("T | X |"));
    }

    @Test
    void toString_startsWithT() {
        Todo todo = new Todo("read book", Priority.LOW);

        assertTrue(todo.toString().startsWith("[T]"));
    }

    @Test
    void toString_containsDescription() {
        Todo todo = new Todo("read book", Priority.LOW);

        assertTrue(todo.toString().contains("read book"));
    }

    @Test
    void toFileString_hasCorrectFormat_notDone() {
        Todo todo = new Todo("read book", Priority.LOW);

        String expectedStart = "T |   |"; // space indicates not done
        assertTrue(todo.toFileString().startsWith("T |"));
        assertTrue(todo.toFileString().contains("read book"));
        assertTrue(todo.toFileString().contains(Priority.LOW.toString()));
    }

    @Test
    void toFileString_hasCorrectFormat_done() {
        Todo todo = new Todo("read book", Priority.LOW, true);

        String fileString = todo.toFileString();

        assertTrue(fileString.startsWith("T | X |"));
        assertTrue(fileString.contains("read book"));
        assertTrue(fileString.contains(Priority.LOW.toString()));
    }

    @Test
    void toFileString_followsFieldOrder() {
        Todo todo = new Todo("study", Priority.HIGH);

        String fileString = todo.toFileString();
        String[] parts = fileString.split(" \\| ");

        assertEquals("T", parts[0]);
        assertEquals("study", parts[2]);
        assertEquals(Priority.HIGH.toString(), parts[3]);
    }
}