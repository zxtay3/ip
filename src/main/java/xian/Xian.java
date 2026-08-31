package xian;

import java.io.IOException;
import java.time.format.DateTimeParseException;

/**
 * Entry point for the Xian task management application.
 * Xian allows users to add, list, mark, unmark, and delete tasks
 * via text-based commands, with automatic saving to and loading from disk.
 */
public class Xian {

    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

    /**
     * Creates a Xian instance, initializing the UI and loading
     * previously saved tasks from the given file path.
     * If loading fails, starts with an empty task list instead.
     *
     * @param filePath the path to the file used for saving/loading tasks.
     */
    public Xian(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);

        try {
            tasks = storage.load();
        } catch (IOException e) {
            System.out.println("Error loading saved tasks");
            tasks = new TaskList();
        } catch (XianException e) {
            System.out.println(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main command loop, reading and executing user commands
     * until the "bye" command is entered.
     */
    public void run() {
        while (true) {
            String input = ui.readCommand();

            if (input.equals("bye")) {
                break;
            }

            try {
                String response = executeCommand(input);
                System.out.println(response);
            } catch (XianException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Task number entered is not valid!! >:(");
            } catch (DateTimeParseException e) {
                System.out.println("Please enter date and time in correct format!! >:(");
            } catch (IOException e) {
                System.out.println("Unable to save your tasks :(");
            }
        }
    }

    /**
     * Handles the "mark" command by marking the specified task as done
     * and saving the updated task list.
     *
     * @param remainder the arguments containing the task index to mark.
     * @throws IOException if the updated task list cannot be saved.
     * @throws XianException if the given index is invalid.
     */
    private String handleMark(String remainder) throws IOException, XianException {
        int index = Parser.parseIndex(remainder);

        if (index < 1 || index > tasks.getSize()) {
            throw new XianException("Hello?! Please enter a valid item to mark  >:(");
        }

        Task task = tasks.get(index - 1);
        task.mark();
        storage.save(tasks);

        return ui.formatTaskMark(task);
    }

    /**
     * Handles the "unmark" command by marking the specified task as not done
     * and saving the updated task list.
     *
     * @param remainder the arguments containing the task index to unmark.
     * @throws IOException if the updated task list cannot be saved.
     * @throws XianException if the given index is invalid.
     */
    private String handleUnmark(String remainder) throws IOException, XianException {
        int index = Parser.parseIndex(remainder);

        if (index < 1 || index > tasks.getSize()) {
            throw new XianException("Hello?! Please enter a valid item to unmark  >:(");
        }

        Task task = tasks.get(index - 1);
        task.unmark();
        storage.save(tasks);

        return ui.formatTaskUnmark(task);
    }

    /**
     * Handles the "delete" command by removing the specified task
     * and saving the updated task list.
     *
     * @param remainder the arguments containing the task index to delete.
     * @throws IOException if the updated task list cannot be saved.
     * @throws XianException if the given index is invalid.
     */
    private String handleDelete(String remainder) throws IOException, XianException {
        int index = Parser.parseIndex(remainder);

        if (index < 1 || index > tasks.getSize()) {
            throw new XianException("Hello?! Please enter a valid item to delete  >:(");
        }

        Task task = tasks.delete(index);
        storage.save(tasks);

        return ui.formatTaskDelete(task, tasks);
    }

    /**
     * Handles the "todo", "deadline", and "event" commands by creating
     * the corresponding task, adding it to the task list, and saving
     * the updated task list.
     *
     * @param command the type of task to add ("todo", "deadline", or "event").
     * @param remainder the arguments describing the task to create.
     * @throws IOException if the updated task list cannot be saved.
     * @throws XianException if the arguments are not in the expected format.
     */
    private String handleAddTask(String command, String remainder) throws IOException, XianException {
        Task task = switch (command) {
            case "todo" -> Parser.parseTodo(remainder);
            case "deadline" -> Parser.parseDeadline(remainder);
            case "event" -> Parser.parseEvent(remainder);
            default -> throw new XianException("Please enter a valid action :( ");
        };
        tasks.add(task);
        storage.save(tasks);
        return ui.formatTaskAdded(task, tasks);
    }

    /**
     * Handles the "find" command by returning a formatted response containing
     * tasks matching the keyword.
     *
     * @param remainder The keyword to search for in task descriptions.
     */
    private String handleFind(String remainder) {
        TaskList matchingTasks = tasks.find(remainder);
        return ui.formatMatchingTasks(matchingTasks);
    }

    /**
     * Executes a user command and returns the formatted response.
     *
     * @param input The user command to execute.
     * @return The formatted response for the command.
     * @throws XianException If the command or its arguments are invalid.
     * @throws IOException If a task update cannot be saved.
     */
    public String executeCommand(String input) throws XianException, IOException {
        if (input.equals("list")) {
            return ui.formatTaskList(tasks);
        } else {
            String command = Parser.getCommandWord(input);
            String remainder = Parser.getArguments(input);

            return switch (command) {
                case "find" -> handleFind(remainder);
                case "mark" -> handleMark(remainder);
                case "unmark" -> handleUnmark(remainder);
                case "delete" -> handleDelete(remainder);
                default -> handleAddTask(command, remainder);
            };
        }
    }

    /**
     * Starts the Xian application.
     *
     * @param args command-line arguments (not used).
     */
    public static void main (String[]args){
        new Xian("data/xian.txt").run();
    }
}
