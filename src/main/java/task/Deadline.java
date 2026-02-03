package task;

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
        try {
            LocalDate byAsDate = LocalDate.parse(by);
            this.by = byAsDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("try again! deadline should be formatted in YYYY-MM-DD format");
        }
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
        LocalDate byAsDate = LocalDate.parse(by);
        this.by = byAsDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        this.isDone = isDone;
    }

    /**
     * Returns the string representation of the deadline task.
     *
     * @return Formatted deadline task string
     */
    @Override
    public String toString() {
        return "[D]" + super.getStatusIcon() + " " + super.toString() + " (by: " + by + ")";
    }

    @Override
    public String toFileString() {
        // Format: "D | X | task detail | Monday 2359"
        return String.format("D | %s | %s | %s",
                getFileStatusIcon(),
                description,
                by);
    }
}