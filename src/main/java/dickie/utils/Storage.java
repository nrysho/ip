package dickie.utils;
import dickie.exception.DickieException;
import dickie.task.Deadline;
import dickie.task.Event;
import dickie.task.Task;
import dickie.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles all file I/O operations for the dickie.Dickie application.
 * Responsible for loading tasks from disk on startup and saving tasks to disk
 * when they are modified.
 */
public class Storage {
    private static final String DEFAULT_FILE_PATH = "./data/dickie.Dickie.txt";
    private final String filePath;
    private ArrayList<Task> tasks;  // Store tasks in Storage

    /**
     * Creates a Storage instance with the specified file path.
     * If the provided file path is null or empty, uses the default path.
     *
     * @param filePath The path to the data file
     */
    public Storage(String filePath) {
        this.filePath = (filePath != null && !filePath.isEmpty()) ? filePath : DEFAULT_FILE_PATH;
        this.tasks = new ArrayList<>();
    }

    /**
     * Load tasks from file when chatbot starts
     *
     * @return ArrayList containing all loaded tasks, or empty list if file doesn't exist
     */
    public ArrayList<Task> load() {
        tasks = new ArrayList<>();
        File file = new File(filePath);

        // If file doesn't exist, return empty list (normal startup)
        if (!file.exists()) {
            System.out.println("No existing data file found. Starting fresh.");
            return tasks;
        }

        // Try to read the file
        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                try {
                    String nextLine = s.nextLine().trim();
                    if (!nextLine.isEmpty()) {
                        Task task = parseTask(nextLine);
                        tasks.add(task);
                    }
                } catch (DickieException e) {
                    System.out.println("Skipping invalid line: " + e.getMessage());
                }
            }

        } catch (FileNotFoundException e) {
            // This should rarely happen since we checked file.exists()
            System.out.println("File disappeared while reading: " + e.getMessage());
            System.out.println("Starting with empty task list.");
        } catch (Exception e) {
            // Catch any other unexpected errors
            System.out.println("Unexpected error loading file: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Parses a single line from the data file into a Task object.
     * Expected formats:
     * - Todo: "T | X | description"
     * - Deadline: "D | X | description | deadline"
     * - Event: "E | X | description | from | to"
     *
     * @param fileString The line from the data file to parse
     * @return A Task object corresponding to the file string
     * @throws DickieException If the file string has invalid format or unknown task type
     */
    public static Task parseTask(String fileString) throws DickieException {
        String[] splitString = fileString.split("\\|");
        String taskType = splitString[0].trim();
        boolean marked = Objects.equals(splitString[1], "X");

        return switch (taskType) {
            case "T" -> new Todo(splitString[2], marked);
            case "D" -> new Deadline(splitString[2], splitString[3], marked);
            case "E" -> new Event(splitString[2], splitString[3], splitString[4], marked);
            default -> throw new DickieException("Error when parsing file");
        };
    }

    /**
     * Save a TaskList object to the data file.
     * This method extracts tasks from the TaskList and writes them to disk.
     *
     * @param taskList The TaskList containing tasks to save
     */
    public void save(TaskList taskList) {
        ArrayList<Task> tasks = taskList.getTasks();
        saveTasksToFile(tasks);
    }

    /**
     * Internal method that actually writes to file
     *
     * @param tasks The list of tasks to write to the file
     */
    private void saveTasksToFile(ArrayList<Task> tasks) {
        try {
            // Ensure directory exists
            ensureDataDirectoryExists();

            FileWriter writer = new FileWriter(filePath);

            for (Task task : tasks) {
                // Convert task to file format
                String fileString = task.toFileString();
                writer.write(fileString + System.lineSeparator());
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Ensure data directory exists before writing
     * Creates the directory and any necessary parent directories if they don't exist
     */
    private void ensureDataDirectoryExists() {
        try {
            Path path = Paths.get(filePath);
            Path parent = path.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
        } catch (IOException e) {
            System.out.println("Error creating directory: " + e.getMessage());
        }
    }
}
