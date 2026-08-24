import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Storage{
    private final Path path;
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public Storage(String filePath){
        this.path = Paths.get(filePath);
    }

    public TaskList load() throws IOException, XianException{
        TaskList tasks = new TaskList();

        if (!Files.exists(path)) {
            return tasks;
        }

        List<String> lines = Files.readAllLines(path);

        for (String line : lines){
            String[] parts = line.split(" \\| ");
            Task task;

            switch(parts[0]){
                case "T" -> task = new Todo(parts[2]);
                case "D" -> task = new Deadline(parts[2], LocalDateTime.parse(parts[3], DATE_TIME_FORMAT));
                case "E" -> task = new Event(
                        parts[2],
                        LocalDateTime.parse(parts[3], DATE_TIME_FORMAT),
                        LocalDateTime.parse(parts[4], DATE_TIME_FORMAT)
                );
                default -> throw new XianException("Unable to load invalid saved task type");
            }

            if (parts[1].equals("1")){
                task.mark();
            }

            tasks.add(task);
        }
        return tasks;
    }

    public void save(TaskList tasks) throws IOException, XianException{
        Path parent = path.getParent();

        if (parent != null){
            Files.createDirectories(parent);
        }

        List<String> lines = new ArrayList<>();

        for(Task task : tasks){
            String doneStatus = task.getStatusCode();

            if(task instanceof Todo){
                lines.add("T | " + doneStatus + " | " + task.getDescription());
            }else if (task instanceof Deadline){
                lines.add("D | " + doneStatus + " | "
                        + task.getDescription() + " | "
                        + ((Deadline) task).getBy_date().format(DATE_TIME_FORMAT));
            }else if (task instanceof Event){
                lines.add("E | " + doneStatus + " | "
                        + task.getDescription() + " | "
                        + ((Event) task).getFrom().format(DATE_TIME_FORMAT) + " | "
                        + ((Event) task).getTo().format(DATE_TIME_FORMAT));
            }else{
                throw new XianException("Invalid task type cannot be saved");
            }
        }

        Files.write(path, lines);
    }
}