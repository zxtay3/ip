public class Deadline extends Task{
    protected String by_date;

    public Deadline(String description,String by_date){
        super(description);
        this.by_date = by_date;
    }

    public String getBy_date(){
        return this.by_date;
    }

    @Override
    public String toString(){
        return "[D]" + super.toString() + " (by: " + by_date + ")";
    }
}
