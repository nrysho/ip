
public class ParseInput {
    public static String[] getDescription(String message) {
        return message.split("\\s+");
    }

    public static TaskType parse(String message, int noOfTasks) {

        String[] splitMessage = message.split("\\s+");

        if (message.isEmpty()) {
            return TaskType.VOID;
        }
        if (message.equals("list")) {
            return TaskType.LIST;
        }
        if (splitMessage.length == 2 && isNumeric(splitMessage[1]))  {
            int num = Integer.parseInt(splitMessage[1]);
            if ((num < 1) || (num > noOfTasks)) {
                System.out.println("invalid task number: mark or unmark, try again!");
                return TaskType.VOID;
            }
            if (splitMessage[0].equals("mark")) {
                return TaskType.MARK;
            } else if (splitMessage[0].equals("unmark")) {
                return TaskType.UNMARK;
            }
        }
        return TaskType.ADDTASK;
    }

    public static boolean isNumeric(String message) {
        if (message == null) {
            return false;
        }
        try {
            Double.parseDouble(message);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
