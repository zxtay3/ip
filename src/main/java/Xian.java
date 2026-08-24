import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.time.format.DateTimeParseException;

public class Xian {

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public static void main(String[] args) {

        Storage storage = new Storage("data/xian.txt");
        Ui ui = new Ui();
        TaskList tasks;

        ui.welcomeMessage();

        try{
            tasks = storage.load();
        }catch (IOException e){
            System.out.println("Error loading saved tasks");
            tasks = new TaskList();
        }catch (XianException e){
            System.out.println(e.getMessage());
            tasks = new TaskList();
        }

        while(true){
            String input = ui.readCommand();
            System.out.println();
            try{
                if (input.equals("bye")){
                    break;
                }else if(input.equals("list")){
                    ui.showTaskList(tasks);
                }else{
                    String[] parts = input.split(" ", 2);
                    String command = parts[0];

                    if (parts.length < 2 || parts[1].isBlank()){
                        throw new XianException("Please remember to state task!! >:(");
                    }

                    String remainder = parts[1];

                    if(command.equals("mark")){
                        int idx = Integer.parseInt(remainder);

                        if(idx < 1 || idx > tasks.getSize()){
                            throw new XianException("Hello?! Please enter a valid item to mark  >:(");
                        }

                        Task t = tasks.get(idx - 1);
                        t.mark();
                        storage.save(tasks);

                        ui.showTaskMark(t);
                    }else if(command.equals("unmark")){
                        int idx = Integer.parseInt(remainder);

                        if(idx < 1 || idx > tasks.getSize()){
                            throw new XianException("Hello?! Please enter a valid item to unmark  >:(");
                        }

                        Task t = tasks.get(idx - 1);
                        t.unmark();
                        storage.save(tasks);

                        ui.showTaskUnmark(t);
                    }else if(command.equals("delete")){
                        int idx = Integer.parseInt(remainder);

                        if(idx < 1 || idx > tasks.getSize()){
                            throw new XianException("Hello?! Please enter a valid item to delete  >:(");
                        }

                        Task t = tasks.delete(idx);
                        storage.save(tasks);

                        ui.showTaskDelete(t, tasks);
                    }else{
                        Task t;

                        switch (command) {
                            case "todo" -> t = new Todo(remainder);
                            case "deadline" -> {
                                String[] task_date = remainder.split(" /by ");

                                if (task_date.length != 2 || task_date[0].isBlank() || task_date[1].isBlank()){
                                    throw new XianException("Please ensure the format of the task is correct!! >:(");
                                }

                                String task = task_date[0];
                                LocalDateTime by = LocalDateTime.parse(task_date[1], DATE_TIME_FORMAT);
                                t = new Deadline(task, by);
                            }
                            case "event" -> {
                                String[] task_date = remainder.split(" /from | /to ");

                                if (task_date.length != 3 || task_date[0].isBlank() || task_date[1].isBlank() || task_date[2].isBlank()){
                                    throw new XianException("Please ensure the format of the task is correct!! >:(");
                                }

                                String task = task_date[0];
                                LocalDateTime from = LocalDateTime.parse(task_date[1], DATE_TIME_FORMAT);
                                LocalDateTime to = LocalDateTime.parse(task_date[2], DATE_TIME_FORMAT);

                                t = new Event(task, from, to);
                            }
                            default -> throw new XianException("Please enter a valid action :( ");
                        }
                        tasks.add(t);
                        storage.save(tasks);
                        ui.showTaskAdded(t, tasks);
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
}
