package xian;

/**
 * Deals with formatting responses and displaying messages to the user.
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
    /**
     * Returns a formatted welcome banner and greeting message.
     *
     * @return The formatted welcome response.
     */
    public String formatWelcomeMessage() {
        return BANNER
                + "Hello, I'm "
                + BOT_NAME
                + "!\n"
                + "What can I do for you today?\n";
    }

    /**
     * Returns a formatted message confirming that a task has been marked as done.
     *
     * @param task The task that was marked.
     * @return The formatted task-marking response.
     */
    public String formatTaskMark(Task task) {
        return "\tOK, I've marked this task as done: \n"
                + "\t "
                + task
                + "\n";
    }

    /**
     * Returns a formatted message confirming that a task has been marked as not done.
     *
     * @param task The task that was unmarked.
     * @return The formatted task-unmarking response.
     */
    public String formatTaskUnmark(Task task) {
        return "\tOK, I've marked this task as not done yet: \n"
                + "\t "
                + task
                + "\n";
    }

    /**
     * Returns a formatted message confirming that a task has been deleted,
     * along with the updated number of tasks remaining.
     *
     * @param task The task that was deleted.
     * @param tasks The task list after deletion.
     * @return The formatted task-deletion response.
     */
    public String formatTaskDelete(Task task, TaskList tasks) {
        return "\tNoted!! I have deleted the item from the list\n"
                + "\t "
                + task
                + "\n"
                + "\tNow you have "
                + tasks.getSize()
                + " tasks in the list\n";
    }

    /**
     * Returns a formatted message confirming that a task has been added,
     * along with the updated number of tasks in the list.
     *
     * @param task The task that was added.
     * @param tasks The task list after addition.
     * @return The formatted task-addition response.
     */
    public String formatTaskAdded(Task task, TaskList tasks) {
        return "\tGot it. I've added this task: \n"
                + "\t "
                + task
                + "\n"
                + "\tNow you have "
                + tasks.getSize()
                + " tasks in the list.\n";
    }

    /**
     * Returns a formatted message confirming that a task has been updated.
     *
     * @param task the task after its details were updated.
     * @return the formatted task-update response.
     */
    public String formatTaskUpdated(Task task) {
        return "\tI've updated this task:\n"
                + "\t "
                + task
                + "\n";
    }

    /**
     * Returns a formatted list of every task currently in the given task list.
     *
     * @param tasks The task list to format.
     * @return The formatted task-list response.
     */
    public String formatTaskList(TaskList tasks) {
        return formatTasksWithHeader(tasks, "\tHere are the task in your list:\n");
    }

    /**
     * Returns a formatted list of the tasks whose descriptions match the search keyword.
     *
     * @param matchingTasks The tasks to include in the formatted response.
     * @return The formatted matching-tasks response.
     */
    public String formatMatchingTasks(TaskList matchingTasks) {
        if (matchingTasks.getSize() == 0) {
            return "\tThere are no tasks that fit the description :(";
        }

        return formatTasksWithHeader(
                matchingTasks,
                "\tHere are the matching tasks in your list:\n");
    }

    /**
     * Returns a numbered list of the given tasks preceded by the supplied header.
     *
     * @param tasks the tasks to include in the response.
     * @param header the header to place before the numbered tasks.
     * @return the formatted task-list response.
     */
    private String formatTasksWithHeader(TaskList tasks, String header) {
        StringBuilder response = new StringBuilder(header);

        for (int i = 0; i < tasks.getSize(); i++) {
            response.append("\t")
                    .append(i + 1)
                    .append(". ")
                    .append(tasks.get(i))
                    .append("\n");
        }

        return response.toString();
    }

}
