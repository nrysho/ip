import java.util.*;

public class Dickie {
    private static ArrayList<Task> TASKLIST = new ArrayList<>();

    public static void main(String[] args) {
        greet();
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();
        InputCommand inputCommand;

        while (!(message.equals("bye"))) {
            inputCommand = ParseInput.getInputCommandType(message, TASKLIST.size());
            String[] description = ParseInput.getDescription(message);

            switch (inputCommand) {
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
                    try {
                        TaskType taskType = ParseInput.getTaskType(description);
                        Task newTask;
                        if (taskType == TaskType.TODO) {
                            newTask = new Todo(message);
                            addTask(newTask);
                        } else if (taskType == TaskType.EVENT) {
                            try {
                                String[] details = ParseInput.getEventDetails(message);
                                newTask = new Event(details[0], details[1], details[2]);
                                addTask(newTask);
                            } catch (IllegalArgumentException e) {
                                System.out.println("try again! use \"/from\" and \"/to\" to indicate the duration of the event!");
                            }
                        } else if (taskType == TaskType.DEADLINE) {
                            try {
                                String[] details = ParseInput.getDeadlineDetails(message);
                                newTask = new Deadline(details[0], details[1]);
                                addTask(newTask);
                            } catch (IllegalArgumentException e) {
                                System.out.println("try again! use \"/by\" to indicate the deadline of this task!");
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("invalid task type, try again! :)");
                    }


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

        public static void bye() {
            String greeting = "   Byee. See ya~\n";
            System.out.println(greeting);
        }

        public static void list() {
            int number = 1;
            for (Task task : TASKLIST) {
                System.out.println("   " + number + ". " + task.toString());
                number++;
            }
        }

        public static void addTask(Task task){
            TASKLIST.add(task);
            System.out.println("   okie, I've added the task: \n" +
                               "     " + task.toString() +
                               "\n   you now have " + TASKLIST.size() + " tasks in your list.");
        }

        public static void mark(String taskNumber){
            int taskPos = Integer.parseInt(taskNumber) - 1; // zero based indexing
            Task taskToMark = TASKLIST.get(taskPos);
            taskToMark.mark();
        }

        public static void unmark(String taskNumber){
            int taskPos = Integer.parseInt(taskNumber) - 1; // zero based indexing
            Task taskToMark = TASKLIST.get(taskPos);
            taskToMark.unmark();
        }
}
