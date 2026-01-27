package harden;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a start (from) date/time and end (to) date/time.
 */
public class Event extends Task {
    private static final DateTimeFormatter DATE_OUT = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter TIME_OUT = DateTimeFormatter.ofPattern("HHmm");

    private final LocalDate fromDate;
    private final LocalTime fromTime;
    private final LocalDate toDate;
    private final LocalTime toTime;

    /**
     * Creates a new event task (not done by default).
     *
     * @param description Description of the event.
     * @param fromDate Start date.
     * @param fromTime Start time.
     * @param toDate End date.
     * @param toTime End time.
     */
    public Event(String description, LocalDate fromDate, LocalTime fromTime, LocalDate toDate, LocalTime toTime) {
        super(description);
        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.toDate = toDate;
        this.toTime = toTime;
    }

    /**
     * Creates a new event task with explicit done status.
     *
     * @param description Description of the event.
     * @param isDone Done status.
     * @param fromDate Start date.
     * @param fromTime Start time.
     * @param toDate End date.
     * @param toTime End time.
     */
    public Event(String description, boolean isDone,
                 LocalDate fromDate, LocalTime fromTime,
                 LocalDate toDate, LocalTime toTime) {
        super(description, isDone);
        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.toDate = toDate;
        this.toTime = toTime;
    }

    private String formatRange() {
        return fromDate + " " + fromTime + " to " + toDate + " " + toTime;
    }

    @Override
    public String serialize() {
        // Format: E | 0/1 | desc | yyyy-MM-dd | HHmm | yyyy-MM-dd | HHmm
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | "
                + fromDate.format(DATE_OUT) + " | " + fromTime.format(TIME_OUT) + " | "
                + toDate.format(DATE_OUT) + " | " + toTime.format(TIME_OUT);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + formatRange() + ")";
    }
}
