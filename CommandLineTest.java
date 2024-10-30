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
        assertFalse(cli.rm("test.txt"));
    }

    @Test
    void testrmdir() {
        CommandLineInterface cli = new CommandLineInterface();
        assertTrue(CommandLineInterface.rmdir("test"));
    }

    @Test
    void testRmdirNotEmpty() {
        CommandLineInterface cli = new CommandLineInterface();
        assertFalse(cli.rmdir("test"));
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
        assertFalse(cli.mv("test.txt", "test1.txt"));
    }

    @Test
    void testMvDestinationFileNotExist() {
        CommandLineInterface cli = new CommandLineInterface();
        assertFalse(cli.mv("test.txt", "test1.txt"));
    }

}
