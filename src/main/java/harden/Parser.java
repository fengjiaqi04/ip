package harden;

/**
 * Parses raw user input into executable Command objects.
 */
public class Parser {

    /**
     * Parses user input into a command.
     *
     * @param input Raw input string.
     * @return Corresponding Command.
     * @throws HardenException If the input is invalid.
     */
    public static Command parse(String input) throws HardenException {
        if (input == null || input.trim().isEmpty()) {
            throw new HardenException("OOPS!! Please enter a command.");
        }

        String[] parts = input.trim().split("\\s+", 2);
        String commandWord = parts[0];
        String rest = parts.length > 1 ? parts[1] : "";

        switch (commandWord) {
            case "bye":
                return new ByeCommand();
            case "list":
                return new ListCommand();
            case "todo":
                if (rest.isEmpty()) {
                    throw new HardenException("OOPS!! The description of a todo cannot be empty.");
                }
                return new TodoCommand(rest);
            default:
                throw new HardenException("OOPS!! I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Deserializes a saved line into a Task.
     *
     * @param line One line from the save file.
     * @return Reconstructed Task.
     * @throws HardenException If the line is corrupted.
     */
    public static Task deserialize(String line) throws HardenException {
        throw new HardenException("Deserialize not implemented.");
    }
}
