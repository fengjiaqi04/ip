package harden;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Adds an event task to the task list.
 * Expected input format (from Parser):
 *   description: event description text
 *   from: "yyyy-MM-dd HHmm"
 *   to:   "yyyy-MM-dd HHmm"
 */
public class EventCommand extends Command {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HHmm");

    private final String description;
    private final String from;
    private final String to;

    /**
     * Constructs an EventCommand.
     *
     * @param description Description of the event.
     * @param from From date/time string (yyyy-MM-dd HHmm).
     * @param to To date/time string (yyyy-MM-dd HHmm).
     */
    public EventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HardenException {
        if (description == null || description.trim().isEmpty()) {
            throw new HardenException("OOPS!! The description of an event cannot be empty.");
        }
        if (from == null || from.trim().isEmpty() || to == null || to.trim().isEmpty()) {
            throw new HardenException("OOPS!! The event must have /from and /to.");
        }

        LocalDate fromDate;
        LocalTime fromTime;
        LocalDate toDate;
        LocalTime toTime;

        try {
            String[] fromParts = from.trim().split("\\s+");
            String[] toParts = to.trim().split("\\s+");

            if (fromParts.length != 2 || toParts.length != 2) {
                throw new HardenException("OOPS!! Use format: /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm");
            }

            fromDate = LocalDate.parse(fromParts[0], DATE_FMT);
            fromTime = LocalTime.parse(fromParts[1], TIME_FMT);

            toDate = LocalDate.parse(toParts[0], DATE_FMT);
            toTime = LocalTime.parse(toParts[1], TIME_FMT);
        } catch (HardenException e) {
            throw e;
        } catch (Exception e) {
            throw new HardenException("OOPS!! Invalid date/time. Use: yyyy-MM-dd HHmm (e.g. 2019-12-06 1400)");
        }

        Task task = new Event(description.trim(), fromDate, fromTime, toDate, toTime);
        tasks.add(task);

        storage.save(tasks.getTasks().toArray(new Task[0]), tasks.size());

        ui.showLine();
        ui.showMessage("Got it. I've added this task:");
        ui.showMessage("  " + task);
        ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
        ui.showLine();
    }
}
