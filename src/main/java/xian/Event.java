package xian;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that spans a period of time, with a start and end date/time.
 */
public class Event extends Task {
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Creates a new Event task with the given description, start time, and end time.
     *
     * @param description The description of the task.
     * @param from The date and time the event starts.
     * @param to The date and time the event ends.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the date and time this event starts.
     *
     * @return the start date and time.
     */
    public LocalDateTime getFrom() {
        return this.from;
    }

    /**
     * Returns the date and time this event ends.
     *
     * @return the end date and time.
     */
    public LocalDateTime getTo() {
        return this.to;
    }

    /**
     * Returns the string representation of this Event task,
     * prefixed with "[E]" and including its start and end date/time.
     *
     * @return the formatted string representation of this task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(DATE_TIME_FORMAT)
                + " to: " + to.format(DATE_TIME_FORMAT) + ")";
    }
}
