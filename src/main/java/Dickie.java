import java.util.*;

public class Dickie {
    private static ArrayList<Task> TASKLIST = new ArrayList<>();


    public static void main(String[] args) {
        greet();
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();
        TaskType taskType;

        while (!(message.equals("bye"))) {
            taskType = ParseInput.parse(message, TASKLIST.size());
            String[] description = ParseInput.getDescription(message);

            switch (taskType) {
                case LIST:
                    list();
                    break;
                case MARK:
                    mark(description[1]);
                    break;
                case UNMARK:
                    unmark(description[1]);
                    break;
                case ADDTASK:
                    Task newTask = new Task(message);
                    addTask(newTask);
                    break;
                case VOID:
                    break;
            }

            message = scanner.nextLine();
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
        for (Task task: TASKLIST) {
            System.out.println("   " + number + ". " + task.getStatusIcon()
                                + " " + task.toString());
            number++;
        }
    }

    public static void addTask(Task task) {
        TASKLIST.add(task);
        System.out.println("   okie added: " + task.toString());
    }

    public static void mark(String taskNumber) {
        int taskPos = Integer.parseInt(taskNumber) - 1; // zero based indexing
        Task taskToMark = TASKLIST.get(taskPos);
        taskToMark.mark();
    }

    public static void unmark(String taskNumber) {
        int taskPos = Integer.parseInt(taskNumber) - 1; // zero based indexing
        Task taskToMark = TASKLIST.get(taskPos);
        taskToMark.unmark();
    }
}
