package dickie.utils;
import dickie.exception.DickieException;
import dickie.task.*;

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
 * Handles all file I/O operations for the dickie application.
 * Responsible for loading tasks from disk on startup and saving tasks to disk
 * when they are modified.
 */
public class Storage {
    private final String filePath;
    private ArrayList<Task> tasks;  // Store tasks in Storage

    /**
     * Creates a Storage instance with the specified file path.
     *
     * @param filePath The path to the data file
     */
    public Storage(String filePath) {
        this.filePath = filePath; // Trust that this filePath will be valid since it's hardcoded to be
                                  // ./data/dickie.txt
        this.tasks = new ArrayList<>();
    }

    /**
     * Checks if the data file exists at the given path, creating it if it does not.
     *
     * @param path The path to the data file
     * @return true if the file already existed, false if it was newly created
     */
    public boolean ensureFileExists(Path path) {
        try {
            if (!Files.exists(path)) {
                Files.createFile(Paths.get(filePath));
                return false; // if file did not exist before
            } else {
                return true; // if file existed before
            }
        } catch (IOException e) {
            System.out.println("error at Storage::ensureFileExists(Path) method: " + e.getMessage());
            return false;
        }
    }

    /**
     * Loads tasks from file specified in filePath when chatbot starts
     *
     * @return ArrayList containing all tasks in file in filePath, or empty list if file doesn't exist
     */
    public ArrayList<Task> load() {
        tasks = new ArrayList<>();

        // Check if file path exists, and file exists in filePath
        Path path = Paths.get(filePath);
        // Check if parent directory exists. If not, create new directory.
        ensureFileDirectoryExists(path);
        // If file does not exist yet, create path
        boolean existed = ensureFileExists(path);

        // If new file was created, return empty arraylist
        if (!existed) {
            return tasks;
        }

        // Try to read the file
        try (Scanner s = new Scanner(new File(filePath))) {
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
     * Checks if the parent directory of the given path exists, creating it if it does not.
     *
     * @param path The path whose parent directory should be checked
     */
    private void ensureFileDirectoryExists(Path path) {
        try {
            Path parent = path.getParent(); // returns null if no parent. path should return ./data
            if (parent != null && !Files.exists(parent)) { // if path has parent, and it does not exist yet,
                                                           // create parent directories
                Files.createDirectories(parent);
            }
        } catch (IOException e) {
            System.out.println("Error creating directory: " + e.getMessage());
        }
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
        String[] splitString = fileString.split(" \\| ");
        assert splitString.length >= 4 : "Corrupted file format: " + fileString;
        String taskType = splitString[0].trim();
        String description = splitString[2].trim();
        boolean isMarked = Objects.equals(splitString[1], "X");

        return switch (taskType) {
            case "T" -> {
                assert splitString.length == 4 : "Todo must have 4 fields (including priority tag)";
                yield new Todo(description, strToPriority(splitString[3].trim()), isMarked);
            }
            case "D" -> {
                assert splitString.length == 5 : "Deadline must have 5 fields";
                yield new Deadline(description, splitString[3].trim(), strToPriority(splitString[4].trim()), isMarked);
            }
            case "E" -> {
                assert splitString.length == 6 : "Event must have 6 fields";
                yield new Event(description, splitString[3].trim(), splitString[4].trim(),
                        strToPriority(splitString[5].trim()), isMarked);
            }
            default -> throw new DickieException("Error in when parsing file: Invalid task type.");
        };
    }

    /**
     * Converts a priority string from the data file into a Priority enum value.
     *
     * @param str The priority string to convert, expected to be LOW, MEDIUM, or HIGH
     * @return The corresponding Priority enum value
     * @throws DickieException If the string does not match a valid priority value
     */
    public static Priority strToPriority(String str) throws DickieException {
        assert str.equals("LOW") || str.equals("MEDIUM") || str.equals("HIGH");
        return switch (str) {
            case "LOW" -> Priority.LOW;
            case "MEDIUM" -> Priority.MEDIUM;
            case "HIGH" -> Priority.HIGH;
            default -> throw new DickieException("invalid priority syntax");
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
     * Writes tasks to file
     *
     * @param tasks The list of tasks to write to the file
     */
    private void saveTasksToFile(ArrayList<Task> tasks) {
        try {
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
}

