import java.io.IOException;
import java.time.format.DateTimeParseException;

public class Xian {

    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

    public Xian(String filePath){
        ui = new Ui();
        storage = new Storage(filePath);

        try{
            tasks = storage.load();
        }catch (IOException e){
            System.out.println("Error loading saved tasks");
            tasks = new TaskList();
        }catch (XianException e){
            System.out.println(e.getMessage());
            tasks = new TaskList();
        }
    }

    public void run(){
        ui.welcomeMessage();
        while(true){
            String input = ui.readCommand();
            System.out.println();
            try{
                if (input.equals("bye")){
                    break;
                }else if(input.equals("list")){
                    ui.showTaskList(tasks);
                }else{
                    String command = Parser.getCommandWord(input);
                    String remainder = Parser.getArguments(input);

                    switch (command) {
                        case "mark" -> handleMark(remainder);
                        case "unmark" -> handleUnmark(remainder);
                        case "delete" -> handleDelete(remainder);
                        default -> handleAddTask(command, remainder);
                    }
                }
            }catch (XianException e){
                System.out.println(e.getMessage());
            }catch (NumberFormatException e){
                System.out.println("Task number entered is not valid!! >:(");
            }catch (DateTimeParseException e){
                System.out.println("Please enter date and time in correct format!! >:(");
            }catch (IOException e){
                System.out.println("Unable to save your tasks :(");
            }
        }
        ui.showEnd();
    }

    private void handleMark(String remainder) throws IOException, XianException{
        int idx = Parser.parseIndex(remainder);

        if (idx < 1 || idx > tasks.getSize()) {
            throw new XianException("Hello?! Please enter a valid item to mark  >:(");
        }

        Task t = tasks.get(idx - 1);
        t.mark();
        storage.save(tasks);

        ui.showTaskMark(t);
    }

    private void handleUnmark(String remainder) throws IOException, XianException{
        int idx = Parser.parseIndex(remainder);

        if (idx < 1 || idx > tasks.getSize()) {
            throw new XianException("Hello?! Please enter a valid item to unmark  >:(");
        }

        Task t = tasks.get(idx - 1);
        t.unmark();
        storage.save(tasks);

        ui.showTaskUnmark(t);
    }

    private void handleDelete(String remainder) throws IOException, XianException{
        int idx = Parser.parseIndex(remainder);

        if (idx < 1 || idx > tasks.getSize()) {
            throw new XianException("Hello?! Please enter a valid item to delete  >:(");
        }

        Task t = tasks.delete(idx);
        storage.save(tasks);

        ui.showTaskDelete(t, tasks);
    }

    private void handleAddTask(String command, String remainder) throws IOException, XianException{
        Task t = switch (command) {
            case "todo" -> Parser.parseTodo(remainder);
            case "deadline" -> Parser.parseDeadline(remainder);
            case "event" -> Parser.parseEvent(remainder);
            default -> throw new XianException("Please enter a valid action :( ");
        };
        tasks.add(t);
        storage.save(tasks);
        ui.showTaskAdded(t, tasks);
    }

    public static void main(String[] args) {
        new Xian("data/xian.txt").run();
    }
}
