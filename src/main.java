import java.util.*;
import lib.*;

public class main {
    static Scanner inputScanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("\u001B[34m[INFO]\u001B[0m : Wilujeng Sumping...\n\n");
        System.out.println("\u001B[34m[INFO]\u001B[0m : Masukkan alamat ABSOLUT image ! \n");

        String inputFile = IO.readFileName();
        IO.readImage(inputFile);
        int method = IO.readErorrMethod();
        double Threshold = IO.readThreshold(method);
        int minBlock = IO.readMinBlock(IO.infoImage);

        IO.infoImage.setImageParam(method, Threshold, minBlock);

        System.out.println("");

        System.out.println("\u001B[34m[INFO]\u001B[0m : Memproses gambar dengan Quadtree...\n");

        long startTime = System.currentTimeMillis();

        Quadtree quadtree = new Quadtree(IO.infoImage.getRow(), IO.infoImage.getCol(), 0, 0);
        Compressor.compress(quadtree);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        String outputFile = IO.readOutputPath();
        IO.saveImage(outputFile, quadtree);

        // OUTPUT //
        System.out.println("\u001B[32m--------[OUTPUT]--------\u001B[0m");
        // WAKTU EKSEKUSI
        System.out.println("\u001B[32m[SUKSES]\u001B[0m : Waktu Eksekusi : " + executionTime + " milliseconds");

        // UKURAN FILE INPUT
        long fileInputSizeInBytes = IO.getFileSize(inputFile);
        System.out.println("\u001B[32m[SUKSES]\u001B[0m : Ukuran File Input : " + fileInputSizeInBytes + " bytes");

        // UKURAN FILE OUTPUT
        long fileOutputSizeInBytes = IO.getFileSize(outputFile);
        System.out.println("\u001B[32m[SUKSES]\u001B[0m : Ukuran File Output : " + fileOutputSizeInBytes + " bytes");

        // Persentase Kompresi
        double compressionRate = IO.calculateCompressionPercentage(fileInputSizeInBytes, fileOutputSizeInBytes);
        System.out.println("\u001B[32m[SUKSES]\u001B[0m : Persentase Kompresi : " + compressionRate + "%");

        // Kedalaman Pohon
        int depth = quadtree.getDepth();
        System.out.println("\u001B[32m[SUKSES]\u001B[0m : Kedalaman Pohon : " + depth);

        // Banyak Simpul
        int nodeCount = quadtree.countNodes();
        System.out.println("\u001B[32m[SUKSES]\u001B[0m : Banyak Simpul : " + nodeCount);

        // Output Path Img
        System.out.println("\u001B[32m[SUKSES]\u001B[0m : Gambar berhasil disimpan sebagai " + outputFile + "\n");
    }
}
