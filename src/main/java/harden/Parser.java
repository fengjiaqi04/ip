package harden;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Parses user input into executable {@link Command} objects.
 * Also supports deserializing saved task lines into {@link Task} objects.
 */
public class Parser {

    /**
     * Parses a user input line into a {@link Command}.
     *
     * @param input Full user input.
     * @return A command object representing the user intent.
     * @throws HardenException If the command format is invalid or unknown.
     */
    public static Command parse(String input) throws HardenException {
        if (input == null) {
            throw new HardenException("Please enter a command.");
        }

        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            throw new HardenException("Please enter a command.");
        }

        String keyword;
        String rest;

        int firstSpace = trimmed.indexOf(' ');
        if (firstSpace == -1) {
            keyword = trimmed;
            rest = "";
        } else {
            keyword = trimmed.substring(0, firstSpace).trim();
            rest = trimmed.substring(firstSpace + 1).trim();
        }

        switch (keyword) {
        case "bye":
            return new ByeCommand();

        case "list":
            return new ListCommand();

        case "todo":
            if (rest.isBlank()) {
                throw new HardenException("Usage: todo <description>");
            }
            return new TodoCommand(rest);

        case "deadline":
            // deadline <desc> /by <...>
            if (rest.isBlank()) {
                throw new HardenException("Usage: deadline <description> /by <date time>");
            }
            String[] byParts = rest.split("\\s+/by\\s+", 2);
            if (byParts.length < 2 || byParts[0].trim().isEmpty() || byParts[1].trim().isEmpty()) {
                throw new HardenException("Usage: deadline <description> /by <date time>");
            }
            return new DeadlineCommand(byParts[0].trim(), byParts[1].trim());

        case "event":
            // event <desc> /from <...> /to <...>
            if (rest.isBlank()) {
                throw new HardenException("Usage: event <description> /from <start> /to <end>");
            }
            String[] fromParts = rest.split("\\s+/from\\s+", 2);
            if (fromParts.length < 2 || fromParts[0].trim().isEmpty() || fromParts[1].trim().isEmpty()) {
                throw new HardenException("Usage: event <description> /from <start> /to <end>");
            }
            String desc = fromParts[0].trim();

            String[] toParts = fromParts[1].trim().split("\\s+/to\\s+", 2);
            if (toParts.length < 2 || toParts[0].trim().isEmpty() || toParts[1].trim().isEmpty()) {
                throw new HardenException("Usage: event <description> /from <start> /to <end>");
            }
            String from = toParts[0].trim();
            String to = toParts[1].trim();
            return new EventCommand(desc, from, to);

        case "find":
            if (rest.isBlank()) {
                throw new HardenException("Usage: find <keyword>");
            }
            return new FindCommand(rest);

        case "mark": {
            int index0 = parseOneBasedIndex(rest, "mark");
            return new MarkCommand(index0);
        }

        case "unmark": {
            int index0 = parseOneBasedIndex(rest, "unmark");
            return new UnmarkCommand(index0);
        }

        default:
            throw new HardenException("OOPS!! I'm sorry, but I don't know what that means.");
        }
    }

    /**
     * Deserializes a saved line from storage into a {@link Task}.
     *
     * Expected formats (with " | " separators):
     * <pre>
     * T | 0/1 | description
     * D | 0/1 | description | yyyy-MM-dd | HH:mm
     * E | 0/1 | description | fromDate | fromTime | toDate | toTime
     * </pre>
     *
     * @param line One line from the save file.
     * @return The reconstructed Task object.
     * @throws HardenException If the line is invalid or cannot be parsed.
     */
    public static Task deserialize(String line) throws HardenException {
        if (line == null || line.trim().isEmpty()) {
            throw new HardenException("Corrupted save data: empty line.");
        }

        // Split by " | " with optional surrounding spaces
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) {
            throw new HardenException("Corrupted save data: " + line);
        }

        String type = parts[0].trim();
        String doneFlag = parts[1].trim();
        String desc = parts[2].trim();

        boolean isDone = "1".equals(doneFlag);

        switch (type) {
        case "T": {
            ToDo t = new ToDo(desc);
            if (isDone) {
                t.markDone();
            }
            return t;
        }

        case "D": {
            if (parts.length < 5) {
                throw new HardenException("Corrupted deadline data: " + line);
            }
            LocalDate date = parseDate(parts[3].trim(), line);
            LocalTime time = parseTime(parts[4].trim(), line);

            // If your Deadline constructor supports (desc, date, time)
            Deadline d = new Deadline(desc, date, time);
            if (isDone) {
                d.markDone();
            }
            return d;
        }

        case "E": {
            if (parts.length < 7) {
                throw new HardenException("Corrupted event data: " + line);
            }
            LocalDate fromDate = parseDate(parts[3].trim(), line);
            LocalTime fromTime = parseTime(parts[4].trim(), line);
            LocalDate toDate = parseDate(parts[5].trim(), line);
            LocalTime toTime = parseTime(parts[6].trim(), line);

            // If your Event constructor supports (desc, fromDate, fromTime, toDate, toTime)
            Event e = new Event(desc, fromDate, fromTime, toDate, toTime);
            if (isDone) {
                e.markDone();
            }
            return e;
        }

        default:
            throw new HardenException("Corrupted save data: unknown task type: " + type);
        }
    }

    // ---------------- helpers ----------------

    /**
     * Parses a 1-based index string and returns 0-based index.
     */
    private static int parseOneBasedIndex(String rest, String cmd) throws HardenException {
        if (rest == null || rest.trim().isEmpty()) {
            throw new HardenException("Usage: " + cmd + " <task number>");
        }
        int oneBased;
        try {
            oneBased = Integer.parseInt(rest.trim());
        } catch (NumberFormatException e) {
            throw new HardenException("Task number must be an integer.");
        }
        if (oneBased <= 0) {
            throw new HardenException("Task number must be 1 or bigger.");
        }
        return oneBased - 1;
    }

    private static LocalDate parseDate(String s, String originalLine) throws HardenException {
        try {
            return LocalDate.parse(s); // expects yyyy-MM-dd
        } catch (Exception e) {
            throw new HardenException("Corrupted date in save data: " + originalLine);
        }
    }

    /**
     * Parses time. Preferred save format is HH:mm (e.g., 18:00).
     * If your saved time is HHmm (e.g., 1800), convert it before saving,
     * or extend this method to handle HHmm.
     */
    private static LocalTime parseTime(String s, String originalLine) throws HardenException {
        try {
            return LocalTime.parse(s); // expects HH:mm
        } catch (Exception e) {
            throw new HardenException("Corrupted time in save data: " + originalLine);
        }
    }
}
