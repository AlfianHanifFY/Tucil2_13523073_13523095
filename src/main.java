import java.awt.image.BufferedImage;
import java.util.*;
import lib.*;

public class main {
    static Scanner inputScanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\u001B[34m[INFO]\u001B[0m : Wilujeng Sumping...\n");

        boolean status = true;

        while (status) {
            System.out.println("\u001B[34m[INFO]\u001B[0m : Masukkan alamat ABSOLUT image !\n");

            String inputFile = IO.readFileName();

            int method = IO.readErorrMethod();
            double Threshold = IO.readThreshold(method);
            int minBlock = IO.readMinBlock();
            double targetKompresi = IO.readTargetCompression();

            String outputFile = IO.readOutputPath(inputFile);
            String outputGIFFile = IO.readOutputGIFPath();

            Quadtree quadtree;

            boolean compressionSuccess = false;
            boolean gifSuccess = false;

            long startTime = System.currentTimeMillis();
            if (targetKompresi == 0) {
                System.out.println("\n\u001B[34m[INFO]\u001B[0m : Memproses gambar dengan Quadtree...\n");
                IO.readImage(inputFile);
                IO.infoImage.setImageParam(method, Threshold, minBlock);
                quadtree = new Quadtree(IO.infoImage.getRow(), IO.infoImage.getCol(), 0, 0);
                try {
                    Compressor.compress(quadtree);

                    IO.saveImage(outputFile, quadtree);
                    compressionSuccess = true;
                } catch (Exception e) {
                    System.out.println(
                            "\u001B[31m[ERROR]\u001B[0m : Terjadi kesalahan saat kompresi gambar - " + e.getMessage());
                }

            } else {
                System.out.println(
                        "\n\u001B[34m[INFO]\u001B[0m : Memproses gambar dengan Quadtree untuk mencapai target kompresi ...\n");
                IO.readImage(inputFile);
                IO.infoImage.setImageParam(method, Threshold, minBlock);
                quadtree = new Quadtree(IO.infoImage.getRow(), IO.infoImage.getCol(), 0, 0);
                double lowerBound = 0;
                double upperBound = 255;
                if (method == 1) {
                    upperBound = 65025;
                } else if (method == 2) {
                    upperBound = 255;
                } else if (method == 3) {
                    upperBound = 255;
                } else if (method == 4) {
                    upperBound = 8;
                } else if (method == 5) {
                    upperBound = 1;
                }
                double tolerance = 0.01;
                double bestThreshold = Threshold;
                double currentCompressionRate = 0;
                boolean found = false;
                double maxCompressionRate;
                IO.infoImage.setImageParam(method, upperBound, minBlock);
                Compressor.compress(quadtree);
                IO.saveImage(outputFile, quadtree);
                maxCompressionRate = IO.calculateCompressionPercentage(IO.getFileSize(inputFile),
                        IO.getFileSize(outputFile));
                double minCompressionRate;
                IO.infoImage.setImageParam(method, lowerBound, minBlock);
                Compressor.compress(quadtree);
                IO.saveImage(outputFile, quadtree);
                minCompressionRate = IO.calculateCompressionPercentage(IO.getFileSize(inputFile),
                        IO.getFileSize(outputFile));

                while (upperBound - lowerBound > tolerance) {
                    double midThreshold = (lowerBound + upperBound) / 2;
                    // System.out.println("[INFO] : Mencoba threshold " + midThreshold);

                    IO.infoImage.setImageParam(method, midThreshold, minBlock);
                    Quadtree tempquadtree = new Quadtree(IO.infoImage.getRow(), IO.infoImage.getCol(), 0, 0);

                    try {
                        Compressor.compress(tempquadtree);

                        // Simpan hasil kompres sementara ke file sementara
                        String tempOutputFile = "temp_output_" + midThreshold + ".jpg";
                        IO.saveImage(tempOutputFile, tempquadtree);

                        long inputSize = IO.getFileSize(inputFile);
                        long outputSize = IO.getFileSize(tempOutputFile);
                        currentCompressionRate = IO.calculateCompressionPercentage(inputSize, outputSize);
                        // System.out.println("[INFO] : lowerBound : " + lowerBound);
                        // System.out.println("[INFO] : upperBound : " + upperBound);
                        // System.out.println("[INFO] : Persentase kompresi : " +
                        // currentCompressionRate/100 + "%");

                        // Hapus file sementara
                        new java.io.File(tempOutputFile).delete();

                        if (Math.abs(currentCompressionRate / 100 - targetKompresi) <= tolerance) {
                            bestThreshold = midThreshold;
                            found = true;
                            compressionSuccess = true;
                            break;
                        }

                        if (currentCompressionRate / 100 > targetKompresi) {
                            upperBound = midThreshold;
                        } else {
                            lowerBound = midThreshold;
                        }

                    } catch (OutOfMemoryError e) {
                        System.out.println("\u001B[31m[ERROR]\u001B[0m : Memori tidak cukup saat mencoba threshold "
                                + midThreshold);
                        upperBound = midThreshold; // Coba threshold lebih rendah
                    } catch (Exception e) {
                        System.out.println("\u001B[31m[ERROR]\u001B[0m : Kesalahan saat mencoba threshold "
                                + midThreshold + " - " + e.getMessage());
                        upperBound = midThreshold; // Coba threshold lebih rendah
                    }
                }

                if (found) {
                    System.out.println("\u001B[34m[INFO]\u001B[0m : Threshold terbaik ditemukan: " + bestThreshold);
                    IO.infoImage.setImageParam(method, bestThreshold, minBlock);
                    Quadtree finalQuadtree = new Quadtree(IO.infoImage.getRow(), IO.infoImage.getCol(), 0, 0);
                    quadtree = finalQuadtree;
                    Compressor.compress(finalQuadtree);
                    IO.saveImage(outputFile, finalQuadtree);

                } else {
                    System.out.println(
                            "\u001B[31m[ERROR]\u001B[0m : Tidak dapat menemukan threshold yang memenuhi sesuai dengan metode error untuk mencapai target kompresi dengan min block size "
                                    + minBlock);
                    System.out.println("\u001B[31m[ERROR]\u001B[0m : Compression rate terbesar yang dicapai: "
                            + maxCompressionRate / 100);
                    System.out.println("\u001B[31m[ERROR]\u001B[0m : Compression rate terkecil yang dicapai: "
                            + minCompressionRate / 100);
                }
            }

            // Buat GIF
            try {
                BufferedImage[] frames = IO.createProcessBufferedImages(quadtree, IO.infoImage.getCol(),
                        IO.infoImage.getRow());
                IO.createGIF(frames, outputGIFFile, 500);
                // System.out.println("\u001B[32m[SUKSES]\u001B[0m : GIF berhasil dibuat di " +
                // outputGIFFile);
                gifSuccess = true;
            } catch (OutOfMemoryError e) {
                System.out.println("\u001B[31m[ERROR]\u001B[0m : Memori tidak cukup saat membuat GIF.");
            } catch (Exception e) {
                System.out
                        .println("\u001B[31m[ERROR]\u001B[0m : Terjadi kesalahan saat membuat GIF - " + e.getMessage());
            }

            // OUTPUT
            try {
                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                System.out.println("\n\n\u001B[32m--------[OUTPUT KOMPRESI]--------\u001B[0m");
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

                System.out.println("\u001B[32m[SUKSES]\u001B[0m : GIF berhasil dibuat di " + outputGIFFile);
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
            } catch (Exception e) {
                System.out.println(
                        "\u001B[31m[ERROR]\u001B[0m : Terjadi kesalahan saat menampilkan hasil - " + e.getMessage());
            }
        }
        System.out.println("\u001B[34m[INFO]\u001B[0m : Program selesai. Hatur nuhun!");
    }
}
