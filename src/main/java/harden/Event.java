package harden;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Event extends Task {
    private final LocalDate fromDate;
    private final LocalTime fromTime; // can be null
    private final LocalDate toDate;
    private final LocalTime toTime;   // can be null

    private static final DateTimeFormatter OUT_DATE = DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter IN_TIME = DateTimeFormatter.ofPattern("HHmm");
    private static final DateTimeFormatter OUT_TIME = DateTimeFormatter.ofPattern("h:mma", Locale.ENGLISH);

    public Event(String description, LocalDate fromDate, LocalTime fromTime, LocalDate toDate, LocalTime toTime) {
        super(description);
        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.toDate = toDate;
        this.toTime = toTime;
    }

    private String fmt(LocalDate d, LocalTime t) {
        if (t == null) {
            return d.format(OUT_DATE);
        }
        return d.format(OUT_DATE) + " " + t.format(OUT_TIME).toLowerCase();
    }

    @Override
    public String serialize() {
        String fTime = (fromTime == null) ? "-" : fromTime.format(IN_TIME);
        String tTime = (toTime == null) ? "-" : toTime.format(IN_TIME);
        return "E | " + (isDone ? 1 : 0) + " | " + description
                + " | " + fromDate + " | " + fTime
                + " | " + toDate + " | " + tTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + fmt(fromDate, fromTime)
                + " to: " + fmt(toDate, toTime) + ")";
    }
}
