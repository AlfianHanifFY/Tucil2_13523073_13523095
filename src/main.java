import java.awt.image.BufferedImage;
import java.util.*;
import lib.*;

public class Main {
    static Scanner inputScanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\u001B[34m[INFO]\u001B[0m : Wilujeng Sumping...\n");

        boolean status = true;

        while (status) {
            System.out.println("\u001B[34m[INFO]\u001B[0m : Masukkan alamat ABSOLUT image !\n");

            String inputFile = IO.readFileName();
            IO.readImage(inputFile);
            int method = IO.readErorrMethod();
            double Threshold = IO.readThreshold(method);
            int minBlock = IO.readMinBlock(IO.infoImage);
            IO.infoImage.setImageParam(method, Threshold, minBlock);
            String outputFile = IO.readOutputPath(inputFile);
            String outputGIFFile = IO.readOutputGIFPath();

            System.out.println("\n\u001B[34m[INFO]\u001B[0m : Memproses gambar dengan Quadtree...\n");

            long startTime = System.currentTimeMillis();
            Quadtree quadtree = new Quadtree(IO.infoImage.getRow(), IO.infoImage.getCol(), 0, 0);

            boolean compressionSuccess = false;
            boolean gifSuccess = false;

            try {
                Compressor.compress(quadtree);

                IO.saveImage(outputFile, quadtree);

                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;

                System.out.println("\u001B[32m--------[OUTPUT KOMpresi]--------\u001B[0m");
                System.out.println("\u001B[32m[SUKSES]\u001B[0m : Waktu Eksekusi : " + executionTime + " milliseconds");

                long fileInputSizeInBytes = IO.getFileSize(inputFile);
                System.out.println(
                        "\u001B[32m[SUKSES]\u001B[0m : Ukuran File Input : " + fileInputSizeInBytes + " bytes");

                long fileOutputSizeInBytes = IO.getFileSize(outputFile);
                System.out.println(
                        "\u001B[32m[SUKSES]\u001B[0m : Ukuran File Output : " + fileOutputSizeInBytes + " bytes");

                double compressionRate = IO.calculateCompressionPercentage(fileInputSizeInBytes, fileOutputSizeInBytes);
                System.out.println("\u001B[32m[SUKSES]\u001B[0m : Persentase Kompresi : " + compressionRate + "%");

                int depth = quadtree.getDepth();
                System.out.println("\u001B[32m[SUKSES]\u001B[0m : Kedalaman Pohon : " + depth);

                int nodeCount = quadtree.countNodes();
                System.out.println("\u001B[32m[SUKSES]\u001B[0m : Banyak Simpul : " + nodeCount);

                System.out
                        .println("\u001B[32m[SUKSES]\u001B[0m : Gambar berhasil disimpan sebagai " + outputFile + "\n");
                compressionSuccess = true;
            } catch (OutOfMemoryError e) {
                System.out.println("\u001B[31m[ERROR]\u001B[0m : Memori tidak cukup saat kompresi gambar.");
            } catch (Exception e) {
                System.out.println(
                        "\u001B[31m[ERROR]\u001B[0m : Terjadi kesalahan saat kompresi gambar - " + e.getMessage());
            }

            try {

                BufferedImage[] frames = IO.reconstructImageByDepth(quadtree, IO.infoImage.getCol(),
                        IO.infoImage.getRow());
                IO.createGIF(frames, outputGIFFile, 500);
                System.out.println("\u001B[32m[SUKSES]\u001B[0m : GIF berhasil dibuat di " + outputGIFFile);
                gifSuccess = true;
            } catch (OutOfMemoryError e) {
                System.out.println("\u001B[31m[ERROR]\u001B[0m : Memori tidak cukup saat membuat GIF.");
            } catch (Exception e) {
                System.out
                        .println("\u001B[31m[ERROR]\u001B[0m : Terjadi kesalahan saat membuat GIF - " + e.getMessage());
            }

            // Kesimpulan
            System.out.println("\n\u001B[34m[INFO]\u001B[0m : Status akhir proses:");
            System.out.println(" - Kompresi Gambar : "
                    + (compressionSuccess ? "\u001B[32mBerhasil\u001B[0m" : "\u001B[31mGagal\u001B[0m"));
            System.out.println(" - Pembuatan GIF   : "
                    + (gifSuccess ? "\u001B[32mBerhasil\u001B[0m" : "\u001B[31mGagal\u001B[0m"));

            // Tanya lanjut
            System.out.println("\n\u001B[34m[INFO]\u001B[0m : Apakah Anda ingin memproses gambar lain?");
            System.out.println("  [1] Ya");
            System.out.println("  [2] Tidak (Keluar)");
            System.out.print("\u001B[38;5;214m[INPUT]\u001B[0m : ");
            int pilihan = inputScanner.nextInt();
            inputScanner.nextLine();
            status = (pilihan == 1);
            System.out.println();
        }

        System.out.println("\u001B[34m[INFO]\u001B[0m : Program selesai. Hatur nuhun!");
    }
}
