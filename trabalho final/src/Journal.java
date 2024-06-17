import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Journal {
    private static final String JOURNAL_FILE = "journal.log";

    public void log(FileOperation operation, String source, String destination) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(JOURNAL_FILE, true))) {
            writer.write(operation + ": " + source + " -> " + (destination != null ? destination : "") + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
