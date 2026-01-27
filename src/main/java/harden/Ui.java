package harden;

/**
 * Handles all user-facing output.
 * Centralizes printing logic to keep other classes free of I/O details.
 */
public class Ui {

    /** Displays the welcome message. */
    public void showWelcome() {
        System.out.println("Hello! I'm Harden");
        System.out.println("What can I do for you?");
    }

    /** Displays the goodbye message. */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /** Prints a divider line. */
    public void showLine() {
        System.out.println("--------------------------------------------------");
    }

    /**
     * Displays an error message.
     *
     * @param message Error message.
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Displays a general message.
     *
     * @param message Message to display.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }
}
