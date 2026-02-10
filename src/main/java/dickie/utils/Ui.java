package dickie.utils;

import dickie.task.Task;

import java.util.ArrayList;

/**
 * Handles all user interface interactions for the Dickie application.
 * This class is responsible for displaying messages and information to the user.
 */
public class Ui {
    /**
     * Prints message to show the user the task they added and number of tasks currently.
     *
     * @param task Task that has been added
     * @param size Number of tasks in task list
     */
    public void showTaskAdded(Task task, int size) {
        System.out.println("   okie, I've added the task:\n" +
                           "     " + task.toString() +
                           "\n   you now have " + size + " tasks in your list.");
    }

    /**
     * Displays a message indicating that a task has been marked as done.
     *
     * @param task The task that has been marked as complete
     */
    public void showTaskMarked(String task) {
        System.out.println("   good job! I've marked " + "[task: " +  task + "] as DONE");
    }

    /**
     * Displays a message indicating that a task has been unmarked.
     *
     * @param task The task that has been unmarked
     */
    public void showTaskUnmarked(String task) {
        System.out.println("   aite, I've unmarked " + "[task: " + task + "]");
    }

    /**
     * Displays a message confirming task deletion and shows the updated number of tasks.
     *
     * @param task The task that has been deleted
     * @param taskListSize The current number of tasks remaining in the task list
     */
    public void showTaskDeleted(String task, int taskListSize) {
        System.out.println("   got it, deleted task: [" + task + "]!"
                + "\n   you now have " + taskListSize + " tasks in your list.");
    }

    /**
     * Displays all tasks that match the search criteria.
     *
     * @param foundTasks ArrayList of tasks that match the search query
     */
    public void showFoundTasks(ArrayList<Task> foundTasks) {
        System.out.println("   Here are the matching tasks in your list:");
        int idx = 1;
        for (Task task: foundTasks) {
            System.out.println("    " + idx + ". " + task.toString());
            idx++;
        }
    }

    /**
     * Displays all tasks currently stored in the task list.
     *
     * @param taskList The TaskList object containing all tasks to be displayed
     */
    public void listTasks(TaskList taskList) {
        ArrayList<Task> arrayTaskList = taskList.getTasks();
        int number = 1;
        for (Task task : arrayTaskList) {
            System.out.println("   " + number + ". " + task.toString());
            number++;
        }
    }

    /**
     * Prints the greeting message when the program starts.
     */
    public void showGreeting() {
        String greeting = "   Hey gorlll I'm dickie\n" +
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
