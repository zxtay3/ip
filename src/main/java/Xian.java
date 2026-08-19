import java.util.Scanner;

public class Xian {
    public static void main(String[] args) {
        String banner = " __  __ ___    _    _   _ \n"
                + " \\ \\/ /|_ _|  / \\  | \\ | |\n"
                + "  \\  /  | |  / _ \\ |  \\| |\n"
                + "  /  \\  | | / ___ \\| |\\  |\n"
                + " /_/\\_\\|___/_/   \\_\\_| \\_|\n";
        String botName = "XIAN";
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

            System.out.println("\t" + input + "\n");
        }

        System.out.println("\tBye!! See you again soon!\n");
    }
}
