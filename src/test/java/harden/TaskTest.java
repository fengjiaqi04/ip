package harden;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
