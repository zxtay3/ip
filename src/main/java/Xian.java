import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Xian {

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public static void main(String[] args) {
        String banner = """
                 __  __ ___    _    _   _\s
                 \\ \\/ /|_ _|  / \\  | \\ | |
                  \\  /  | |  / _ \\ |  \\| |
                  /  \\  | | / ___ \\| |\\  |
                 /_/\\_\\|___/_/   \\_\\_| \\_|
                """;
        String botName = "XIAN";
        Storage storage = new Storage("data/xian.txt");
        List<Task> tasks;

        System.out.println(banner);
        System.out.println("Hello, I'm " + botName + "!");
        System.out.println("What can I do for you today?\n");

        try{
            tasks = storage.load();
        }catch (IOException e){
            System.out.println("Error loading saved tasks");
            tasks = new ArrayList<>();
        }catch (XianException e){
            System.out.println(e.getMessage());
            tasks = new ArrayList<>();
        }

        Scanner scanner = new Scanner(System.in);

        while(true){
            String input = scanner.nextLine();
            System.out.println();
            try{
                if (input.equals("bye")){
                    break;
                }else if(input.equals("list")){
                    System.out.println("\tHere are the task in your list:");
                    for (int i = 0; i < tasks.size(); i++){
                        System.out.println("\t" + (i+1) + ". " + tasks.get(i));
                    }
                    System.out.println();
                }else{
                    String[] parts = input.split(" ", 2);
                    String command = parts[0];

                    if (parts.length < 2 || parts[1].isBlank()){
                        throw new XianException("Please remember to state task!! >:(");
                    }

                    String remainder = parts[1];

                    if(command.equals("mark")){
                        int idx = Integer.parseInt(remainder);

                        if(idx < 1 || idx > tasks.size()){
                            throw new XianException("Hello?! Please enter a valid item to mark  >:(");
                        }

                        Task t = tasks.get(idx - 1);
                        t.mark();
                        storage.save(tasks);

                        System.out.println("\tNice! I've marked this task as done: ");
                        System.out.println("\t " + t + "\n");
                    }else if(command.equals("unmark")){
                        int idx = Integer.parseInt(remainder);

                        if(idx < 1 || idx > tasks.size()){
                            throw new XianException("Hello?! Please enter a valid item to unmark  >:(");
                        }

                        Task t = tasks.get(idx - 1);
                        t.unmark();
                        storage.save(tasks);

                        System.out.println("\tOK, I've marked this task as not done yet: ");
                        System.out.println("\t " + t + "\n");
                    }else if(command.equals("delete")){
                        int idx = Integer.parseInt(remainder);

                        if(idx < 1 || idx > tasks.size()){
                            throw new XianException("Hello?! Please enter a valid item to delete  >:(");
                        }

                        Task t = tasks.remove(idx - 1);
                        storage.save(tasks);

                        System.out.println("\tNoted!! I have deleted the item from the list");
                        System.out.println("\t " + t + "\n");
                        System.out.println("\tNow you have " + tasks.size() + " tasks in the list\n");
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
                        System.out.println("\tGot it. I've added this task: ");
                        System.out.println("\t " + t);
                        System.out.println("\tNow you have " + tasks.size() + " tasks in the list.\n");
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

        System.out.println("\tBye!! See you again soon!\n");
    }
}
