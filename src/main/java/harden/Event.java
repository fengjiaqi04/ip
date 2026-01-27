package harden;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task spanning a start and end date/time.
 */
public class Event extends Task {

    private final LocalDate fromDate;
    private final LocalTime fromTime;
    private final LocalDate toDate;
    private final LocalTime toTime;

    /**
     * Constructs an event task.
     *
     * @param description Event description.
     * @param fromDate Start date.
     * @param fromTime Start time.
     * @param toDate End date.
     * @param toTime End time.
     */
    public Event(String description, LocalDate fromDate, LocalTime fromTime,
                 LocalDate toDate, LocalTime toTime) {
        super(description);
        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.toDate = toDate;
        this.toTime = toTime;
    }

    /**
     * Formats the start date/time for display.
     *
     * @return Formatted start date/time string.
     */
    private String formatFrom() {
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MMM dd yyyy");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("h:mma");
        return fromDate.format(dateFmt) + " " + fromTime.format(timeFmt).toLowerCase();
    }

    /**
     * Formats the end date/time for display.
     *
     * @return Formatted end date/time string.
     */
    private String formatTo() {
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MMM dd yyyy");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("h:mma");
        return toDate.format(dateFmt) + " " + toTime.format(timeFmt).toLowerCase();
    }

    /**
     * Returns the save-file representation of this event.
     *
     * @return Serialized form for storage.
     */
    @Override
    public String serialize() {
        // ISO formats for easy parsing later
        return "E | " + (isDone ? "1" : "0") + " | " + description
                + " | " + fromDate + " | " + fromTime
                + " | " + toDate + " | " + toTime;
    }

    /**
     * Returns the display string for this event.
     *
     * @return User-facing task string.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + formatFrom()
                + " to: " + formatTo() + ")";
    }
}
