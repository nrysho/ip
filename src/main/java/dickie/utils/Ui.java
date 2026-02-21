package dickie.utils;

import dickie.task.Task;

import java.util.ArrayList;

public class Ui {
    public String showTaskAdded(Task task, int size) {
        return "okie, I've added the task:\n"
               + task.toString() +
               "\nyou now have " + size + " tasks in your list.";
    }

    public String showTaskMarked(String task) {
        return "good job! I've marked " + "[task: " +  task + "] as DONE";
    }

    public String showTaskUnmarked(String task) {
        return "aite, I've unmarked " + "[task: " + task + "]";
    }

    public String showTaskDeleted(String task, int taskListSize) {
        return "got it, deleted task: [" + task + "]!"
                + "\nyou now have " + taskListSize + " tasks in your list.";
    }

    public String showFoundTasks(ArrayList<Task> foundTasks) {
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:");
        int idx = 1;
        for (Task task: foundTasks) {
            sb.append("\n" + idx + ". " + task.toString());
            idx++;
        }
        return sb.toString();
    }

    /**
     * Displays all tasks currently stored in the task list.
     *
     * @param taskList The TaskList object containing all tasks to be displayed
     */
    public String listTasks(TaskList taskList) {
        if (taskList.getSize() == 0)  {
            return "you currently have no tasks in your tasklist!";
        }
        StringBuilder sb = new StringBuilder();
        ArrayList<Task> arrayTaskList = taskList.getTasks();
        int number = 1;
        for (Task task : arrayTaskList) {
            if (number == 1) {
                sb.append(number + ". " + task.toString());
            } else {
                sb.append("\n" + number + ". " + task.toString());
            }
            number++;
        }
        return sb.toString();
    }

    /**
     * Prints the greeting message when the program starts.
     */
    public String showGreeting() {
        return "Hey gorlll I'm dickie\n" +
                "What can I do for you?";
    }

    /**
     * Prints the farewell message when the program ends.
     */
    public String showGoodbye() {
        return "Byee. See ya~";
    }
}
