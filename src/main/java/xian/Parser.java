package xian;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Deals with interpreting raw user input into structured commands and task data.
 * This class performs no side effects (no printing, no saving) — it only
 * parses strings and returns objects, or throws an exception if the input
 * is malformed.
 */
public class Parser {

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Returns the command word (the first token) from the given user input.
     *
     * @param input the raw user input.
     * @return the command word.
     */
    public static String getCommandWord(String input) {
        return input.split(" ", 2)[0];
    }

    /**
     * Returns the arguments portion of the given user input, i.e. everything
     * after the command word.
     *
     * @param input the raw user input.
     * @return the arguments portion of the input.
     * @throws XianException if no arguments are provided after the command word.
     */
    public static String getArguments(String input) throws XianException {
        String[] parts = input.split(" ", 2);

        if (parts.length < 2 || parts[1].isBlank()) {
            throw new XianException("Please remember to state task!! >:(");
        }

        return parts[1];
    }

    /**
     * Parses the given string into a task index.
     *
     * @param remainder the string containing the task index.
     * @return the parsed index.
     * @throws NumberFormatException if the string cannot be parsed as an integer.
     */
    public static int parseIndex(String remainder) throws NumberFormatException {
        return Integer.parseInt(remainder);
    }

    /**
     * Parses a date and time using the application's supported format.
     *
     * @param dateTime the date and time to parse.
     * @return the parsed date and time.
     * @throws DateTimeParseException if the date and time has an invalid format.
     */
    public static LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DATE_TIME_FORMAT);
    }

    /**
     * Creates a Todo task from the given arguments.
     *
     * @param remainder the description of the todo task.
     * @return the created Todo task.
     */
    public static Task parseTodo(String remainder) {
        return new Todo(remainder);
    }

    /**
     * Parses the index, field, and value supplied to an update command.
     *
     * @param remainder the arguments supplied to the update command.
     * @return the update index, field, and value in separate array elements.
     * @throws XianException if the update arguments are incomplete.
     */
    public static String[] parseUpdate(String remainder) throws XianException {
        String[] updateParts = remainder.split(" ", 3);

        if (updateParts.length != 3 || updateParts[0].isBlank()
                || updateParts[1].isBlank() || updateParts[2].isBlank()) {
            throw new XianException("Please ensure the update format is correct!! >:(");
        }

        return updateParts;
    }

    /**
     * Splits task arguments and validates that the expected number of non-blank parts is present.
     *
     * @param remainder the task arguments to split.
     * @param splitRegex the regular expression used to split the arguments.
     * @param expectedParts the number of parts required by the task format.
     * @return the validated task argument parts.
     * @throws XianException if the arguments do not contain the expected parts.
     */
    private static String[] splitAndValidateTaskArguments(String remainder,
            String splitRegex, int expectedParts) throws XianException {
        String[] taskParts = remainder.split(splitRegex);

        if (taskParts.length != expectedParts) {
            throw new XianException("Please ensure the format of the task is correct!! >:(");
        }

        for (String taskPart : taskParts) {
            if (taskPart.isBlank()) {
                throw new XianException("Please ensure the format of the task is correct!! >:(");
            }
        }

        return taskParts;
    }

    /**
     * Creates a Deadline task from the given arguments, which should be in the
     * format {@code <description> /by <d/M/yyyy HHmm>}.
     *
     * @param remainder the arguments containing the description and due date/time.
     * @return the created Deadline task.
     * @throws XianException if the arguments are not in the expected format.
     */
    public static Task parseDeadline(String remainder) throws XianException {
        String[] taskDate = splitAndValidateTaskArguments(remainder, " /by ", 2);

        LocalDateTime by = parseDateTime(taskDate[1]);
        return new Deadline(taskDate[0], by);
    }

    /**
     * Creates an Event task from the given arguments, which should be in the
     * format {@code <description> /from <d/M/yyyy HHmm> /to <d/M/yyyy HHmm>}.
     *
     * @param remainder the arguments containing the description, start time, and end time.
     * @return the created Event task.
     * @throws XianException if the arguments are not in the expected format.
     */
    public static Task parseEvent(String remainder) throws XianException {
        String[] taskDate = splitAndValidateTaskArguments(remainder, " /from | /to ", 3);

        LocalDateTime from = parseDateTime(taskDate[1]);
        LocalDateTime to = parseDateTime(taskDate[2]);

        return new Event(taskDate[0], from, to);
    }
}
