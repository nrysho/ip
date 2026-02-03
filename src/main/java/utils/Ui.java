package utils;

import task.Task;

import java.util.Scanner;

public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void showTaskAdded(Task task, int size) {
        System.out.println("   okie, I've added the task:\n" +
                           "     " + task.toString() +
                           "\n   you now have " + size + " tasks in your list.");
    }

    public void showLoadingError() {
        System.out.println("Error loading saved tasks.");
    }

    /**
     * Prints the greeting message when the program starts.
     */
    public void showGreeting() {
        String greeting = "   Hey gorlll I'm Dickie\n" +
                "   What can I do for you?";
        System.out.println(greeting);
    }

    /**
     * Prints the farewell message when the program ends.
     */
    public void showGoodbye() {
        String greeting = "   Byee. See ya~";
        System.out.println(greeting);
    }
}
