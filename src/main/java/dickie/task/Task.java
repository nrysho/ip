package dickie.task;

/**
 * Represents a generic task with a description and completion status
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected Priority priority;

    /**
     * Creates a new task with the given description and priority, defaulting to not done.
     *
     * @param description Description of the task
     * @param priority Priority level of the task
     */
    public Task(String description, Priority priority) {
        this.description = description;
        this.priority = priority;
        this.isDone = false;
    }

    /**
     * Creates a new task with the given description, priority, and completion status.
     *
     * @param description Description of the task
     * @param priority Priority level of the task
     * @param isDone Boolean of whether the task is done or not
     */
    public Task(String description, Priority priority, boolean isDone) {
        this.description = description;
        this.priority = priority;
        this.isDone = isDone;
    }

    /**
     * Returns description of Task
     *
     * @return task description
     */
    public String getDescription() {
        return this.description;
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
     * Returns the priority of the task in string form.
     *
     * @return "[LOW]" if the priority is low, "[MEDIUM]" if the priority is medium, "[HIGH]" if the priority is high
     */
    public String getPriorityString() {
        return "[" + priority.toString() + "]";
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
     * Returns the description of the task.
     *
     * @return Task description
     */
    public String toString() {
        return description;
    }

    /**
     * Converts task to file format string to save into file
     * Format: "T | X | task detail | HIGH" or "D | X | task detail | Monday 2359 | LOW" etc.
     *
     * @return String representation for file storage
     */
    public abstract String toFileString();

    /**
     * Marks the task as done
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done
     */
    public void unmark() {
        this.isDone = false;
    }
}