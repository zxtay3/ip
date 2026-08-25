package xian;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parser{

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public static String getCommandWord(String input){
        return input.split(" ", 2)[0];
    }

    public static String getArguments(String input) throws XianException{
        String[] parts = input.split(" ", 2);

        if (parts.length < 2 || parts[1].isBlank()){
            throw new XianException("Please remember to state task!! >:(");
        }

        return parts[1];
    }

    public static int parseIndex(String remainder) throws NumberFormatException{
        return Integer.parseInt(remainder);
    }

    public static Task parseTodo(String remainder){
        return new Todo(remainder);
    }

    public static Task parseDeadline(String remainder) throws XianException{
        String[] taskDate = remainder.split(" /by ");

        if (taskDate.length != 2 || taskDate[0].isBlank() || taskDate[1].isBlank()){
            throw new XianException("Please ensure the format of the task is correct!! >:(");
        }

        LocalDateTime by = LocalDateTime.parse(taskDate[1], DATE_TIME_FORMAT);
        return new Deadline(taskDate[0], by);
    }

    public static Task parseEvent(String remainder) throws XianException{
        String[] taskDate = remainder.split(" /from | /to ");

        if (taskDate.length != 3 || taskDate[0].isBlank() || taskDate[1].isBlank() || taskDate[2].isBlank()){
            throw new XianException("Please ensure the format of the task is correct!! >:(");
        }

        LocalDateTime from = LocalDateTime.parse(taskDate[1], DATE_TIME_FORMAT);
        LocalDateTime to = LocalDateTime.parse(taskDate[2], DATE_TIME_FORMAT);

        return new Event(taskDate[0], from, to);
    }
}