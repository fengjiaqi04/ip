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
                    System.out.println((i + 1) + "." + tasks[i]);
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
            }


            else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println("added: " + input);
            }
        }
    }
}
