import java.util.*;

import lib.*;

public class Main {
    static Scanner inputScanner = new Scanner(System.in);

    public static void main(String[] args) {
        IO io;
        Image image;
        System.out.println("\u001B[34m[INFO]\u001B[0m" + " : Wilujeng Sumping...\n\n");
        System.out.println("\u001B[34m[INFO]\u001B[0m" + " : Masukkan alamat image ! \n");
        image = IO.readImage();
        image.print(1);
    }
}