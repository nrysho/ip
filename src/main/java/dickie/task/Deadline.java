package dickie.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that must be completed by a specific deadline
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Creates a new deadline task with the given description, deadline date, and priority, defaulting to not done.
     *
     * @param description Description of the task
     * @param by Deadline date of the task in YYYY-MM-DD format
     * @param priority Priority level of the task
     */
    public Deadline(String description, String by, Priority priority) {
        super(description, priority);
        this.by = by;
    }

    /**
     * Creates a new deadline task with the given description, deadline date, priority, and completion status.
     *
     * @param description Description of the task
     * @param by Deadline date of the task in YYYY-MM-DD format
     * @param priority Priority level of the task
     * @param isDone Boolean of whether the task is done or not
     */
    public Deadline(String description, String by, Priority priority, boolean isDone) {
        super(description, priority, isDone);
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
        return "[D]" + super.getStatusIcon() + super.getPriorityString()
                + " " + super.toString()
                + " (by: " +
                LocalDate.parse(by).format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + ")";
    }

    /**
     * Converts the deadline task to file format string for storage.
     *
     * @return String representation in format "D | X | description | YYYY-MM-DD | priority"
     */
    @Override
    public String toFileString() {
        // Format: "D | X | task detail | Monday 2359 | HIGH"
        return String.format("D | %s | %s | %s | %s",
                getFileStatusIcon(),
                description,
                by,
                priority);
    }
}