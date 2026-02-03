package harden;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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
