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

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;

            } else if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }

            } else if (input.startsWith("mark ")) {
                System.out.println("Nice! I've marked this task as done:");

                String[] parts = input.split(" ");
                int taskNumber = Integer.parseInt(parts[1]);
                tasks[taskNumber - 1].markDone();

                System.out.println(tasks[taskNumber - 1]);
            } else if (input.startsWith("unmark ")) {
                System.out.println("OK, I've marked this task as not done yet:");

                String[] parts = input.split(" ");
                int taskNumber = Integer.parseInt(parts[1]);
                tasks[taskNumber - 1].markNotDone();
//for printing no need to use tasks[n].toString() cuz println(Object) is effectively println(Object.toString())
                System.out.println(tasks[taskNumber - 1]);
            } else {
                // ---- ADD TASKS (Level 4) ----
                if (input.startsWith("todo ")) {
                    String description = input.substring("todo ".length()).trim();

                    tasks[taskCount] = new ToDo(description);

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount]);
                    System.out.println("Now you have " + (taskCount + 1) + " tasks in the list.");

                    taskCount++;

                } else if (input.startsWith("deadline ")) {
                    String rest = input.substring("deadline ".length()).trim(); // "return book /by Sunday"

                    int byIndex = rest.indexOf("/by");
                    String description = rest.substring(0, byIndex).trim();
                    String by = rest.substring(byIndex + 3).trim(); // after "/by"

                    tasks[taskCount] = new Deadline(description, by);

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount]);
                    System.out.println("Now you have " + (taskCount + 1) + " tasks in the list.");

                    taskCount++;

                } else if (input.startsWith("event ")) {
                    String rest = input.substring("event ".length()).trim(); // "project meeting /from Mon 2pm /to 4pm"

                    int fromIndex = rest.indexOf("/from");
                    int toIndex = rest.indexOf("/to");

                    String description = rest.substring(0, fromIndex).trim();
                    String from = rest.substring(fromIndex + 5, toIndex).trim(); // after "/from" (length 5)
                    String to = rest.substring(toIndex + 3).trim();              // after "/to" (length 3)

                    tasks[taskCount] = new Event(description, from, to);

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount]);
                    System.out.println("Now you have " + (taskCount + 1) + " tasks in the list.");

                    taskCount++;

                } else {
                    // fallback: treat as todo OR you can print an error message later
                    tasks[taskCount] = new ToDo(input);

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount]);
                    System.out.println("Now you have " + (taskCount + 1) + " tasks in the list.");

                    taskCount++;
                }
            }

        }
    }
}