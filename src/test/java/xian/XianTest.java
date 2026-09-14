package xian;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class XianTest {

    @TempDir
    Path temporaryDirectory;

    @Test
    public void executeCommand_updateDescription_updatesExistingTask() throws IOException, XianException {
        Xian xian = createXian();
        xian.executeCommand("todo read book");

        String response = xian.executeCommand("update 1 desc read textbook");

        assertEquals("\tDone — I've updated this task:\n\t [T][ ] read textbook\n", response);
    }

    @Test
    public void executeCommand_updateDeadlineDate_updatesExistingTask() throws IOException, XianException {
        Xian xian = createXian();
        xian.executeCommand("deadline submit report /by 10/9/2026 1800");

        String response = xian.executeCommand("update 1 by 11/9/2026 1800");

        assertEquals("\tDone — I've updated this task:\n"
                + "\t [D][ ] submit report (by: 11/9/2026 1800)\n", response);
    }

    @Test
    public void executeCommand_updateEventEnd_updatesExistingTask() throws IOException, XianException {
        Xian xian = createXian();
        xian.executeCommand("event project meeting /from 10/9/2026 1400 /to 10/9/2026 1600");

        String response = xian.executeCommand("update 1 to 10/9/2026 1700");

        assertEquals("\tDone — I've updated this task:\n"
                + "\t [E][ ] project meeting (from: 10/9/2026 1400 to: 10/9/2026 1700)\n", response);
    }

    @Test
    public void executeCommand_updateTodoDate_throwsXianException() throws IOException, XianException {
        Xian xian = createXian();
        xian.executeCommand("todo read book");

        assertThrows(XianException.class,
                () -> xian.executeCommand("update 1 by 11/9/2026 1800"));
    }

    @Test
    public void executeCommand_updateInvalidDate_throwsDateTimeParseException()
            throws IOException, XianException {
        Xian xian = createXian();
        xian.executeCommand("deadline submit report /by 10/9/2026 1800");

        assertThrows(DateTimeParseException.class,
                () -> xian.executeCommand("update 1 by not-a-date"));
    }

    private Xian createXian() {
        Path saveFile = temporaryDirectory.resolve("xian.txt");
        return new Xian(saveFile.toString());
    }
}
