import java.util.Scanner;

public class Harden {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Storage storage = new Storage("data/harden.txt");
        Task[] tasks = new Task[100];
        int taskCount = 0;

        // Load on startup (Level 7)
        try {
            Task[] loaded = storage.load();
            for (Task t : loaded) {
                if (t == null) break;
                tasks[taskCount++] = t;
            }
        } catch (HardenException e) {
            System.out.println("Could not load save file: " + e.getMessage());
        }

        System.out.println("Hello! I'm Harden");
        System.out.println("What can I do for you?");

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            try {
                boolean changed = false;

                if (input.equals("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + ". " + tasks[i]);
                    }
                    continue;
                }

                if (input.startsWith("mark ")) {
                    int n = Integer.parseInt(input.substring(5).trim());
                    if (n < 1 || n > taskCount) throw new HardenException("Task number out of range.");
                    tasks[n - 1].markDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks[n - 1]);
                    changed = true;

                } else if (input.startsWith("unmark ")) {
                    int n = Integer.parseInt(input.substring(7).trim());
                    if (n < 1 || n > taskCount) throw new HardenException("Task number out of range.");
                    tasks[n - 1].markNotDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks[n - 1]);
                    changed = true;

                } else if (input.startsWith("delete ")) {
                    int n = Integer.parseInt(input.substring(7).trim());
                    if (n < 1 || n > taskCount) throw new HardenException("Task number out of range.");

                    Task removed = tasks[n - 1];
                    for (int i = n - 1; i < taskCount - 1; i++) {
                        tasks[i] = tasks[i + 1];
                    }
                    tasks[taskCount - 1] = null;
                    taskCount--;

                    System.out.println("Noted. I've removed this task:");
                    System.out.println(removed);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    changed = true;

                } else {
                    Task newTask = Parser.parseToTask(input);
                    tasks[taskCount++] = newTask;

                    System.out.println("Got it. I've added this task:");
                    System.out.println(newTask);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    changed = true;
                }

                if (changed) {
                    storage.save(tasks, taskCount);
                }

            } catch (HardenException e) {
                System.out.println("OOPS!!! " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("OOPS!!! Please give a valid number.");
            }
        }
    }
}
