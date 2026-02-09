package dickie.task;

/**
 * Represents a todo task without any date or time constraints
 */
public class Todo extends Task {
    /**
     * Creates a new todo task with the given description.
     *
     * @param description Description of the todo task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Creates a new todo task with the given description and specified isDone field
     *
     * @param description Description of the todo task
     * @param isDone Boolean on whether or not task is done
     */
    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    /**
     * Returns the string representation of the todo task.
     *
     * @return Formatted todo task string
     */
    @Override
    public String toString() {
        return "[T]" + super.getStatusIcon() + " " + super.toString();
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
        return String.format("T | %s | %s",
                getFileStatusIcon(),
                description);
    }
}