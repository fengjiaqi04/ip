package harden;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Handles loading tasks from and saving tasks to a file on disk.
 */
public class Storage {

    private static final int MAX_TASKS = 100;

    /** Path to the save file. */
    private final Path filePath;

    /**
     * Creates a {@code Storage} object that reads from and writes to the given file path.
     *
     * @param relativePath Relative path to the save file
     */
    public Storage(String relativePath) {
        this.filePath = Paths.get(relativePath);
    }

    /**
     * Saves the given tasks into the save file.
     * Only the first {@code taskCount} tasks in the array will be written.
     *
     * @param tasks Array containing tasks to be saved
     * @param taskCount Number of valid tasks in the array
     * @throws HardenException If an I/O error occurs while saving tasks
     */
    public void save(Task[] tasks, int taskCount) throws HardenException {
        if (tasks == null) {
            throw new HardenException("Failed to save: tasks cannot be null.");
        }
        if (taskCount < 0 || taskCount > tasks.length) {
            throw new HardenException("Failed to save: invalid task count.");
        }

        try {
            createParentDirectoriesIfNeeded();

            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                for (int i = 0; i < taskCount; i++) {
                    writer.write(tasks[i].serialize());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new HardenException("Failed to save: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the save file into an array.
     * If the save file does not exist, an empty task array is returned.
     *
     * @return Array of tasks loaded from disk (capacity {@value #MAX_TASKS})
     * @throws HardenException If an I/O error occurs while loading tasks
     */
    public Task[] load() throws HardenException {
        Task[] tasks = new Task[MAX_TASKS];

        if (!Files.exists(filePath)) {
            return tasks;
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            int idx = 0;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                if (idx >= tasks.length) {
                    throw new HardenException("Save file has more than " + MAX_TASKS + " tasks.");
                }
                tasks[idx++] = Parser.deserialize(line);
            }
            return tasks;
        } catch (IOException e) {
            throw new HardenException("Failed to load: " + e.getMessage());
        }
    }

    private void createParentDirectoriesIfNeeded() throws IOException {
        Path parent = filePath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
    }
}
