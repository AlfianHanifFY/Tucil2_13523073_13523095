import java.util.*;
import lib.*;

public class Main {
    static Scanner inputScanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("\u001B[34m[INFO]\u001B[0m : Wilujeng Sumping...\n\n");
        System.out.println("\u001B[34m[INFO]\u001B[0m : Masukkan alamat ABSOLUT image ! \n");

        Image image = IO.readImage();
        int method = IO.readErorrMethod();
        double Threshold = IO.readThreshold();
        int minBlock = IO.readMinBlock();

        image.setImageParam(method, Threshold, minBlock);

        System.out.println("\u001B[34m[INFO]\u001B[0m : Memproses gambar dengan Quadtree...");
        Quadtree quadtree = new Quadtree(image);
        Compressor.compress(quadtree);

        String outputFilePath = IO.readOutputPath();
        IO.saveImage(outputFilePath, quadtree);

        System.out.println("\u001B[32m[SUKSES]\u001B[0m : Gambar berhasil disimpan sebagai " + outputFilePath);
    }
}
