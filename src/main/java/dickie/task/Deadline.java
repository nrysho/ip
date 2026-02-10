package dickie.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that must be completed by a specific deadline
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Creates a new deadline task with a description and deadline.
     *
     * @param description Description of the task
     * @param by Deadline of the task
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Creates a new deadline task with a description and deadline. Able to specify isDone boolean
     *
     * @param description Description of the task
     * @param by Deadline of the task
     * @param isDone Boolean on whether task is done
     */
    public Deadline(String description, String by, boolean isDone) {
        super(description, isDone);
        this.by = by;
        this.isDone = isDone;
    }

    /**
     * Returns the string representation of the deadline task.
     *
     * @return Formatted deadline task string
     */
    @Override
    public String toString() {
        return "[D]" + super.getStatusIcon() + " " + super.toString() + " (by: " +
                LocalDate.parse(by).format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }

    /**
     * Converts the deadline task to file format string for storage.
     *
     * @return String representation in format "D | X | description | deadline"
     */
    @Override
    public String toFileString() {
        // Format: "D | X | task detail | Monday 2359"
        return String.format("D | %s | %s | %s",
                getFileStatusIcon(),
                description,
                by);
    }
}