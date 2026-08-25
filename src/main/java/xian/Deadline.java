package xian;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that needs to be completed before a specific date and time.
 */
public class Deadline extends Task{
    protected LocalDateTime by_date;
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Creates a new Deadline task with the given description and due date/time.
     *
     * @param description the description of the task.
     * @param by_date the date and time by which the task should be completed.
     */
    public Deadline(String description,LocalDateTime by_date){
        super(description);
        this.by_date = by_date;
    }

    /**
     * Returns the date and time by which this task should be completed.
     *
     * @return the deadline date and time.
     */
    public LocalDateTime getBy_date(){
        return this.by_date;
    }

    /**
     * Returns the string representation of this Deadline task,
     * prefixed with "[D]" and including its due date and time.
     *
     * @return the formatted string representation of this task.
     */
    @Override
    public String toString(){
        return "[D]" + super.toString() + " (by: " + by_date.format(DATE_TIME_FORMAT) + ")";
    }
}
