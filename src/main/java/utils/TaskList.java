package utils;

import task.Task;
import java.util.ArrayList;

/**
 * Manages a collection of Task objects in memory.
 * Provides operations to add, remove, mark, unmark, and list tasks.
 */
public class TaskList {
    ArrayList<Task> taskList;

    /**
     * Creates a TaskList with the provided list of tasks.
     *
     * @param taskList Initial list of tasks
     */
    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Displays all tasks currently stored in the task list.
     */
    public void list() {
        int number = 1;
        for (Task task : taskList) {
            System.out.println("   " + number + ". " + task.toString());
            number++;
        }
    }

    /**
     * Returns number of tasks in the task list
     *
     * @return size of tasklist
     */
    public int getSize() {
        return taskList.size();
    }

    /**
     * Adds a task to the task list and prints a confirmation message.
     *
     * @param task Task to be added
     */
    public void addTask(Task task){
        taskList.add(task);
    }

    /**
     * Marks a task as done based on the given task number.
     *
     * @param taskNumber Task number provided by the user
     */
    public void mark(String taskNumber){
        int taskPos = Integer.parseInt(taskNumber) - 1; // zero based indexing
        Task taskToMark = taskList.get(taskPos);
        taskToMark.mark();
    }

    /**
     * Unmarks a task based on the given task number.
     *
     * @param taskNumber Task number provided by the user
     */
    public void unmark(String taskNumber){
        int taskPos = Integer.parseInt(taskNumber) - 1; // zero based indexing
        Task taskToUnmark = taskList.get(taskPos);
        taskToUnmark.unmark();
    }

    /**
     * Deletes a task based on the given task number and prints a confirmation message.
     *
     * @param taskNumber Task number provided by the user
     */
    public void delete(String taskNumber) {
        int taskPos = Integer.parseInt(taskNumber) - 1; // zero based indexing
        Task removedTask = taskList.remove(taskPos);
        System.out.println("   got it, deleted task: [" + removedTask.toString() + "]!"
                + "\n   you now have " + taskList.size() + " tasks in your list.");
    }

    /**
     * Returns the underlying list of tasks.
     *
     * @return ArrayList containing all tasks
     */
    public ArrayList<Task> getTasks() {
        return this.taskList;
    }

    public void find(String keyword) {
        boolean isFound = false;
        int count = 0;
        for (Task task: taskList) {
            String description = task.getDescription();
            String[] splitDescription = description.split(" ");
            for (String word: splitDescription) {
                if (word.equals(keyword)) {
                    if (!isFound) {
                        System.out.println("   Here are the matching tasks in your list:");
                        count++;
                        System.out.println("    " + count + ". " + task.toString());
                        isFound = true;
                    } else {
                        count++;
                        System.out.println("    " + count + ". " + task.toString());
                    }
                }
            }
        }
    }
}
