package dickie.command;

/**
 * Represents the different types of commands that can be issued by the user
 * Each command corresponds to a specific action that the program can perform
 */
public enum CommandType {
    // VOID is the case where the message length is 0
    LIST, MARK, UNMARK, ADDTASK, DELETE, FIND;
}
