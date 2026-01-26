import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Storage {
    private final Path filePath;

    public Storage(String relativePath) {
        this.filePath = Paths.get(relativePath);
    }

    public ArrayList<Task> load() throws HardenException {
        ArrayList<Task> tasks = new ArrayList<>();

        if (!Files.exists(filePath)) {
            // file doesn't exist yet -> first run, return empty list
            return tasks;
        }

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                lineNo++;
                line = line.trim();
                if (line.isEmpty()) continue;
                tasks.add(parseLine(line, lineNo));
            }
        } catch (IOException e) {
            throw new HardenException("Failed to read data file: " + e.getMessage());
        }

        return tasks;
    }

    public void save(ArrayList<Task> tasks) throws HardenException {
        try {
            // ensure folder exists
            Path parent = filePath.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }

            try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
                for (Task t : tasks) {
                    bw.write(t.serialize());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw new HardenException("Failed to save data file: " + e.getMessage());
        }
    }

    // Format (example):
    // T | 1 | read book
    // D | 0 | return book | Sunday
    // E | 0 | meeting | Mon 2pm | 4pm
    private Task parseLine(String line, int lineNo) throws HardenException {
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) {
            throw new HardenException("Corrupted data file at line " + lineNo + ": " + line);
        }

        String type = parts[0];
        String doneStr = parts[1];

        boolean done;
        if (doneStr.equals("1")) done = true;
        else if (doneStr.equals("0")) done = false;
        else throw new HardenException("Corrupted done flag at line " + lineNo + ": " + line);

        if (type.equals("T")) {
            String desc = parts[2];
            ToDo t = new ToDo(desc);
            if (done) t.markDone();
            return t;
        }

        if (type.equals("D")) {
            if (parts.length < 4) {
                throw new HardenException("Corrupted deadline at line " + lineNo + ": " + line);
            }
            String desc = parts[2];
            String by = parts[3];
            Deadline d = new Deadline(desc, by);
            if (done) d.markDone();
            return d;
        }

        if (type.equals("E")) {
            if (parts.length < 5) {
                throw new HardenException("Corrupted event at line " + lineNo + ": " + line);
            }
            String desc = parts[2];
            String from = parts[3];
            String to = parts[4];
            Event e = new Event(desc, from, to);
            if (done) e.markDone();
            return e;
        }

        throw new HardenException("Unknown task type at line " + lineNo + ": " + line);
    }
}
