package harden;

import java.time.LocalDate;
import java.time.LocalTime;

public class Parser {

    public static Command parse(String input) throws HardenException {
        if (input == null) {
            throw new HardenException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }

        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            throw new HardenException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }

        String[] parts = trimmed.split("\\s+", 2);
        String commandWord = parts[0].toLowerCase();
        String rest = (parts.length < 2) ? "" : parts[1];

        switch (commandWord) {
        case "bye":
            return new ByeCommand();
        case "list":
            return new ListCommand();
        case "todo":
            if (rest.trim().isEmpty()) {
                throw new HardenException("OOPS!!! The description of a todo cannot be empty.");
            }
            return new TodoCommand(rest);
        default:
            throw new HardenException("OOPS!!! This command is not refactored yet: " + commandWord);
        }
    }

    /**
     * Loads one line from harden.txt into a harden.Task.
     * Supports BOTH older (date-only) and newer (date+time) formats.
     */
    public static Task deserialize(String line) throws HardenException {
        try {
            if (line == null || line.trim().isEmpty()) {
                throw new HardenException("Corrupted save file: empty line.");
            }

            String[] parts = line.split("\\s*\\|\\s*");
            if (parts.length < 3) {
                throw new HardenException("Corrupted save file: " + line);
            }

            String type = parts[0].trim();
            String doneFlag = parts[1].trim();
            String desc = parts[2].trim();

            Task task;

            switch (type) {
            case "T":
                task = new ToDo(desc);
                break;

            case "D":
                // Possible:
                // D | done | desc | yyyy-MM-dd
                // D | done | desc | yyyy-MM-dd | HHmm
                // D | done | desc | HHmm | yyyy-MM-dd
                if (parts.length < 4) {
                    throw new HardenException("Corrupted save file (deadline): " + line);
                }

                LocalDate byDate;
                LocalTime byTime = LocalTime.MIDNIGHT;

                if (parts.length == 4) {
                    // date-only
                    byDate = parseDate(parts[3]);
                } else {
                    String a = parts[3].trim();
                    String b = parts[4].trim();

                    if (looksLikeDate(a) && looksLikeTime(b)) {
                        byDate = parseDate(a);
                        byTime = parseTime(b);
                    } else if (looksLikeTime(a) && looksLikeDate(b)) {
                        byTime = parseTime(a);
                        byDate = parseDate(b);
                    } else if (looksLikeDate(a)) {
                        // fallback: treat a as date, ignore b
                        byDate = parseDate(a);
                    } else {
                        throw new HardenException("Corrupted save file (deadline datetime): " + line);
                    }
                }

                task = new Deadline(desc, byDate, byTime);
                break;

            case "E":
                // Possible:
                // E | done | desc | fromDate | toDate
                // E | done | desc | fromDate | fromTime | toDate | toTime
                // (and sometimes time/date swapped per pair)
                if (parts.length < 5) {
                    throw new HardenException("Corrupted save file (event): " + line);
                }

                LocalDate fromDate;
                LocalTime fromTime = LocalTime.MIDNIGHT;
                LocalDate toDate;
                LocalTime toTime = LocalTime.MIDNIGHT;

                if (parts.length == 5) {
                    // date-only
                    fromDate = parseDate(parts[3]);
                    toDate = parseDate(parts[4]);
                } else if (parts.length >= 7) {
                    // date+time pairs (allow swapped within each pair)
                    // pair1: parts[3], parts[4]
                    // pair2: parts[5], parts[6]
                    LocalDateTimePair p1 = parseDateTimePair(parts[3], parts[4]);
                    LocalDateTimePair p2 = parseDateTimePair(parts[5], parts[6]);

                    fromDate = p1.date;
                    fromTime = p1.time;
                    toDate = p2.date;
                    toTime = p2.time;
                } else {
                    throw new HardenException("Corrupted save file (event datetime): " + line);
                }

                task = new Event(desc, fromDate, fromTime, toDate, toTime);
                break;

            default:
                throw new HardenException("Corrupted save file (unknown task type): " + type);
            }

            if ("1".equals(doneFlag)) {
                task.markDone(); // ✅ matches your harden.Task.java
            }

            return task;

        } catch (HardenException e) {
            throw e;
        } catch (Exception e) {
            // Wrap DateTimeParseException or any unexpected parsing issue
            throw new HardenException("Corrupted save file line: " + line);
        }
    }

    // ---------- helpers ----------

    private static boolean looksLikeDate(String s) {
        String t = s.trim();
        return t.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    private static boolean looksLikeTime(String s) {
        String t = s.trim();
        return t.matches("\\d{4}") || t.matches("\\d{2}:\\d{2}");
    }

    private static LocalDate parseDate(String s) {
        return LocalDate.parse(s.trim()); // yyyy-MM-dd
    }

    private static LocalTime parseTime(String s) {
        String t = s.trim();
        if (t.matches("\\d{4}")) {
            // "1400" -> 14:00
            int hh = Integer.parseInt(t.substring(0, 2));
            int mm = Integer.parseInt(t.substring(2, 4));
            return LocalTime.of(hh, mm);
        }
        // "14:00"
        return LocalTime.parse(t);
    }

    private static class LocalDateTimePair {
        final LocalDate date;
        final LocalTime time;

        LocalDateTimePair(LocalDate d, LocalTime t) {
            this.date = d;
            this.time = t;
        }
    }

    private static LocalDateTimePair parseDateTimePair(String aRaw, String bRaw) throws HardenException {
        String a = aRaw.trim();
        String b = bRaw.trim();

        if (looksLikeDate(a) && looksLikeTime(b)) {
            return new LocalDateTimePair(parseDate(a), parseTime(b));
        }
        if (looksLikeTime(a) && looksLikeDate(b)) {
            return new LocalDateTimePair(parseDate(b), parseTime(a));
        }
        throw new HardenException("Corrupted save file (datetime pair): " + aRaw + " | " + bRaw);
    }
}
