import java.util.ArrayList;
import java.util.Scanner;

public class Harden {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage("data/duke.txt");

        ArrayList<Task> tasks;
        try {
            tasks = storage.load();
        } catch (HardenException e) {
            System.out.println("OOPS!!! " + e.getMessage());
            tasks = new ArrayList<>();
        }

        System.out.println("Hello! I'm Harden");
        System.out.println("What can I do for you?");

        while (true) {
            String input = scanner.nextLine();

            try {
                input = input.trim();

                if (input.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                } else if (input.equals("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                } else if (input.startsWith("mark ")) {
                    int idx = parseIndex(input, "mark");
                    ensureInRange(idx, tasks.size());
                    tasks.get(idx).markDone();

                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks.get(idx));

                    storage.save(tasks);
                } else if (input.startsWith("unmark ")) {
                    int idx = parseIndex(input, "unmark");
                    ensureInRange(idx, tasks.size());
                    tasks.get(idx).markNotDone();

                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks.get(idx));

                    storage.save(tasks);
                } else if (input.startsWith("delete ")) {
                    int idx = parseIndex(input, "delete");
                    ensureInRange(idx, tasks.size());

                    Task removed = tasks.remove(idx);

                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + removed);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");

                    storage.save(tasks);
                } else if (input.startsWith("todo")) {
                    String desc = input.length() > 4 ? input.substring(4).trim() : "";
                    if (desc.isEmpty()) throw new HardenException("The description of a todo cannot be empty.");
                    Task t = new ToDo(desc);
                    tasks.add(t);
                    printAdded(t, tasks.size());
                    storage.save(tasks);
                } else if (input.startsWith("deadline")) {
                    String rest = input.length() > 8 ? input.substring(8).trim() : "";
                    if (rest.isEmpty()) throw new HardenException("The description of a deadline cannot be empty.");

                    int byIdx = rest.indexOf("/by");
                    if (byIdx == -1) {
                        throw new HardenException("Deadline must have /by. Example: deadline return book /by Sunday");
                    }

                    String desc = rest.substring(0, byIdx).trim();
                    String by = rest.substring(byIdx + 3).trim();

                    if (desc.isEmpty()) throw new HardenException("The description of a deadline cannot be empty.");
                    if (by.isEmpty()) throw new HardenException("The /by part of a deadline cannot be empty.");

                    Task t = new Deadline(desc, by);
                    tasks.add(t);
                    printAdded(t, tasks.size());
                    storage.save(tasks);
                } else if (input.startsWith("event")) {
                    String rest = input.length() > 5 ? input.substring(5).trim() : "";
                    if (rest.isEmpty()) throw new HardenException("The description of an event cannot be empty.");

                    int fromIdx = rest.indexOf("/from");
                    int toIdx = rest.indexOf("/to");
                    if (fromIdx == -1 || toIdx == -1 || toIdx < fromIdx) {
                        throw new HardenException("Event must have /from and /to. Example: event meeting /from Mon 2pm /to 4pm");
                    }

                    String desc = rest.substring(0, fromIdx).trim();
                    String from = rest.substring(fromIdx + 5, toIdx).trim();
                    String to = rest.substring(toIdx + 3).trim();

                    if (desc.isEmpty()) throw new HardenException("The description of an event cannot be empty.");
                    if (from.isEmpty()) throw new HardenException("The /from part of an event cannot be empty.");
                    if (to.isEmpty()) throw new HardenException("The /to part of an event cannot be empty.");

                    Task t = new Event(desc, from, to);
                    tasks.add(t);
                    printAdded(t, tasks.size());
                    storage.save(tasks);
                } else {
                    throw new HardenException("I'm sorry, but I don't know what that means.");
                }

            } catch (HardenException e) {
                System.out.println("OOPS!!! " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void printAdded(Task task, int newCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + newCount + " tasks in the list.");
    }

    private static int parseIndex(String input, String cmd) throws HardenException {
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 2) throw new HardenException("Usage: " + cmd + " <task number>");
        try {
            int num = Integer.parseInt(parts[1]);
            if (num <= 0) throw new NumberFormatException();
            return num - 1;
        } catch (NumberFormatException e) {
            throw new HardenException("Task number must be a positive integer.");
        }
    }

    private static void ensureInRange(int idx, int size) throws HardenException {
        if (size == 0) throw new HardenException("There are no tasks yet.");
        if (idx < 0 || idx >= size) throw new HardenException("Task number is out of range.");
    }
}
