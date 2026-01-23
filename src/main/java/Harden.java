import java.util.Scanner;

public class Harden {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println("Hello! I'm Harden");
        System.out.println("What can I do for you?");

        while (true) {
            String input = scanner.nextLine();

            try {
                // handle command; update taskCount if needed
                taskCount = handleInput(input, tasks, taskCount);

                if (input.equals("bye")) {
                    break;
                }
            } catch (HardenException e) {
                System.out.println("OOPS!!! " + e.getMessage());
            } catch (Exception e) {
                // optional: catch unexpected bugs so program doesn't crash
                System.out.println("OOPS!!! Something went wrong: " + e.getMessage());
            }
        }
    }

    private static int handleInput(String input, Task[] tasks, int taskCount) throws HardenException {
        input = input.trim();

        if (input.equals("bye")) {
            System.out.println("Bye. Hope to see you again soon!");
            return taskCount;
        }

        if (input.equals("list")) {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < taskCount; i++) {
                System.out.println((i + 1) + ". " + tasks[i]);
                //println(Object) == println(Object.toString())
            }
            return taskCount;
        }

        if (input.startsWith("mark ")) {
            int idx = parseTaskNumber(input, "mark");
            ensureInRange(idx, taskCount);
            tasks[idx].markDone();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + tasks[idx]);
            return taskCount;
        }

        if (input.startsWith("unmark ")) {
            int idx = parseTaskNumber(input, "unmark");
            ensureInRange(idx, taskCount);
            tasks[idx].markNotDone();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + tasks[idx]);
            return taskCount;
        }

        // ---- Level 4 commands ----
        if (input.startsWith("todo")) {
            String desc = input.length() > 4 ? input.substring(4).trim() : "";
            if (desc.isEmpty()) {
                throw new HardenException("The description of a todo cannot be empty.");
            }
            tasks[taskCount] = new ToDo(desc);
            printAdded(tasks[taskCount], taskCount + 1);
            return taskCount + 1;
        }

        if (input.startsWith("deadline")) {
            String rest = input.length() > 8 ? input.substring(8).trim() : ""; // after "deadline"
            if (rest.isEmpty()) {
                throw new HardenException("The description of a deadline cannot be empty.");
            }

            int byIndex = rest.indexOf("/by");
            if (byIndex == -1) {
                throw new HardenException("Deadline must have /by. Example: deadline return book /by Sunday");
            }

            String desc = rest.substring(0, byIndex).trim();
            String by = rest.substring(byIndex + 3).trim();

            if (desc.isEmpty()) {
                throw new HardenException("The description of a deadline cannot be empty.");
            }
            if (by.isEmpty()) {
                throw new HardenException("The /by part of a deadline cannot be empty.");
            }

            tasks[taskCount] = new Deadline(desc, by);
            printAdded(tasks[taskCount], taskCount + 1);
            return taskCount + 1;
        }

        if (input.startsWith("event")) {
            String rest = input.length() > 5 ? input.substring(5).trim() : ""; // after "event"
            if (rest.isEmpty()) {
                throw new HardenException("The description of an event cannot be empty.");
            }

            int fromIndex = rest.indexOf("/from");
            int toIndex = rest.indexOf("/to");
            if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
                throw new HardenException("Event must have /from and /to. Example: event meeting /from Mon 2pm /to 4pm");
            }

            String desc = rest.substring(0, fromIndex).trim();
            String from = rest.substring(fromIndex + 5, toIndex).trim();
            String to = rest.substring(toIndex + 3).trim();

            if (desc.isEmpty()) throw new HardenException("The description of an event cannot be empty.");
            if (from.isEmpty()) throw new HardenException("The /from part of an event cannot be empty.");
            if (to.isEmpty()) throw new HardenException("The /to part of an event cannot be empty.");

            tasks[taskCount] = new Event(desc, from, to);
            printAdded(tasks[taskCount], taskCount + 1);
            return taskCount + 1;
        }

        // if we reach here, it's an unknown command
        throw new HardenException("I'm sorry, but I don't know what that means.");
    }

    private static int parseTaskNumber(String input, String command) throws HardenException {
        // expects: "mark 2" or "unmark 2"
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 2) {
            throw new HardenException("Usage: " + command + " <task number>");
        }

        try {
            int num = Integer.parseInt(parts[1]);
            if (num <= 0) throw new NumberFormatException();
            return num - 1; // convert to 0-based index
        } catch (NumberFormatException e) {
            throw new HardenException("Task number must be a positive integer.");
        }
    }

    private static void ensureInRange(int idx, int taskCount) throws HardenException {
        if (taskCount == 0) {
            throw new HardenException("There are no tasks yet.");
        }
        if (idx < 0 || idx >= taskCount) {
            throw new HardenException("Task number is out of range.");
        }
    }

    private static void printAdded(Task task, int newCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + newCount + " tasks in the list.");
    }
}
