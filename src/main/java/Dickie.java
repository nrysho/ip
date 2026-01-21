import java.util.Scanner;

public class Dickie {
    public static void main(String[] args) {
        greet();
        echo();;
    }

    public static void greet() {
        String greeting = "____________________________________________________________\n" +
                " Hey gorlll I'm Dickie\n" +
                " What can I do for you?\n" +
                "____________________________________________________________";
        System.out.println(greeting);
    }

    public static void echo() {
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();
        while (!message.equals("bye")) {
            System.out.println("____________________________________________________________\n " +
                    message + "\n" +
                    "____________________________________________________________");
            message = scanner.nextLine();
        }

        System.out.println("____________________________________________________________\n" +
        " Byee. See ya~\n" +
        "____________________________________________________________\n");
        scanner.close();
    }
}
