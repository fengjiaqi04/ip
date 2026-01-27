package harden;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Manages loading and saving tasks to a file.
 */

public class Storage {
    private final Path filePath;
    /**
     * Constructs a Storage instance with a given file path.
     *
     *
     */
    public Storage(String relativePath) {
        this.filePath = Paths.get(relativePath);
    }

    public void save(Task[] tasks, int taskCount) throws HardenException {
        try {
            Path parent = filePath.getParent();
            if (parent != null) Files.createDirectories(parent);

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
     * Loads tasks from disk.
     *
     * @return Array of tasks.
     * @throws HardenException If loading fails.
     */
    public Task[] load() throws HardenException {
        Task[] tasks = new Task[100];

        if (!Files.exists(filePath)) {
            return tasks; // first run: file not created yet
        }

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            int idx = 0;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                tasks[idx++] = Parser.deserialize(line);
            }
            return tasks;
        } catch (IOException e) {
            throw new HardenException("Failed to load: " + e.getMessage());
        }
    }
}
