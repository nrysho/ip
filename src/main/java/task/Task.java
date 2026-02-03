package task;

/**
 * Represents a generic task with a description and completion status
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new task with the given description.
     *
     * @param description Description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Creates a new task with the given description and specified isDone
     *
     * @param description Description of the task
     * @param isDone Boolean of whether the task is done or not
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns the status icon of the task.
     *
     * @return "[X]" if the task is done, "[ ]" otherwise
     */
    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]"); // mark done task with X
    }

    /**
     * Returns the status icon of the task, specifically formatted for txt file storage
     *
     * @return "[X]" if the task is done, "[ ]" otherwise
     */
    public String getFileStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns the string representation of the task.
     *
     * @return Task description
     */
    public String toString() {
        return description;
    }

    /**
     * Converts task to file format string
     * Format: "T | X | task detail" or "D | X | task detail | Monday 2359" etc.
     *
     * @return String representation for file storage
     */
    public String toFileString() {
        return null;
    }

    /**
     * Marks the task as done and prints a confirmation message.
     */
    public void mark() {
        this.isDone = true;
        System.out.println("   good job! I've marked " + "[task: " +  this.toString() + "] as DONE");
    }

    /**
     * Marks the task as not done and prints a confirmation message.
     */
    public void unmark() {
        this.isDone = false;
        System.out.println("   aite, I've unmarked " + "[task: " + this.toString() + "]");
    }
}