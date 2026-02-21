package dickie.task;

/**
 * Represents a todo task without any date or time constraints
 */
public class Todo extends Task {
    /**
     * Creates a new todo task with the given description and priority, defaulting to not done.
     *
     * @param description Description of the todo task
     * @param priority Priority level of the task
     */
    public Todo(String description, Priority priority) {
        super(description, priority);
    }

    /**
     * Creates a new todo task with the given description, priority, and completion status.
     *
     * @param description Description of the todo task
     * @param priority Priority level of the task
     * @param isDone Boolean of whether the task is done or not
     */
    public Todo(String description, Priority priority, boolean isDone) {
        super(description, priority, isDone);
    }

    /**
     * Returns the string representation of the todo task.
     *
     * @return Formatted todo task string
     */
    @Override
    public String toString() {
        return "[T]" + super.getStatusIcon() + super.getPriorityString() + " " + super.toString();
    }

    /**
     * Converts the task to a string format suitable for file storage.
     * Format follows: "T | X | description" for Todo tasks,
     * where "X" indicates done, space indicates not done.
     *
     * @return String representation of the task for file storage
     */
    @Override
    public String toFileString() {
        // Format: "T | X | task detail"
        return String.format("T | %s | %s | %s",
                getFileStatusIcon(),
                description,
                priority);
    }
}