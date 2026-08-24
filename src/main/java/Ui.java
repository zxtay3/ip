import java.util.Scanner;

public class Ui{

    private final Scanner scanner = new Scanner(System.in);
    private static final String banner = """
             __  __ ___    _    _   _\s
             \\ \\/ /|_ _|  / \\  | \\ | |
              \\  /  | |  / _ \\ |  \\| |
              /  \\  | | / ___ \\| |\\  |
             /_/\\_\\|___/_/   \\_\\_| \\_|
            """;
    private static final String BOT_NAME = "XIAN";

    public String readCommand(){
        return scanner.nextLine();
    }

    public void welcomeMessage(){
        System.out.println(banner);
        System.out.println("Hello, I'm " + BOT_NAME + "!");
        System.out.println("What can I do for you today?\n");
    }

    public void showTaskMark(Task t){
        System.out.println("\tNice! I've marked this task as done: ");
        System.out.println("\t " + t + "\n");
    }

    public void showTaskUnmark(Task t){
        System.out.println("\tOK, I've marked this task as not done yet: ");
        System.out.println("\t " + t + "\n");
    }

    public void showTaskDelete(Task t, TaskList tasks){
        System.out.println("\tNoted!! I have deleted the item from the list");
        System.out.println("\t " + t + "\n");
        System.out.println("\tNow you have " + tasks.getSize() + " tasks in the list\n");
    }

    public void showTaskAdded(Task t, TaskList tasks){
        System.out.println("\tGot it. I've added this task: ");
        System.out.println("\t " + t);
        System.out.println("\tNow you have " + tasks.getSize() + " tasks in the list.\n");
    }

    public void showTaskList(TaskList tasks){
        System.out.println("\tHere are the task in your list:");
        for (int i = 0; i < tasks.getSize(); i++){
            System.out.println("\t" + (i+1) + ". " + tasks.get(i));
        }
        System.out.println();
    }

    public void showEnd(){
        System.out.println("\tBye!! See you again soon!\n");
    }
}
