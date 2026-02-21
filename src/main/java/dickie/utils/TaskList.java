package dickie.utils;

import dickie.task.Task;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

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
        Collections.sort(taskList);
    }

    /**
     * Marks a task as done based on the given task number.
     *
     * @param taskNumber Task number provided by the user
     *
     * @return Task that has been marked
     */
    public Task mark(String taskNumber){
        int taskPos = Integer.parseInt(taskNumber) - 1; // zero based indexing
        assert taskPos >= 0 && taskPos < taskList.size() : "Task index" +
                "out of bounds";
        Task taskToMark = taskList.get(taskPos);
        taskToMark.mark();
        return taskToMark;
    }

    /**
     * Unmarks a task based on the given task number.
     *
     * @param taskNumber Task number provided by the user
     */
    public Task unmark(String taskNumber){
        int taskPos = Integer.parseInt(taskNumber) - 1; // zero based indexing
        Task taskToUnmark = taskList.get(taskPos);
        taskToUnmark.unmark();
        return taskToUnmark;
    }

    /**
     * Deletes a task based on the given task number and prints a confirmation message.
     *
     * @param taskNumber Task number provided by the user
     */
    public Task delete(String taskNumber) {
        int taskPos = Integer.parseInt(taskNumber) - 1; // zero based indexing
        Task removedTask = taskList.remove(taskPos);
        return removedTask;
    }

    /**
     * Returns the underlying list of tasks.
     *
     * @return ArrayList containing all tasks
     */
    public ArrayList<Task> getTasks() {
        return this.taskList;
    }

    /**
     * Finds and returns all tasks that contain the specified keyword in their description.
     *
     * @param keyword The keyword to search for in task descriptions
     * @return ArrayList of tasks whose descriptions contain the keyword
     */
    public ArrayList<Task> find(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return taskList.stream()
                .filter(task -> task.getDescription().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
