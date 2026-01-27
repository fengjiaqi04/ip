package harden;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that must be completed by a specific date and time.
 */
public class Deadline extends Task {

    private final LocalDate byDate;
    private final LocalTime byTime;

    /**
     * Constructs a deadline task.
     *
     * @param description Task description.
     * @param byDate Due date.
     * @param byTime Due time.
     */
    public Deadline(String description, LocalDate byDate, LocalTime byTime) {
        super(description);
        this.byDate = byDate;
        this.byTime = byTime;
    }

    /**
     * Formats the deadline date/time for display.
     *
     * @return A formatted due date/time string.
     */
    private String formatBy() {
        // Example output: Dec 03 2019 6:00pm
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MMM dd yyyy");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("h:mma");
        return byDate.format(dateFmt) + " " + byTime.format(timeFmt).toLowerCase();
    }

    /**
     * Returns the save-file representation of this deadline.
     *
     * @return Serialized form for storage.
     */
    @Override
    public String serialize() {
        // Use ISO format so parsing is easy: 2019-12-03 and 18:00
        return "D | " + (isDone ? "1" : "0") + " | " + description
                + " | " + byDate + " | " + byTime;
    }

    /**
     * Returns the display string for this deadline.
     *
     * @return User-facing task string.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + formatBy() + ")";
    }
}
