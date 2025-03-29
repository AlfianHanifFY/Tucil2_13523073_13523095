package lib;

import java.util.*;

import java.io.*;

public class IO {
    public String fileName;
    public Scanner fileScanner;
    public static Scanner inputScanner = new Scanner(System.in);

    // Konstruktor
    public IO(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    // Open & Close File
    public void openFile() {
        try {
            File file = new File(getFileName());
            this.fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("\u001B[33m[WARNING]\u001B[0m" + " : File " + getFileName()
                    + " tidak ditemukan di directory...");
            System.exit(0);
        }
    }

    public void closeFile() {
        if (fileScanner != null) {
            fileScanner.close();
        }
    }

    // Read file name from input
    public static String readFileName() {
        String fileName = "";
        while (true) {
            try {
                System.out.print("\u001B[38;5;214m[INPUT]\u001B[0m" + " : ");
                fileName = inputScanner.nextLine();
                File file = new File("test/input/" + fileName);
                Scanner tempScanner = new Scanner(file);
                tempScanner.close();
                break;
            } catch (FileNotFoundException e) {
                System.out.println("\u001B[33m[WARNING]\u001B[0m" + " : File " + fileName
                        + " tidak ditemukan di directory test/input.");
            }
        }
        return "test/input/" + fileName;
    }

}