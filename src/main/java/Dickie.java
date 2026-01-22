import java.util.*;

public class Dickie {
    static ArrayList<String> TASKLIST = new ArrayList<>();

    public static void main(String[] args) {
        greet();
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();

        while (!message.equals("bye")) {
            if (message.equals("list")) {
                list();
                message = scanner.nextLine();
            } else {
                addTask(message);
                message = scanner.nextLine();
            }
        }
        bye();
    }

    public static void greet() {
        String greeting = "   Hey gorlll I'm Dickie\n" +
                "   What can I do for you?";
        System.out.println(greeting);
    }

    public static void bye () {
        String greeting = "   Byee. See ya~\n";
        System.out.println(greeting);
    }

    public static void list() {
        int number = 1;
        for (String task: TASKLIST) {
            System.out.println("   " + number + ". " + task);
            number++;
        }
    }

    public static void addTask(String task) {
        TASKLIST.add(task);
        System.out.println("   okie added: " + task);
    }
}
