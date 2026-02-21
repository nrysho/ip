package dickie.command;

import dickie.exception.DickieException;
import dickie.task.TaskType;
import dickie.utils.TaskList;
import dickie.utils.Ui;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// AI-assisted: Used ChatGPT to help identify behavioural test cases and edge cases
// for CommandParser, including invalid commands, task number validation,
// and integration behaviour with TaskList and Ui.
// I reviewed all generated tests, adjusted inputs to match actual TaskFactory
// requirements , and ensured the tests reflect the implemented behaviour
// without relying on internal details.
class CommandParserTest {

    // =========================
    // splitInput Tests
    // =========================

    @Test
    void splitInput_trimsAndSplitsCorrectly() {
        String[] result = CommandParser.splitInput("mark   1   ");
        assertArrayEquals(new String[]{"mark", "1"}, result);
    }

    @Test
    void splitInput_singleWord() {
        String[] result = CommandParser.splitInput("list");
        assertArrayEquals(new String[]{"list"}, result);
    }

    // =========================
    // isValidTaskNumber Tests
    // =========================

    @Test
    void isValidTaskNumber_validNumberWithinRange_returnsTrue() {
        assertTrue(CommandParser.isValidTaskNumber("1", 3));
    }

    @Test
    void isValidTaskNumber_zero_returnsFalse() {
        assertFalse(CommandParser.isValidTaskNumber("0", 3));
    }

    @Test
    void isValidTaskNumber_negative_returnsFalse() {
        assertFalse(CommandParser.isValidTaskNumber("-1", 3));
    }

    @Test
    void isValidTaskNumber_nonNumeric_returnsFalse() {
        assertFalse(CommandParser.isValidTaskNumber("abc", 3));
    }

    @Test
    void isValidTaskNumber_outOfRange_returnsFalse() {
        assertFalse(CommandParser.isValidTaskNumber("5", 3));
    }

    // =========================
    // getTaskType Tests
    // =========================

    @Test
    void getTaskType_validTodo_returnsTodoType() throws DickieException {
        assertEquals(TaskType.TODO,
                CommandParser.getTaskType(new String[]{"todo"}));
    }

    @Test
    void getTaskType_invalidType_throwsException() {
        assertThrows(DickieException.class, () ->
                CommandParser.getTaskType(new String[]{"invalid"}));
    }

    // =========================
    // getInputCommandType Tests
    // =========================

    @Test
    void getInputCommandType_listValid_returnsList() throws DickieException {
        assertEquals(CommandType.LIST,
                CommandParser.getInputCommandType(new String[]{"list"}, 0));
    }

    @Test
    void getInputCommandType_listWithExtraArgs_throwsException() {
        assertThrows(DickieException.class, () ->
                CommandParser.getInputCommandType(new String[]{"list", "extra"}, 0));
    }

    @Test
    void getInputCommandType_findWithoutKeyword_throwsException() {
        assertThrows(DickieException.class, () ->
                CommandParser.getInputCommandType(new String[]{"find"}, 0));
    }

    @Test
    void getInputCommandType_markWithInvalidNumber_throwsException() {
        assertThrows(DickieException.class, () ->
                CommandParser.getInputCommandType(new String[]{"mark", "5"}, 1));
    }

    @Test
    void getInputCommandType_invalidCommand_throwsException() {
        assertThrows(DickieException.class, () ->
                CommandParser.getInputCommandType(new String[]{"hello"}, 0));
    }

    // =========================
    // handleInput Behaviour Tests
    // =========================

    @Test
    void handleInput_list_returnsUiListOutput() throws DickieException {
        TaskList taskList = new TaskList(new ArrayList<>());
        Ui ui = new Ui();

        String result = CommandParser.handleInput("list", taskList, ui);

        assertNotNull(result);
    }

    @Test
    void handleInput_todo_addsTask() throws DickieException {
        TaskList taskList = new TaskList(new ArrayList<>());
        Ui ui = new Ui();

        String result = CommandParser.handleInput(
                "todo read book /p HIGH",
                taskList,
                ui
        );

        assertEquals(1, taskList.getSize());
        assertNotNull(result);
    }

    @Test
    void handleInput_mark_validMarksTask() throws DickieException {
        TaskList taskList = new TaskList(new ArrayList<>());
        Ui ui = new Ui();

        CommandParser.handleInput("todo read book /p MEDIUM", taskList, ui);
        String result = CommandParser.handleInput("mark 1", taskList, ui);

        assertNotNull(result);
    }

    @Test
    void handleInput_invalidCommand_throwsException() {
        TaskList taskList = new TaskList(new ArrayList<>());
        Ui ui = new Ui();

        assertThrows(DickieException.class, () ->
                CommandParser.handleInput("invalid", taskList, ui));
    }
}
