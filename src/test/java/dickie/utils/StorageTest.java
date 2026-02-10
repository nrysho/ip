package dickie.utils;

import dickie.exception.DickieException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class StorageTest {
    @Test
    public void parseTask_invalidInput_exceptionThrown(){
        try {
            String fileString = "X | T | homework";
            Storage.parseTask(fileString);
            fail();
        } catch (DickieException e) {
            assertEquals("Error in when parsing file: Invalid task type.",
                         e.getMessage());
        }
    }
}
