import java.util.Scanner;

public class FileSystemSimulator {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine();
            fileSystem.executeCommand(command);
        }
    }
}
