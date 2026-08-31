package xian;

import java.util.Scanner;

/**
 * Deals with interactions with the user, including displaying messages
 * and reading commands from standard input.
 */
public class Ui {

    private static final String BANNER = """
             __  __ ___    _    _   _\s
             \\ \\/ /|_ _|  / \\  | \\ | |
              \\  /  | |  / _ \\ |  \\| |
              /  \\  | | / ___ \\| |\\  |
             /_/\\_\\|___/_/   \\_\\_| \\_|
            """;
    private static final String BOT_NAME = "XIAN";
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Reads a single line of command input from the user.
     *
     * @return the raw command entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays the welcome banner and greeting message.
     */
    public void welcomeMessage() {
        System.out.println(BANNER);
        System.out.println("Hello, I'm " + BOT_NAME + "!");
        System.out.println("What can I do for you today?\n");
    }

    /**
     * Displays a message confirming that a task has been marked as done.
     *
     * @param task The task that was marked.
     */
    public void showTaskMark(Task task) {
        System.out.println("\tNice! I've marked this task as done: ");
        System.out.println("\t " + task + "\n");
    }

    /**
     * Displays a message confirming that a task has been marked as not done.
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmark(Task task) {
        System.out.println("\tOK, I've marked this task as not done yet: ");
        System.out.println("\t " + task + "\n");
    }

    /**
     * Displays a message confirming that a task has been deleted,
     * along with the updated number of tasks remaining.
     *
     * @param task The task that was deleted.
     * @param tasks The task list after deletion.
     */
    public void showTaskDelete(Task task, TaskList tasks) {
        System.out.println("\tNoted!! I have deleted the item from the list");
        System.out.println("\t " + task + "\n");
        System.out.println("\tNow you have " + tasks.getSize() + " tasks in the list\n");
    }

    /**
     * Displays a message confirming that a task has been added,
     * along with the updated number of tasks in the list.
     *
     * @param task The task that was added.
     * @param tasks The task list after addition.
     */
    public void showTaskAdded(Task task, TaskList tasks) {
        System.out.println("\tGot it. I've added this task: ");
        System.out.println("\t " + task);
        System.out.println("\tNow you have " + tasks.getSize() + " tasks in the list.\n");
    }

    /**
     * Displays every task currently in the given task list.
     *
     * @param tasks The task list to display.
     */
    public void showTaskList(TaskList tasks) {
        System.out.println("\tHere are the task in your list:");
        for (int i = 0; i < tasks.getSize(); i++) {
            System.out.println("\t" + (i + 1) + ". " + tasks.get(i));
        }
        System.out.println();
    }

    /**
     * Displays the tasks whose descriptions match the search keyword.
     *
     * @param matchingTasks The tasks that match the search keyword.
     */
    public void showMatchingTasks(TaskList matchingTasks) {
        if (matchingTasks.getSize() == 0) {
            System.out.println("\tThere are no tasks that fit the description :(");
            return;
        }

        System.out.println("\tHere are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.getSize(); i++) {
            System.out.println("\t" + (i + 1) + ". " + matchingTasks.get(i));
        }
        System.out.println();
    }

    /**
     * Displays the farewell message shown when the program exits.
     */
    public void showEnd() {
        System.out.println("\tBye!! See you again soon!\n");
    }
}
