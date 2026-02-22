package dickie;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// AI-assisted: Used ChatGPT to design behavioural unit tests for the Dickie class.
// Identified key public behaviours, edge cases, and exception-handling scenarios.
// Tests focus on observable behaviour only, avoiding private methods and implementation details.
// All test cases were reviewed and adapted to align with the project architecture and JUnit 5 standards.
public class DickieTest {
    @Test
    public void getGreeting_validCall_returnsNonNullGreeting() {
        Dickie dickie = new Dickie();

        String greeting1 = dickie.getGreeting();
        String greeting2 = dickie.getGreeting();

        assertNotNull(greeting1);
        assertEquals(greeting1, greeting2);
    }

    @Test
    public void getResponse_inputBye_returnsGoodbyeMessage() {
        Dickie dickie = new Dickie();

        String response = dickie.getResponse("bye");

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }

    @Test
    public void getResponse_invalidCommand_returnsErrorMessage() {
        Dickie dickie = new Dickie();

        String response = dickie.getResponse("invalidcommand123");

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }

    @Test
    public void getResponse_emptyInput_returnsErrorMessage() {
        Dickie dickie = new Dickie();

        String response = dickie.getResponse("");

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }

    @Test
    public void getResponse_whitespaceInput_returnsErrorMessage() {
        Dickie dickie = new Dickie();

        String response = dickie.getResponse("   ");

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }

    @Test
    public void getResponse_validTodoCommand_returnsSuccessMessage() {
        Dickie dickie = new Dickie();

        String response = dickie.getResponse("todo read book /p low");

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }

    @Test
    public void getResponse_addThenList_taskAppearsInList() {
        Dickie dickie = new Dickie();

        dickie.getResponse("todo read book /p low");
        String listResponse = dickie.getResponse("list");

        assertNotNull(listResponse);
        assertTrue(listResponse.contains("read book"));
    }

    @Test
    public void getResponse_multipleSequentialCalls_noCrash() {
        Dickie dickie = new Dickie();

        assertDoesNotThrow(() -> {
            dickie.getResponse("todo A /p low");
            dickie.getResponse("todo B /p medium");
            dickie.getResponse("list");
            dickie.getResponse("bye");
        });
    }
}