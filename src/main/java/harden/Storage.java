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
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
                for (int i = 0; i < taskCount; i++) {
                    bw.write(tasks[i].serialize());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw new HardenException("Failed to save: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the save file into an array.
     * <p>
     * If the save file does not exist, an empty task array is returned.
     *
     * @return Array of tasks loaded from disk
     * @throws HardenException If an I/O error occurs while loading tasks
     */
    public Task[] load() throws HardenException {
        Task[] tasks = new Task[100];

        if (!Files.exists(filePath)) {
            return tasks;
        }

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            int idx = 0;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                tasks[idx++] = Parser.deserialize(line);
            }
            return tasks;
        } catch (IOException e) {
            throw new HardenException("Failed to load: " + e.getMessage());
        }
    }
}
