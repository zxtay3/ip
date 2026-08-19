import java.util.ArrayList;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Xian {
    public static void main(String[] args) {
        String banner = " __  __ ___    _    _   _ \n"
                + " \\ \\/ /|_ _|  / \\  | \\ | |\n"
                + "  \\  /  | |  / _ \\ |  \\| |\n"
                + "  /  \\  | | / ___ \\| |\\  |\n"
                + " /_/\\_\\|___/_/   \\_\\_| \\_|\n";
        String botName = "XIAN";
        List<String> tasks = new ArrayList<>();

        System.out.println(banner);
        System.out.println("Hello, I'm " + botName + "!");
        System.out.println("What can I do for you today?\n");

        Scanner scanner = new Scanner(System.in);

        while(true){
            String input = scanner.nextLine();
            System.out.println();

            if (input.equals("bye")){
                break;
            }

            if(input.equals("list")){
                for (int i = 0; i < tasks.size(); i++){
                    System.out.println("\t" + (i+1) + ". " + tasks.get(i));
                }
                System.out.println();
            }else {
                tasks.add(input);
                System.out.println("\t" + "added: " + input + "\n");
            }
        }

        System.out.println("\tBye!! See you again soon!\n");
    }
}
