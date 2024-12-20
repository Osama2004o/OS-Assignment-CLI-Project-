import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CommandLineInterface {
    public Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        CommandLineInterface cli = new CommandLineInterface();
        cli.run();
    }

    public void run() throws IOException {
        while (true) {
            System.out.print("> ");
            String command = input.nextLine().trim();
            String[] parts = command.split("\\s+");

            switch (parts[0]) {
                case "pwd":
                    pwd();
                    break;
                case "cd":
                    if (parts.length > 1) {
                        cd(parts[1]);
                    } else {
                        System.out.println("Usage: cd <directory>");
                    }
                    break;
                case "ls":
                    if (parts.length > 1) {
                        ls(parts[1]);
                    } else {
                        ls();
                    }
                    break;
                case "rm":
                    if (parts.length > 1) {
                        for (int i = 1; i < parts.length; i++)
                            rm(parts[i]);
                    } else {
                        System.out.println("No such file exist");
                    }
                    break;
                case "touch":
                    if (parts.length > 1) {
                        for (int i = 1; i < parts.length; i++)
                            touch(parts[i]);
                    }
                    break;
                case "Mkdir":
                    if (parts.length > 1) {
                        for (int i = 1; i < parts.length; i++)
                            Mkdir(parts[i]);
                    }
                    break;
                case "rmdir":
                    if (parts.length > 1) {
                        for (int i = 1; i < parts.length; i++)
                            rmdir(parts[i]);
                    }
                    break;
                case "cat":
                    if (parts.length > 1) {
                        cat(parts[1]);
                    }
                    break;
                case "mv":
                    if (parts.length > 1) {
                        mv(parts[1], parts[2]);
                    }
                    break;
                case "writeToFile":
                    if (parts.length > 1) {
                        if (parts[2].charAt(0) == 'p') {
                            writeToFile(parts[1], pwd());

                        } else {
                            writeToFile(parts[1], parts[2]);
                        }
                    }
                    break;
                case "appendToFile":
                    if (parts.length > 1) {
                        if (parts[2].charAt(0) == 'p') {
                            appendToFile(parts[1], pwd());

                        } else {
                            appendToFile(parts[1], parts[2]);
                        }
                    }
                    break;
                case "exit":
                    exit();
                    return;
                case "help":
                    help();
                    break;
                default:
                    System.out.println("Unknown command. Type 'help' for available commands.");
            }
        }
    }

    public static String pwd() {
        System.out.println(System.getProperty("user.dir"));
        return System.getProperty("user.dir");
    }

    public static boolean cd(String path) {
        File newDir = new File(path);
        if (newDir.isDirectory()) {
            System.setProperty("user.dir", newDir.getAbsolutePath());
            System.out.println("Changed directory to: " + newDir.getAbsolutePath());
            return true;
        } else {
            System.out.println("Error: Not a valid directory");
            return false;
        }
    }

    public static void ls(String option) {
        File currentDir = new File(System.getProperty("user.dir"));
        File[] files = currentDir.listFiles();

        if (files == null) {
            System.out.println("Error: Unable to list files");
            return;
        }

        if (option.equals("-a")) {
            for (File file : files) {
                System.out.println(file.getName());
            }
        } else if (option.equals("-r")) {
            for (int i = files.length - 1; i >= 0; i--) {
                System.out.println(files[i].getName());
            }
        } else {
            System.out.println("Invalid option. Use -a or -r");
        }
    }

    public boolean rm(String name) {
        File file = new File(name);
        if (file.exists()) {
            file.delete();
            return true;

        } else {
            return false;
        }
    }

    public static void ls() {
        File file = new File(System.getProperty("user.dir"));
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                String name = file2.getName();
                if (name.charAt(0) == '.') {
                    continue;
                }
                System.out.println(file2.getName());
            }
        }
    }

    public void cat(String name) throws FileNotFoundException {
        File file = new File(name);
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            String line = myReader.nextLine();
            System.out.println(line);
        }
    }

    public static boolean touch(String name) throws IOException {
        File file = new File(name);
        if (file.exists()) {
            return false;
        } else {
            file.createNewFile();
            return true;
        }
    }

    public static boolean Mkdir(String name) {
        File file = new File(name);
        if (file.isDirectory()) {
            return false;
        } else {
            file.mkdir();
            return true;
        }
    }

    public static boolean rmdir(String name) {
        File file = new File(name);
        if (!file.isDirectory()) {
            return false;
        }
        if (file.list().length > 0) {
            return false;
        } else {
            file.delete();
            return true;
        }
    }

    public static boolean mv(String name1, String name2) {
        File sourceFile = new File(name1);
        File destinationFile = new File(name2);

        if (!sourceFile.exists()) {
            return false;
        }

        if (sourceFile.exists() || destinationFile.exists()) {
            sourceFile.renameTo(destinationFile);

            return true;
        } else {

            return false;
        }
    }

    public static boolean writeToFile(String fileName, String content) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            writer.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean appendToFile(String fileName, String content) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(content + System.lineSeparator());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void exit() {
        System.exit(0);
    }

    public void help() {
        System.out.println("Available commands:");
        System.out.println("pwd - Print working directory");
        System.out.println("cd <directory> - Change directory");
        System.out.println("ls - List files and directories");
        System.out.println("ls -a - List all files and directories (including hidden)");
        System.out.println("ls -r - List files and directories in reverse order");
        System.out.println("rm removes a files");
        System.out.println("cat reads the text from a file and print it to terminal");
        System.out.println("touch makes a file");
        System.out.println("Mkdir makes a directory");
        System.out.println("rmdir removes a directory if it's empty");
        System.out.println("mv moves one or more files/directories to a directory");
        System.out.println("writeToFile adds the output of string or command to file");
        System.out.println("appendToFile append the output of command to the file without removing the first");
        System.out.println("exit - Exit the CLI");
        System.out.println("help - Display this help message");

    }

}
