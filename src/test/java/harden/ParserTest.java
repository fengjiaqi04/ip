package harden;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void parse_validTodoCommand() throws HardenException {
        Command c = Parser.parse("todo read book");
        assertTrue(c instanceof TodoCommand);
    }

    @Test
    public void parse_blankInput_throwsException() {
        assertThrows(HardenException.class, () -> Parser.parse("   "));
    }
}
