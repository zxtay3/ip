import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Xian {
    public static void main(String[] args) {
        String banner = " __  __ ___    _    _   _ \n"
                + " \\ \\/ /|_ _|  / \\  | \\ | |\n"
                + "  \\  /  | |  / _ \\ |  \\| |\n"
                + "  /  \\  | | / ___ \\| |\\  |\n"
                + " /_/\\_\\|___/_/   \\_\\_| \\_|\n";
        String botName = "XIAN";
        List<Task> tasks = new ArrayList<>();

        System.out.println(banner);
        System.out.println("Hello, I'm " + botName + "!");
        System.out.println("What can I do for you today?\n");

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

                        System.out.println("\tNice! I've marked this task as done: ");
                        System.out.println("\t " + t + "\n");
                    }else if(command.equals("unmark")){
                        int idx = Integer.parseInt(remainder);

                        if(idx < 1 || idx > tasks.size()){
                            throw new XianException("Hello?! Please enter a valid item to unmark  >:(");
                        }

                        Task t = tasks.get(idx - 1);
                        t.unmark();

                        System.out.println("\tOK, I've marked this task as not done yet: ");
                        System.out.println("\t " + t + "\n");
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
                                String by = task_date[1];
                                t = new Deadline(task, by);
                            }
                            case "event" -> {
                                String[] task_date = remainder.split(" /from | /to ");

                                if (task_date.length != 3 || task_date[0].isBlank() || task_date[1].isBlank() || task_date[2].isBlank()){
                                    throw new XianException("Please ensure the format of the task is correct!! >:(");
                                }

                                String task = task_date[0];
                                String from = task_date[1];
                                String to = task_date[2];
                                t = new Event(task, from, to);
                            }
                            default -> throw new XianException("Please enter a valid action :( ");
                        }
                        tasks.add(t);
                        System.out.println("\tGot it. I've added this task: ");
                        System.out.println("\t " + t);
                        System.out.println("\tNow you have " + tasks.size() + " tasks in the list.\n");
                    }
                }
            }catch (XianException e){
                System.out.println(e.getMessage());
            }catch (NumberFormatException e){
                System.out.println("Task number entered is not valid!! >:(");
            }

        }

        System.out.println("\tBye!! See you again soon!\n");
    }
}
