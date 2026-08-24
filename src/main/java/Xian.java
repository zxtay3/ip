import java.io.IOException;
import java.time.format.DateTimeParseException;

public class Xian {

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
                    String command = Parser.getCommandWord(input);
                    String remainder = Parser.getArguments(input);

                    if(command.equals("mark")){
                        int idx = Parser.parseIndex(remainder);

                        if(idx < 1 || idx > tasks.getSize()){
                            throw new XianException("Hello?! Please enter a valid item to mark  >:(");
                        }

                        Task t = tasks.get(idx - 1);
                        t.mark();
                        storage.save(tasks);

                        ui.showTaskMark(t);
                    }else if(command.equals("unmark")){
                        int idx = Parser.parseIndex(remainder);

                        if(idx < 1 || idx > tasks.getSize()){
                            throw new XianException("Hello?! Please enter a valid item to unmark  >:(");
                        }

                        Task t = tasks.get(idx - 1);
                        t.unmark();
                        storage.save(tasks);

                        ui.showTaskUnmark(t);
                    }else if(command.equals("delete")){
                        int idx = Parser.parseIndex(remainder);

                        if(idx < 1 || idx > tasks.getSize()){
                            throw new XianException("Hello?! Please enter a valid item to delete  >:(");
                        }

                        Task t = tasks.delete(idx);
                        storage.save(tasks);

                        ui.showTaskDelete(t, tasks);
                    }else{
                        Task t;

                        switch (command) {
                            case "todo" -> t = Parser.parseTodo(remainder);
                            case "deadline" -> t = Parser.parseDeadline(remainder);
                            case "event" -> t = Parser.parseEvent(remainder);
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
