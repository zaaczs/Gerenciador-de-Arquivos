import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileSystem {
    private Journal journal;

    public FileSystem() {
        this.journal = new Journal();
    }

    public void executeCommand(String command) {
        String[] parts = command.split(" ");
        String operation = parts[0];

        switch (operation) {
            case "copy":
                if (parts.length < 3) {
                    System.out.println("Usage: copy <source> <destination>");
                } else {
                    copyFile(parts[1], parts[2]);
                }
                break;
            case "delete":
                if (parts.length < 2) {
                    System.out.println("Usage: delete <file>");
                } else {
                    deleteFile(parts[1]);
                }
                break;
            case "rename":
                if (parts.length < 3) {
                    System.out.println("Usage: rename <oldName> <newName>");
                } else {
                    renameFile(parts[1], parts[2]);
                }
                break;
            case "mkdir":
                if (parts.length < 2) {
                    System.out.println("Usage: mkdir <directory>");
                } else {
                    createDirectory(parts[1]);
                }
                break;
            case "rmdir":
                if (parts.length < 2) {
                    System.out.println("Usage: rmdir <directory>");
                } else {
                    deleteDirectory(parts[1]);
                }
                break;
            case "list":
                if (parts.length < 2) {
                    System.out.println("Usage: list <directory>");
                } else {
                    listDirectory(parts[1]);
                }
                break;
            default:
                System.out.println("Invalid command");
        }
    }

    private void copyFile(String source, String destination) {
        try {
            Path srcPath = Paths.get(source);
            Path destPath = Paths.get(destination);
            Files.copy(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);
            journal.log(FileOperation.COPY, source, destination);
        } catch (IOException e) {
            System.err.println("Error copying file: " + e.getMessage());
        }
    }

    private void deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.delete(path);
            journal.log(FileOperation.DELETE, filePath, null);
        } catch (IOException e) {
            System.err.println("Error deleting file: " + e.getMessage());
        }
    }

    private void renameFile(String oldName, String newName) {
        try {
            Path oldPath = Paths.get(oldName);
            Path newPath = Paths.get(newName);
            Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
            journal.log(FileOperation.RENAME, oldName, newName);
        } catch (IOException e) {
            System.err.println("Error renaming file: " + e.getMessage());
        }
    }

    private void createDirectory(String dirPath) {
        try {
            Path path = Paths.get(dirPath);
            if (Files.exists(path)) {
                System.out.println("Directory already exists: " + dirPath);
            } else {
                Files.createDirectory(path);
                journal.log(FileOperation.CREATE_DIR, dirPath, null);
            }
        } catch (IOException e) {
            System.err.println("Error creating directory: " + e.getMessage());
        }
    }

    private void deleteDirectory(String dirPath) {
        try {
            Path path = Paths.get(dirPath);
            if (Files.exists(path)) {
                Files.delete(path);
                journal.log(FileOperation.DELETE_DIR, dirPath, null);
            } else {
                System.out.println("Directory does not exist: " + dirPath);
            }
        } catch (IOException e) {
            System.err.println("Error deleting directory: " + e.getMessage());
        }
    }

    private void listDirectory(String dirPath) {
        try {
            Path path = Paths.get(dirPath);
            if (Files.exists(path) && Files.isDirectory(path)) {
                Files.list(path).forEach(System.out::println);
                journal.log(FileOperation.LIST, dirPath, null);
            } else {
                System.out.println("Directory does not exist: " + dirPath);
            }
        } catch (IOException e) {
            System.err.println("Error listing directory: " + e.getMessage());
        }
    }
}
