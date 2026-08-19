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
                String remainder = parts[1];

                if(command.equals("mark")){
                    int idx = Integer.parseInt(parts[1]);
                    Task t = tasks.get(idx - 1);
                    t.mark();

                    System.out.println("\tNice! I've marked this task as done: ");
                    System.out.println("\t " + t + "\n");
                }else if(command.equals("unmark")){
                    int idx = Integer.parseInt(parts[1]);
                    Task t = tasks.get(idx - 1);
                    t.unmark();

                    System.out.println("\tOK, I've marked this task as not done yet: ");
                    System.out.println("\t " + t + "\n");
                }else{
                    Task t;
                    if(command.equals("todo")){
                        t = new Todo(remainder);
                    }else if(command.equals("deadline")){
                        String[] task_date = remainder.split(" /by ");
                        String task = task_date[0];
                        String by = task_date[1];
                        t = new Deadline(task, by);
                    }else{
                        continue;
                    }
                    tasks.add(t);
                    System.out.println("\tGot it. I've added this task: ");
                    System.out.println("\t " + t);
                    System.out.println("\tNow you have " + tasks.size() + " tasks in the list.\n");
                }
            }
        }

        System.out.println("\tBye!! See you again soon!\n");
    }
}
