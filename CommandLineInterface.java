import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class CommandLineInterface {
    public Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        ls();
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


    public void exit() {
        System.exit(0);
    }

    public void help() {

    }
}
