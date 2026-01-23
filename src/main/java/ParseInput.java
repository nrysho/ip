public class ParseInput {
    public static String[] getDescription(String message) {
        return message.split("\\s+");
    }

    public static TaskType getTaskType(String[] description) {
        String taskName = description[0];
        if (taskName.equals("todo")) {
            return TaskType.TODO;
        } else if (taskName.equals("deadline")) {
            return TaskType.DEADLINE;
        } else if (taskName.equals("event")) {
            return TaskType.EVENT;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static String[] getDeadlineDetails(String message) {
        int byIndex = message.indexOf("/by");
        String taskDeadline = message.substring(byIndex + 3).trim();
        // if substring " /by " does not exist, or if there are no words after "/by", throw exception
        if (!message.matches(".*\\s" + "/by" + "\\s.*") || taskDeadline.isBlank()) {
            throw new IllegalArgumentException();
        }

        String taskName = message.substring(0, byIndex).trim();

        return new String[]{taskName, taskDeadline};
    }

    public static String[] getEventDetails(String message) {
        // if substring " /from " or " /to " does not exist, throw exception
        if (!message.matches(".*\\s" + "/from" + "\\s.*") ||
                !message.matches(".*\\s" + "/to" + "\\s.*")) {
            throw new IllegalArgumentException();
        }
        int fromIndex = message.indexOf("/from");
        int toIndex = message.indexOf("/to");
        String taskName = message.substring(0, fromIndex).trim();
        String from = message.substring(fromIndex + 5, toIndex).trim();
        String to = message.substring(toIndex + 3).trim();

        return new String[]{taskName, from, to};
    }
    public static InputCommand getInputCommandType(String message, int noOfTasks) {

        String[] splitMessage = message.split("\\s+");

        if (message.isEmpty()) {
            return InputCommand.VOID;
        }
        if (message.equals("list")) {
            return InputCommand.LIST;
        }
        if (splitMessage.length == 2 && isNumeric(splitMessage[1]))  {
            int num = Integer.parseInt(splitMessage[1]);
            if ((num < 1) || (num > noOfTasks)) {
                System.out.println("invalid task number: mark or unmark, try again!");
                return InputCommand.VOID;
            }
            if (splitMessage[0].equals("mark")) {
                return InputCommand.MARK;
            } else if (splitMessage[0].equals("unmark")) {
                return InputCommand.UNMARK;
            }
        }
        return InputCommand.ADDTASK;
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
