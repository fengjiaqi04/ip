import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Parser {

    private static final DateTimeFormatter IN_TIME = DateTimeFormatter.ofPattern("HHmm");

    public static Task parseToTask(String input) throws HardenException {
        input = input.trim();

        if (input.startsWith("todo")) {
            String desc = input.substring(4).trim();
            if (desc.isEmpty()) throw new HardenException("The description of a todo cannot be empty.");
            return new ToDo(desc);
        }

        if (input.startsWith("deadline")) {
            String rest = input.substring(8).trim();
            if (rest.isEmpty()) throw new HardenException("The description of a deadline cannot be empty.");

            String[] parts = rest.split("\\s+/by\\s+", 2);
            if (parts.length < 2) throw new HardenException("Deadline must have /by <yyyy-mm-dd> [HHmm].");

            String desc = parts[0].trim();
            String byRaw = parts[1].trim();
            if (desc.isEmpty()) throw new HardenException("The description of a deadline cannot be empty.");
            if (byRaw.isEmpty()) throw new HardenException("Deadline /by cannot be empty.");

            DateTimeParts by = parseDateTimeParts(byRaw);
            return new Deadline(desc, by.date, by.time);
        }

        if (input.startsWith("event")) {
            String rest = input.substring(5).trim();
            if (rest.isEmpty()) throw new HardenException("The description of an event cannot be empty.");

            String[] p1 = rest.split("\\s+/from\\s+", 2);
            if (p1.length < 2) throw new HardenException("Event must have /from and /to.");

            String desc = p1[0].trim();
            String[] p2 = p1[1].split("\\s+/to\\s+", 2);
            if (p2.length < 2) throw new HardenException("Event must have /to.");

            String fromRaw = p2[0].trim();
            String toRaw = p2[1].trim();

            if (desc.isEmpty()) throw new HardenException("The description of an event cannot be empty.");

            DateTimeParts from = parseDateTimeParts(fromRaw);
            DateTimeParts to = parseDateTimeParts(toRaw);

            return new Event(desc, from.date, from.time, to.date, to.time);
        }

        if (input.equals("list") || input.equals("bye") ||
                input.startsWith("mark ") || input.startsWith("unmark ") || input.startsWith("delete ")) {
            throw new HardenException("This should not be parsed as a task command: " + input);
        }

        throw new HardenException("I'm sorry, but I don't know what that means :-(");
    }

    // ===== Level 7/8: deserialize save line into Task =====
    public static Task deserialize(String line) throws HardenException {
        // Formats:
        // T | 1 | read book
        // D | 0 | return book | 2019-12-02 | -
        // D | 0 | return book | 2019-12-02 | 1800
        // E | 0 | meeting | 2019-12-06 | - | 2019-12-06 | -
        // E | 0 | meeting | 2019-12-06 | 1400 | 2019-12-06 | 1600

        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) throw new HardenException("Corrupted save line: " + line);

        String type = parts[0].trim();
        boolean done = parts[1].trim().equals("1");
        String desc = parts[2].trim();

        Task t;

        if (type.equals("T")) {
            t = new ToDo(desc);

        } else if (type.equals("D")) {
            if (parts.length < 5) throw new HardenException("Corrupted deadline line: " + line);
            LocalDate d = LocalDate.parse(parts[3].trim());
            LocalTime tm = parseOptionalTime(parts[4].trim());
            t = new Deadline(desc, d, tm);

        } else if (type.equals("E")) {
            if (parts.length < 7) throw new HardenException("Corrupted event line: " + line);
            LocalDate fd = LocalDate.parse(parts[3].trim());
            LocalTime ft = parseOptionalTime(parts[4].trim());
            LocalDate td = LocalDate.parse(parts[5].trim());
            LocalTime tt = parseOptionalTime(parts[6].trim());
            t = new Event(desc, fd, ft, td, tt);

        } else {
            throw new HardenException("Unknown task type in save file: " + type);
        }

        if (done) t.markDone();
        return t;
    }

    // ===== helpers =====
    private static class DateTimeParts {
        LocalDate date;
        LocalTime time; // nullable
        DateTimeParts(LocalDate d, LocalTime t) { date = d; time = t; }
    }

    private static DateTimeParts parseDateTimeParts(String raw) throws HardenException {
        // Accept:
        // yyyy-mm-dd
        // yyyy-mm-dd HHmm
        String[] toks = raw.trim().split("\\s+");
        try {
            LocalDate d = LocalDate.parse(toks[0]);
            LocalTime t = null;
            if (toks.length >= 2) {
                t = LocalTime.parse(toks[1], IN_TIME);
            }
            return new DateTimeParts(d, t);
        } catch (Exception e) {
            throw new HardenException("Invalid date/time. Use yyyy-mm-dd or yyyy-mm-dd HHmm.");
        }
    }

    private static LocalTime parseOptionalTime(String token) throws HardenException {
        token = token.trim();
        if (token.equals("-") || token.isEmpty()) return null;
        try {
            return LocalTime.parse(token, IN_TIME);
        } catch (Exception e) {
            throw new HardenException("Corrupted time in save file: " + token);
        }
    }
}
