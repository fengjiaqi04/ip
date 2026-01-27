package harden;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * Adds a {@link Deadline} task to the task list.
 * Expected input format: {@code deadline <desc> /by <yyyy-mm-dd [HHmm]>}
 */
public class DeadlineCommand extends Command {

    private final String description;
    private final String by;

    /**
     * Creates a DeadlineCommand.
     *
     * @param description Description of the deadline task.
     * @param by Due date/time string (e.g. "2024-06-06" or "2024-06-06 1400").
     */
    public DeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HardenException {
        if (description == null || description.trim().isEmpty()) {
            throw new HardenException("OOPS!! The description of a deadline cannot be empty.");
        }
        if (by == null || by.trim().isEmpty()) {
            throw new HardenException("OOPS!! A deadline must have a /by date.");
        }

        LocalDate date;
        LocalTime time;

        String trimmedBy = by.trim();

        try {
            // Accept either:
            // 1) "yyyy-mm-dd"
            // 2) "yyyy-mm-dd HHmm"
            String[] parts = trimmedBy.split("\\s+", 2);

            date = LocalDate.parse(parts[0]);

            if (parts.length == 2) {
                String t = parts[1].trim(); // "1400"
                if (!t.matches("\\d{4}")) {
                    throw new HardenException("OOPS!! Time must be in HHmm format, e.g. 1400.");
                }
                int hour = Integer.parseInt(t.substring(0, 2));
                int minute = Integer.parseInt(t.substring(2, 4));
                time = LocalTime.of(hour, minute);
            } else {
                // If no time provided, default to end of day
                time = LocalTime.of(23, 59);
            }

        } catch (DateTimeParseException e) {
            throw new HardenException("OOPS!! Date must be in yyyy-mm-dd format, e.g. 2024-06-06.");
        } catch (NumberFormatException e) {
            throw new HardenException("OOPS!! Time must be in HHmm format, e.g. 1400.");
        } catch (RuntimeException e) {
            // catches invalid time like 2560, etc.
            throw new HardenException("OOPS!! Invalid date/time.");
        }

        Task task = new Deadline(description.trim(), date, time);
        tasks.add(task);

        storage.save(tasks.getTasks().toArray(new Task[0]), tasks.size());

        ui.showLine();
        ui.showMessage("Got it. I've added this task:");
        ui.showMessage("  " + task);
        ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
        ui.showLine();
    }
}
