import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task{
    protected LocalDateTime by_date;
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public Deadline(String description,LocalDateTime by_date){
        super(description);
        this.by_date = by_date;
    }

    public LocalDateTime getBy_date(){
        return this.by_date;
    }

    @Override
    public String toString(){
        return "[D]" + super.toString() + " (by: " + by_date.format(DATE_TIME_FORMAT) + ")";
    }
}
