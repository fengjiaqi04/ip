import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Deadline extends Task {
    private final LocalDate byDate;
    private final LocalTime byTime; // can be null

    private static final DateTimeFormatter OUT_DATE = DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter IN_TIME = DateTimeFormatter.ofPattern("HHmm");
    private static final DateTimeFormatter OUT_TIME = DateTimeFormatter.ofPattern("h:mma", Locale.ENGLISH);

    public Deadline(String description, LocalDate byDate, LocalTime byTime) {
        super(description);
        this.byDate = byDate;
        this.byTime = byTime;
    }

    public LocalDate getByDate() {
        return byDate;
    }

    public LocalTime getByTime() {
        return byTime;
    }

    private String formatBy() {
        if (byTime == null) {
            return byDate.format(OUT_DATE);
        }
        return byDate.format(OUT_DATE) + " " + byTime.format(OUT_TIME).toLowerCase();
    }

    @Override
    public String serialize() {
        String timeStr = (byTime == null) ? "-" : byTime.format(IN_TIME);
        return "D | " + (isDone ? 1 : 0) + " | " + description + " | " + byDate + " | " + timeStr;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + formatBy() + ")";
    }
}
