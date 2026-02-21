package dickie.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that occurs over a specific time period
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Creates a new event task with the given description, start date, end date, and priority, defaulting to not done.
     *
     * @param description Description of the event
     * @param from Start date of the event in YYYY-MM-DD format
     * @param to End date of the event in YYYY-MM-DD format
     * @param priority Priority level of the task
     */
    public Event(String description, String from, String to, Priority priority) {
        super(description, priority);
        this.from = from;
        this.to = to;
    }

    /**
     * Creates a new event task with the given description, start date, end date, priority, and completion status.
     *
     * @param description Description of the event
     * @param from Start date of the event in YYYY-MM-DD format
     * @param to End date of the event in YYYY-MM-DD format
     * @param priority Priority level of the task
     * @param isDone Boolean of whether the task is done or not
     */
    public Event(String description, String from, String to, Priority priority, boolean isDone) {
        super(description, priority, isDone);
        this.from = from;
        this.to = to;
        this.priority = priority;
    }

    /**
     * Returns the string representation of the event task.
     *
     * @return Formatted event task string
     */
    @Override
    public String toString() {
        return "[E]" + super.getStatusIcon() + super.getPriorityString()
                + " " +super.toString() + " (from: "
                + LocalDate.parse(from).format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + " to: "
                + LocalDate.parse(to).format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + ")";
    }

    /**
     * Converts the event task to file format string for storage.
     *
     * @return String representation in format "E | X | description | from | to"
     */
    @Override
    public String toFileString() {
        // Format: "E | X | task detail | 2pm | 4pm"
        return String.format("E | %s | %s | %s | %s | %s",
                getFileStatusIcon(),
                description,
                from,
                to,
                priority);
    }
}