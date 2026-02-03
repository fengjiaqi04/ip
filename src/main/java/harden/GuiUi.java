package harden;

/**
 * A GUI version of Ui that captures output instead of printing to stdout.
 */
public class GuiUi extends Ui {

    private final StringBuilder output = new StringBuilder();

    @Override
    public void showWelcome() {
        output.append("Hello! I'm Harden\n");
        output.append("What can I do for you?\n");
    }

    @Override
    public void showGoodbye() {
        output.append("Bye. Hope to see you again soon!\n");
    }

    @Override
    public void showLine() {
        output.append("--------------------------------------------------\n");
    }

    @Override
    public void showError(String message) {
        output.append(message).append("\n");
    }

    @Override
    public void showMessage(String message) {
        output.append(message).append("\n");
    }

    /**
     * Returns all output generated so far.
     *
     * @return Output text
     */
    public String getOutput() {
        return output.toString();
    }
}
