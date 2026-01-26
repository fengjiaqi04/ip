public class TodoCommand extends Command {
    private final String description;

    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HardenException {
        if (description == null || description.trim().isEmpty()) {
            throw new HardenException("OOPS!!! The description of a todo cannot be empty.");
        }

        Task task = new ToDo(description.trim());
        tasks.add(task);

        // persist after mutation
        storage.save(tasks.getTasks().toArray(new Task[0]), tasks.size());



        ui.showLine();
        ui.showMessage("Got it. I've added this task:");
        ui.showMessage("  " + task);
        ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
        ui.showLine();
    }
}
