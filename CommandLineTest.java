import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class CommandLineTest {

    @Test
    void testLs() {
        System.setProperty("user.dir", new File("testDir").getAbsolutePath());
        Assertions.assertDoesNotThrow(() -> {
            CommandLineInterface.ls();
        });
    }

    @Test
    void testTouch() throws IOException {
        assertTrue(CommandLineInterface.touch("test.txt"));
    }

    @Test
    void testTouchFileAlreadyExist() throws IOException {
        assertFalse(CommandLineInterface.touch("test.txt"));

    }

    @Test
    void testMkdir() {
        assertTrue(CommandLineInterface.Mkdir("test"));
    }

    @Test
    void testMkdirAlreadyExist() {
        assertFalse(CommandLineInterface.Mkdir("testDir"));

    }

    @Test
    public void testCat() throws Exception {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));
        CommandLineInterface cli = new CommandLineInterface();
        cli.cat("test.txt");

        System.setOut(originalOut);

        String expectedOutput = "test";
        String actualOutput = bos.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testCatTextNotEqual() throws Exception {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));
        CommandLineInterface cli = new CommandLineInterface();
        cli.cat("test.txt");

        System.setOut(originalOut);

        String expectedOutput = "test1";
        String actualOutput = bos.toString().trim();

        assertFalse(expectedOutput == actualOutput);
    }

    @Test
    void testCatFileNotFound() {
        CommandLineInterface CommandLineInterface = new CommandLineInterface();
        assertThrows(FileNotFoundException.class, () -> CommandLineInterface.cat("nonExistentFile.txt"));
    }

    @Test
    void testRm() {
        CommandLineInterface cli = new CommandLineInterface();
        assertTrue(cli.rm("test.txt"));
    }

    @Test
    void testRmFileNotFound() {
        CommandLineInterface cli = new CommandLineInterface();
        assertFalse(cli.rm("test1.txt"));
    }

    @Test
    void testrmdir() {
        CommandLineInterface cli = new CommandLineInterface();
        assertTrue(CommandLineInterface.rmdir("test"));
    }

    @Test
    void testRmdirNotEmpty() {
        CommandLineInterface cli = new CommandLineInterface();
        assertFalse(cli.rmdir("lib"));
    }

    @Test
    void testRmdirNotExist() {
        CommandLineInterface cli = new CommandLineInterface();
        assertFalse(cli.rmdir("test"));
    }

    @Test
    void testmv() {
        CommandLineInterface cli = new CommandLineInterface();
        assertTrue(cli.mv("test.txt", "test1.txt"));
    }

    @Test
    void testMvSourceFileNotExist() {
        CommandLineInterface cli = new CommandLineInterface();
        assertFalse(cli.mv("test2.txt", "test1.txt"));
    }

    @Test
    void testMvDestinationFileNotExist() {
        CommandLineInterface cli = new CommandLineInterface();
        assertFalse(cli.mv("test3.txt", "test1.txt"));
    }

    @Test
    void testPwd() {
        String actualOutput = CommandLineInterface.pwd();
        String expectedOutput = System.getProperty("user.dir");
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testCd() {
        String newPath = "testDir";
        File expectedDir = new File(newPath).getAbsoluteFile();
        assertTrue(CommandLineInterface.cd(newPath));
        assertEquals(expectedDir.getPath(), System.getProperty("user.dir"));
    }

    @Test
    void testCdInvalidDirectory() {
        String invalidPath = "nonexistentDirectory";
        assertFalse(CommandLineInterface.cd(invalidPath));
    }

    @Test
    void testLsA() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));
        CommandLineInterface.ls("-a");
        System.setOut(originalOut);
        String output = bos.toString().trim();
        assertTrue(output.contains("."));
    }

    @Test
    void testLsR() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));
        CommandLineInterface.ls("-r");
        System.setOut(originalOut);
        String output = bos.toString().trim();
        assertFalse(output.isEmpty());
    }

    @Test
    void testWriteToFile() throws FileNotFoundException {
        String fileName = "test.txt";
        File file = new File(fileName);
        CommandLineInterface cli = new CommandLineInterface();
        cli.writeToFile(fileName, CommandLineInterface.pwd()); // p is pwd but i write it as this
        Scanner myReader = new Scanner(file);
        String line = myReader.nextLine();
        assertEquals(line, CommandLineInterface.pwd());
    }

    @Test
    void testAppendToFile() throws FileNotFoundException {
        String fileName = "test.txt";
        File file = new File(fileName);
        CommandLineInterface cli = new CommandLineInterface();
        Scanner myReader = new Scanner(file);
        String line1 = myReader.nextLine();
        cli.appendToFile(fileName, "123");
        Scanner myReader1 = new Scanner(file);
        String line2 = myReader1.nextLine();
        assertEquals(line1 += "123", line2);
    }

}
