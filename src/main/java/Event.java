import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task{
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public Event(String description, LocalDateTime from, LocalDateTime to){
        super(description);
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getFrom(){
        return this.from;
    }

    public LocalDateTime getTo(){
        return this.to;
    }

    @Override
    public String toString(){
        return "[E]" + super.toString() + " (from: " + from.format(DATE_TIME_FORMAT) + " to: " + to.format(DATE_TIME_FORMAT) + ")";
    }
}