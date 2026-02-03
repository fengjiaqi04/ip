package harden;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    public void markDoneAndUnmark_updatesDoneStatus() {
        Task t = new ToDo("read book");

        t.markDone();
        assertTrue(t.toString().contains("[X]"));

        t.markNotDone();
        assertTrue(t.toString().contains("[ ]"));
    }
}
