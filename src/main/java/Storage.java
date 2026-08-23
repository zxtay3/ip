import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Storage{
    private final Path path;

    public Storage(String filePath){
        this.path = Paths.get(filePath);
    }

    public List<Task> load() throws IOException, XianException{
        List<Task> tasks = new ArrayList<>();

        if (!Files.exists(path)) {
            return tasks;
        }

        List<String> lines = Files.readAllLines(path);

        for (String line : lines){
            String[] parts = line.split(" \\| ");
            Task task;

            switch(parts[0]){
                case "T" -> task = new Todo(parts[2]);
                case "D" -> task = new Deadline(parts[2], parts[3]);
                case "E" -> task = new Event(parts[2], parts[3], parts[4]);
                default -> throw new XianException("Unable to load invalid saved task type");
            }

            if (parts[1].equals("1")){
                task.mark();
            }

            tasks.add(task);
        }
        return tasks;
    }

    public void save(List<Task> tasks) throws IOException, XianException{
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
                lines.add("D | " + doneStatus + " | " + task.getDescription() + " | " + ((Deadline) task).getBy_date());
            }else if (task instanceof Event){
                lines.add("E | " + doneStatus + " | " + task.getDescription() + " | " + ((Event) task).getFrom() + " | " + ((Event) task).getTo());
            }else{
                throw new XianException("Invalid task type cannot be saved");
            }
        }

        Files.write(path, lines);
    }
}