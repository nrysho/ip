package dickie.task;

/**
 * Represents a task that occurs over a specific time period
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Creates a new event task with a description, start time, and end time.
     *
     * @param description Description of the event
     * @param from Start time of the event
     * @param to End time of the event
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Creates a new event task with a description, start time, end time and specified isDone
     *
     * @param description Description of the event
     * @param from Start time of the event
     * @param to End time of the event
     * @param isDone Boolean on whether task is done
     */
    public Event(String description, String from, String to, boolean isDone) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the string representation of the event task.
     *
     * @return Formatted event task string
     */
    @Override
    public String toString() {
        return "[E]" + super.getStatusIcon() + " " +super.toString() + " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String toFileString() {
        // Format: "E | X | task detail | 2pm | 4pm"
        return String.format("E | %s | %s | %s | %s",
                getFileStatusIcon(),
                description,
                from,
                to);
    }
}