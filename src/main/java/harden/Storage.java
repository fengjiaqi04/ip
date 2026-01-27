package harden;

import java.io.*;
import java.util.ArrayList;

/**
 * Manages loading and saving tasks to a file.
 */
public class Storage {

    private final String filePath;

    /**
     * Constructs a Storage instance with a given file path.
     *
     * @param filePath Save file path.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from disk.
     *
     * @return Array of tasks.
     * @throws HardenException If loading fails.
     */
    public Task[] load() throws HardenException {
        File file = new File(filePath);
        if (!file.exists()) {
            return new Task[0];
        }
        return new Task[0];
    }

    /**
     * Saves tasks to disk.
     *
     * @param tasks Task list to save.
     * @throws HardenException If writing fails.
     */
    public void save(ArrayList<Task> tasks) throws HardenException {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            PrintWriter pw = new PrintWriter(file);
            for (Task t : tasks) {
                pw.println(t.serialize());
            }
            pw.close();
        } catch (IOException e) {
            throw new HardenException("Error saving tasks.");
        }
    }
}
