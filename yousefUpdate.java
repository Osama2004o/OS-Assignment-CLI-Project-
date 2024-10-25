// created by : Alpha Amr 

import java.io.File;
import java.io.FileNotFoundException;
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
                case "exit":
                    exit();
                    return;
                case "help":
                    help();
                    break;
                // ... other commands ...
                default:
                    System.out.println("Unknown command. Type 'help' for available commands.");
            }
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

    public boolean touch(String name) throws IOException {
        File file = new File(name);
        if (file.exists()) {
            return true;
        } else {
            file.createNewFile();
            return true;
        }
    }

    public static boolean Mkdir(String name) {
        File file = new File(name);
        if (file.isDirectory()) {
            return true;
        } else {
            file.mkdir();
            return true;
        }
    }

    public static void rmdir(String name){
        File file = new File(name);
        if (file.list().length > 0) {
            System.out.println( name + ": is not empty");
        }
        else{
            file.delete();
            System.out.println( name + ": is deleted");
        }
    }

    public static void mv(String name1, String name2) {
        File sourceFile = new File(name1);
        File destinationFile = new File(name2);

        if (!sourceFile.exists()) {
            System.out.println(name1 + ": is not exist");
            return;
        }

        if (sourceFile.exists()||destinationFile.exists()) {
            sourceFile.renameTo(destinationFile);
            System.out.println("File moved successfully.");
        } else {
            System.out.println("failed to move file");
        }
    }
// i made it as an attempt to understand how the program will be built and run .
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
        // ... list other available commands ...
        System.out.println("exit - Exit the CLI");
        System.out.println("help - Display this help message");
    }
//my tasks (pwd, cd, ls) Alpha tasks
    public static void pwd() {
        System.out.println(System.getProperty("user.dir"));
    }

    public static void cd(String path) {
        File newDir = new File(path);
        if (newDir.isDirectory()) {
            System.setProperty("user.dir", newDir.getAbsolutePath());
            System.out.println("Changed directory to: " + newDir.getAbsolutePath());
        } else {
            System.out.println("Error: Not a valid directory");
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
}
