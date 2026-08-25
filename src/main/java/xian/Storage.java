package xian;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Deals with loading tasks from the save file and saving tasks to the save file.
 */
public class Storage{
    private final Path path;
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Creates a Storage instance that reads from and writes to the given file path.
     *
     * @param filePath the path of the file used for saving/loading tasks.
     */
    public Storage(String filePath){
        this.path = Paths.get(filePath);
    }

    /**
     * Loads tasks from the save file into a new TaskList.
     * If the save file does not exist, an empty TaskList is returned.
     *
     * @return the loaded TaskList.
     * @throws IOException if the save file cannot be read.
     * @throws XianException if a line in the save file has an invalid task type.
     */
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

    /**
     * Saves the given TaskList to the save file, creating any missing
     * parent directories as needed.
     *
     * @param tasks the TaskList to save.
     * @throws IOException if the save file cannot be written.
     * @throws XianException if the TaskList contains a task of an unrecognized type.
     */
    public void save(TaskList tasks) throws IOException, XianException{
        Path parent = path.getParent();

        if (parent != null){
            Files.createDirectories(parent);
        }

        List<String> lines = new ArrayList<>();

        for(Task task : tasks){
            String doneStatus = task.getStatusCode();

            switch (task) {
                case Todo todo -> lines.add("T | " + doneStatus + " | " + task.getDescription());
                case Deadline deadline -> lines.add("D | " + doneStatus + " | "
                        + task.getDescription() + " | "
                        + deadline.getBy_date().format(DATE_TIME_FORMAT));
                case Event event -> lines.add("E | " + doneStatus + " | "
                        + task.getDescription() + " | "
                        + event.getFrom().format(DATE_TIME_FORMAT) + " | "
                        + event.getTo().format(DATE_TIME_FORMAT));
                default -> throw new XianException("Invalid task type cannot be saved");
            }
        }

        Files.write(path, lines);
    }
}