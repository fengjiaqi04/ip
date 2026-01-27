package harden;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task with a due date and time.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter DATE_OUT = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter TIME_OUT = DateTimeFormatter.ofPattern("HHmm");

    private final LocalDate byDate;
    private final LocalTime byTime;

    /**
     * Creates a new deadline task (not done by default).
     *
     * @param description Description of the deadline task.
     * @param byDate Due date.
     * @param byTime Due time.
     */
    public Deadline(String description, LocalDate byDate, LocalTime byTime) {
        super(description);
        this.byDate = byDate;
        this.byTime = byTime;
    }

    /**
     * Creates a new deadline task with explicit done status.
     *
     * @param description Description of the deadline task.
     * @param isDone Done status.
     * @param byDate Due date.
     * @param byTime Due time.
     */
    public Deadline(String description, boolean isDone, LocalDate byDate, LocalTime byTime) {
        super(description, isDone);
        this.byDate = byDate;
        this.byTime = byTime;
    }

    private String formatBy() {
        // You can change this display format if your course requires something else.
        return byDate + " " + byTime;
    }

    @Override
    public String serialize() {
        // Format: D | 0/1 | desc | yyyy-MM-dd | HHmm
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | "
                + byDate.format(DATE_OUT) + " | " + byTime.format(TIME_OUT);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + formatBy() + ")";
    }
}
