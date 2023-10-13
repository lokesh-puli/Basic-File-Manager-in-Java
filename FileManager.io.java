import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class FileManager {

    public static void main(String[] args) {
        File currentDirectory = new File(System.getProperty("user.dir"));
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Current Directory: " + currentDirectory.getAbsolutePath());
            System.out.println("Options:");
            System.out.println("1. List Files and Directories");
            System.out.println("2. Create Directory");
            System.out.println("3. Create File");
            System.out.println("4. Rename File/Directory");
            System.out.println("5. Copy File/Directory");
            System.out.println("6. Move File/Directory");
            System.out.println("7. Delete File/Directory");
            System.out.println("8. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    listFilesAndDirectories(currentDirectory);
                    break;
                case 2:
                    createDirectory(scanner, currentDirectory);
                    break;
                case 3:
                    createFile(scanner, currentDirectory);
                    break;
                case 4:
                    renameFileOrDirectory(scanner, currentDirectory);
                    break;
                case 5:
                    copyFileOrDirectory(scanner, currentDirectory);
                    break;
                case 6:
                    moveFileOrDirectory(scanner, currentDirectory);
                    break;
                case 7:
                    deleteFileOrDirectory(scanner, currentDirectory);
                    break;
                case 8:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void listFilesAndDirectories(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                System.out.println(file.getName());
            }
        }
    }

    private static void createDirectory(Scanner scanner, File currentDirectory) {
        System.out.print("Enter the directory name: ");
        String directoryName = scanner.nextLine();
        File newDirectory = new File(currentDirectory, directoryName);

        if (newDirectory.mkdir()) {
            System.out.println("Directory created successfully.");
        } else {
            System.out.println("Failed to create the directory.");
        }
    }

    private static void createFile(Scanner scanner, File currentDirectory) {
        System.out.print("Enter the file name: ");
        String fileName = scanner.nextLine();
        File newFile = new File(currentDirectory, fileName);

        try {
            if (newFile.createNewFile()) {
                System.out.println("File created successfully.");
            } else {
                System.out.println("Failed to create the file.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void renameFileOrDirectory(Scanner scanner, File currentDirectory) {
        System.out.print("Enter the old name: ");
        String oldName = scanner.nextLine();
        System.out.print("Enter the new name: ");
        String newName = scanner.nextLine();

        File oldFile = new File(currentDirectory, oldName);
        File newFile = new File(currentDirectory, newName);

        if (oldFile.renameTo(newFile)) {
            System.out.println("File/Directory renamed successfully.");
        } else {
            System.out.println("Failed to rename the file/directory.");
        }
    }

    private static void copyFileOrDirectory(Scanner scanner, File currentDirectory) {
        System.out.print("Enter the source name: ");
        String sourceName = scanner.nextLine();
        System.out.print("Enter the destination name: ");
        String destinationName = scanner.nextLine();

        File sourceFile = new File(currentDirectory, sourceName);
        File destinationFile = new File(currentDirectory, destinationName);

        if (sourceFile.exists()) {
            if (sourceFile.isFile()) {
                copyFile(sourceFile, destinationFile);
            } else if (sourceFile.isDirectory()) {
                copyDirectory(sourceFile, destinationFile);
            }
        } else {
            System.out.println("Source file/directory does not exist.");
        }
    }

    private static void moveFileOrDirectory(Scanner scanner, File currentDirectory) {
        System.out.print("Enter the source name: ");
        String sourceName = scanner.nextLine();
        System.out.print("Enter the destination name: ");
        String destinationName = scanner.nextLine();

        File sourceFile = new File(currentDirectory, sourceName);
        File destinationFile = new File(currentDirectory, destinationName);

        if (sourceFile.exists()) {
            if (sourceFile.isFile()) {
                moveFile(sourceFile, destinationFile);
            } else if (sourceFile.isDirectory()) {
                moveDirectory(sourceFile, destinationFile);
            }
        } else {
            System.out.println("Source file/directory does not exist.");
        }
    }

    private static void deleteFileOrDirectory(Scanner scanner, File currentDirectory) {
        System.out.print("Enter the file/directory name to delete: ");
        String fileName = scanner.nextLine();
        File fileToDelete = new File(currentDirectory, fileName);

        if (fileToDelete.exists()) {
            if (fileToDelete.isFile()) {
                if (fileToDelete.delete()) {
                    System.out.println("File deleted successfully.");
                } else {
                    System.out.println("Failed to delete the file.");
                }
            } else if (fileToDelete.isDirectory()) {
                deleteDirectory(fileToDelete);
            }
        } else {
            System.out.println("File/directory does not exist.");
        }
    }

    private static void copyFile(File sourceFile, File destinationFile) {
        try {
            Files.copy(sourceFile.toPath(), destinationFile.toPath());
            System.out.println("File copied successfully.");
        } catch (IOException e) {
            System.out.println("Failed to copy the file: " + e.getMessage());
        }
    }

    private static void copyDirectory(File sourceDir, File destinationDir) {
        if (!destinationDir.exists()) {
            destinationDir.mkdirs();
        }

        File[] files = sourceDir.listFiles();
        if (files != null) {
            for (File file : files) {
                File destinationFile = new File(destinationDir, file.getName());
                if (file.isDirectory()) {
                    copyDirectory(file, destinationFile);
                } else {
                    copyFile(file, destinationFile);
                }
            }
        }
        System.out.println("Directory copied successfully.");
    }

    private static void moveFile(File sourceFile, File destinationFile) {
        if (sourceFile.renameTo(destinationFile)) {
            System.out.println("File moved successfully.");
        } else {
            System.out.println("Failed to move the file.");
        }
    }

    private static void moveDirectory(File sourceDir, File destinationDir) {
        if (sourceDir.renameTo(destinationDir)) {
            System.out.println("Directory moved successfully.");
        } else {
            System.out.println("Failed to move the directory.");
        }
    }

    private static void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    if (file.delete()) {
                        System.out.println("File deleted: " + file.getName());
                    } else {
                        System.out.println("Failed to delete the file: " + file.getName());
                    }
                }
            }
        }

        if (directory.delete()) {
            System.out.println("Directory deleted successfully.");
        } else {
            System.out.println("Failed to delete the directory.");
        }
    }
}


